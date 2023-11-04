package com.example.creamsoda.mock;

import com.example.creamsoda.config.SecurityConfig;
import com.example.creamsoda.example.EmailExample;
import com.example.creamsoda.module.email.controller.EmailController;
import com.example.creamsoda.module.email.dto.CheckRequest;
import com.example.creamsoda.module.email.dto.EmailAuthRequestDto;
import com.example.creamsoda.module.email.dto.PasswordFindRequestDto;
import com.example.creamsoda.module.email.service.EmailService;
import com.example.creamsoda.module.user.service.UserService;
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

import java.time.LocalDateTime;
import java.util.Optional;

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

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[실패] 인증번호 오류")
    void checkEmailFail() throws Exception {

        CheckRequest checkRequest = new CheckRequest("khh5762@naver.com","12345", LocalDateTime.now()); // 랜덤 인증번호


        //given
        given(this.emailService.emailCheck(checkRequest.email(), checkRequest.check())).willReturn(Optional.empty());

        //when
        ResultActions perform = this.mockMvc.perform(
                post("/send/check")
                        .content(objectMapper.writeValueAsString(checkRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.detail").value("인증번호가 일치하지 않습니다."))
                .andExpect(jsonPath("$.instance").value("/send/check")
                );
    }

    @Test
    @DisplayName("[실패] 이메일체크 인증시간만료")
    void checkEmailFail2() throws Exception {

        CheckRequest checkRequest = new CheckRequest("khh5762@naver.com","12345", LocalDateTime.of(2023,11,1,20,30)); // 랜덤 인증번호

        //given
        given(this.emailService.emailCheck(checkRequest.email(), checkRequest.check())).willReturn(Optional.of(EmailExample.email));

        //when
        ResultActions perform = this.mockMvc.perform(
                post("/send/check")
                        .content(objectMapper.writeValueAsString(checkRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.detail").value("만료시간 3분이 초과 되었습니다."))
                .andExpect(jsonPath("$.instance").value("/send/check")
                );
    }

    @Test
    @DisplayName("[성공] 이메일체크성공")
    void checkEmailSuccess() throws Exception {

        CheckRequest checkRequest = new CheckRequest("khh5762@naver.com","12345", LocalDateTime.now()); // 랜덤 인증번호

        //given
        given(this.emailService.emailCheck(checkRequest.email(), checkRequest.check())).willReturn(Optional.of(EmailExample.email));

        //when
        ResultActions perform = this.mockMvc.perform(
                post("/send/check")
                        .content(objectMapper.writeValueAsString(checkRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.detail").value("인증번호 체크 완료")
                );
    }

    @Test
    @DisplayName("[성공] 이메일보내기")
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
                .andExpect(jsonPath("$.detail").value("이메일 전송 완료")
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
                .andExpect(jsonPath("$.detail").value("임시비밀번호 전송 완료")
                );
    }
}
