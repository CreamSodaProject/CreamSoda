package com.example.creamsoda.mock;

import com.example.creamsoda.config.SecurityConfig;
import com.example.creamsoda.module.email.controller.EmailController;
import com.example.creamsoda.module.email.dto.EmailAuthRequestDto;
import com.example.creamsoda.module.email.dto.PasswordFindRequestDto;
import com.example.creamsoda.module.email.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("이메일 Mock 테스트")
@Import(SecurityConfig.class)
public class EmailMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[성공] 이메일확인")
    void sendEmail() throws Exception {

        EmailAuthRequestDto emailAuthRequestDto = new EmailAuthRequestDto("khh5762@naver.com");

        //given
        given(this.emailService.sendEmail(emailAuthRequestDto)).willReturn(emailAuthRequestDto.toEntity("12345"));

        //when
        ResultActions perform = this.mockMvc.perform(
                post("/send/email")
                        .content(objectMapper.writeValueAsString(emailAuthRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value("khh5762@naver.com"))
                .andExpect(jsonPath("$.authNum").value("12345"))
                .andExpect(jsonPath("$.type").value("EMAIL")
                );
    }

    @Test
    @DisplayName("[성공] 비밀번호찾기 이메일 보내기")
    void findPassword() throws Exception {

        PasswordFindRequestDto passwordFindRequestDto = new PasswordFindRequestDto("khh5762@naver.com");

        //given
        given(this.emailService.sendPassword(passwordFindRequestDto)).willReturn(passwordFindRequestDto.toEntity("asd@as!e3"));

        //when
        ResultActions perform = this.mockMvc.perform(
                post("/send/password")
                        .content(objectMapper.writeValueAsString(passwordFindRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value("khh5762@naver.com"))
                .andExpect(jsonPath("$.authNum").value("asd@as!e3"))
                .andExpect(jsonPath("$.type").value("PASSWORD")
                );
    }
}
