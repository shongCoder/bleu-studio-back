package com.portfolio.bleustudio.manager.service;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.dto.ManagerSignUpRequestDTO;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.repository.ManagerRepository;
import com.portfolio.bleustudio.manager.vo.ManagerEmailVO;
import com.portfolio.bleustudio.manager.vo.ManagerLoginIdVO;
import com.portfolio.bleustudio.manager.vo.ManagerPasswordVO;
import com.portfolio.bleustudio.manager.vo.ManagerPhoneVO;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Manager getManagerById(String id) {
        return managerRepository.findByLoginId(id).orElseThrow(
                () -> new RestApiException(ErrorEnum.MANAGER_NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    public Optional<Manager> getOptionalManagerById(String id) {
        return managerRepository.findByLoginId(id);
    }

    @Transactional
    public Long createManager(ManagerSignUpRequestDTO requestDTO) {

        /* 검증부 */
        ManagerLoginIdVO loginIdVO = ManagerLoginIdVO.from(requestDTO.getLoginId());
        ManagerPasswordVO passwordVO = ManagerPasswordVO.from(requestDTO.getPassword());
        ManagerEmailVO emailVO = ManagerEmailVO.from(requestDTO.getEmail());
        ManagerPhoneVO phoneVO = ManagerPhoneVO.from(requestDTO.getPhone());

        // 아이디 중복 체크
        Optional<Manager> checkedId = managerRepository.findByLoginId(loginIdVO.getValue());
        if (checkedId.isPresent()) {
            throw new RestApiException(ErrorEnum.DUPLICATE_MANAGER_LOGIN_ID);
        }
        // 이메일 중복 체크
        Optional<Manager> checkedEmail = managerRepository.findByEmail(emailVO.getValue());
        if (checkedEmail.isPresent()) {
            throw new RestApiException(ErrorEnum.DUPLICATE_MANAGER_EMAIL);
        }
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(passwordVO.getValue());

        Manager manager = Manager.builder()
                .loginId(loginIdVO.getValue())
                .password(encodedPassword)
                .name(requestDTO.getName())
                .phone(phoneVO.getValue())
                .email(emailVO.getValue())
                .useState(true)
                .build();
        Manager savedManager = managerRepository.save(manager);

        return savedManager.getManagerNo();
    }
}
