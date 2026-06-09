package com.portfolio.bleustudio.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.bleustudio.board.dto.BoardRequestDto;
import com.portfolio.bleustudio.board.service.BoardService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
class BoardControllerTest {

    private MockMvc mockMvc;

    @MockitoBean
    private BoardService boardService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .alwaysDo(print())
                .build();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("게시글 등록 성공")
    void createBoard() throws Exception {
        BoardRequestDto request = new BoardRequestDto();
        request.setTitle("테스트 제목");
        request.setContent("테스트 내용");

        String json = objectMapper.writeValueAsString(request);

        MockMultipartFile image = new MockMultipartFile("board", "image.png", "image/png", new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A});
        MockMultipartFile metadata = new MockMultipartFile(
                "request",
                "request.json",
                APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );

        when(boardService.createBoard(any())).thenReturn(1L);

        ResultActions result = this.mockMvc.perform(multipart("/api/board")
                .file(image)
                .file(metadata)
                .contentType(MULTIPART_FORM_DATA)
                .accept(APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andDo(document("create-board",
                        requestParts(
                                partWithName("request").description("게시글 등록 JSON 데이터"),
                                partWithName("board").description("첨부파일 목록").optional()
                        ),
                        requestPartFields("request",
                                fieldWithPath("title").type(STRING).description("게시글 제목"),
                                fieldWithPath("content").type(STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(STRING).description("응답 코드"),
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("data").type(NUMBER).description("생성된 게시글 번호"),
                                fieldWithPath("error").type(BOOLEAN).description("에러 여부"),
                                fieldWithPath("status").type(BOOLEAN).description("상태 값"),
                                fieldWithPath("message").type(STRING).description("응답 메시지")
                        )
                ));

        verify(boardService).createBoard(any());
    }
}
