package com.example.creamsoda.integrated;

import com.example.creamsoda.module.email.dto.CheckRequest;
import com.example.creamsoda.module.email.dto.EmailAuthRequestDto;
import com.example.creamsoda.module.email.dto.PasswordFindRequestDto;
import com.example.creamsoda.module.user.dto.UserJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EmailControllerTest extends AbstractIntegrated{

    @Test
    @DisplayName("[성공] 이메일 보내기")
    void sendEmail() throws Exception {

        EmailAuthRequestDto request = new EmailAuthRequestDto("khh9608@gmail.com");


        ResultActions perform = this.mockMvc.perform(
                post("/send/email")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("send-email",
                                requestFields(sendEmailSuccess()),
                                responseFields(sendResponse())
                        )

                );
    }

    @Test
    @DisplayName("[성공] 임시비밀번호 보내기")
    void sendPassword() throws Exception {

        PasswordFindRequestDto passwordFindRequestDto = new PasswordFindRequestDto("khh9608@gmail.com");

        ResultActions perform = this.mockMvc.perform(
                post("/send/password")
                        .content(objectMapper.writeValueAsString(passwordFindRequestDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("send-password",
                                requestFields(sendEmailSuccess()),
                                responseFields(sendResponse())
                        )

                );
    }
//
//    @Test
//    @DisplayName("[성공] 인증번호 체크")
//    void emailCheck() throws Exception {
//
//        CheckRequest request = new CheckRequest("khh9608@gmail.com", "12345");
//
//
//        ResultActions perform = this.mockMvc.perform(
//                post("/send/check")
//                        .content(objectMapper.writeValueAsString(request))
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//        );
//
//        perform
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(
//                        document("email-check",
//                                requestFields(EmailCheckSuccess()),
//                                responseFields(sendResponse())
//                        )
//
//                );
//    }

    @Test
    @DisplayName("[실패] 인증번호 오류")
    void emailCheckFail1() throws Exception {

        CheckRequest request = new CheckRequest("khh9608@gmail.com", "672222244");


        ResultActions perform = this.mockMvc.perform(
                post("/send/check")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("email-check-authNumError",
                                requestFields(EmailCheckSuccess()),
                                responseFields(getFailResponseField())
                        )

                );
    }

    @Test
    @DisplayName("[실패] 인증번호 마간시간 초과")
    void emailCheckFail2() throws Exception {

        CheckRequest request = new CheckRequest("khh9608@gmail.com", "67244");


        ResultActions perform = this.mockMvc.perform(
                post("/send/check")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("email-check-timeOut",
                                requestFields(EmailCheckSuccess()),
                                responseFields(getFailResponseField())
                        )

                );
    }
    private FieldDescriptor[] sendEmailSuccess() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("이메일")
        };
    }
    private FieldDescriptor[] sendResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("detail").description("응답메시지")
        };
    }


    private FieldDescriptor[] EmailCheckSuccess() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("이메일"),
                fieldWithPath("check").description("인증번호")
        };
    }
}
