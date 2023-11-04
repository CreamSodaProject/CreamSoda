package com.example.creamsoda.integrated;

import com.example.creamsoda.module.user.dto.UserJoinRequest;
import com.example.creamsoda.module.user.dto.UserLoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController Test")
public class UserControllerTest extends AbstractIntegrated{

    @Test
    @DisplayName("[실패] 유저 회원가입 테스트 (Valid 오류)")
    void userJoinFail() throws Exception {

        UserJoinRequest joinDTO = new UserJoinRequest("", "1234", "ho");


        ResultActions perform = this.mockMvc.perform(
                post("/user/join")
                        .content(objectMapper.writeValueAsString(joinDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("user-join-valid",
                                requestFields(getUserJoinRequestField()),
                                responseFields(getFailResponseField())
                        )

                );
    }

    @Test
    @DisplayName("[성공] 유저 회원가입 테스트")
    void userJoin() throws Exception {

        UserJoinRequest joinDTO = new UserJoinRequest("khh5762@naver.com", "1234", "ho");


        ResultActions perform = this.mockMvc.perform(
                post("/user/join")
                        .content(objectMapper.writeValueAsString(joinDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("user-join",
                                requestFields(getUserJoinRequestField()),
                                responseFields(getUserJoinField())
                        )

                );
    }

    @Test
    @DisplayName("[실패] 유저 로그인 테스트 (로그인 정보 오류)")
    void userLoginFail() throws Exception {

        UserLoginRequest loginDTO = new UserLoginRequest("", "1234");


        ResultActions perform = this.mockMvc.perform(
                post("/user/login")
                        .content(objectMapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("user-login",
                                requestFields(getUserLoginRequestField()),
                                responseFields(getFailResponseField())
                        )

                );
    }

    @Test
    @DisplayName("[성공] 유저 로그인 테스트")
    void userLogin() throws Exception {

        UserLoginRequest loginDTO = new UserLoginRequest("khh5762@naver.com", "1234");


        ResultActions perform = this.mockMvc.perform(
                post("/user/login")
                        .content(objectMapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("user-login",
                                requestFields(getUserLoginRequestField()),
                                responseFields(getUserLoginField())
                        )

                );
    }

    private FieldDescriptor[] getUserJoinRequestField() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("이메일"),
                fieldWithPath("password").description("비밀번호"),
                fieldWithPath("name").description("사용자이름"),
        };
    }
    private FieldDescriptor[] getUserJoinField() {
        return new FieldDescriptor[]{
                fieldWithPath("id").description("유저 id"),
                fieldWithPath("email").description("유저 이메일"),
                fieldWithPath("password").description("유저 비밀번호"),
                fieldWithPath("name").description("유저 이름"),
                fieldWithPath("profile").description("유저 프로필사진"),
                fieldWithPath("createdDate").description("가입 시간"),
                fieldWithPath("modifiedDate").description("수정 시간"),
        };
    }

    private FieldDescriptor[] getUserLoginRequestField() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("이메일"),
                fieldWithPath("password").description("비밀번호"),
        };
    }

    private FieldDescriptor[] getUserLoginField() {
        return new FieldDescriptor[]{
                fieldWithPath("id").description("유저 id"),
                fieldWithPath("email").description("유저 이메일"),
                fieldWithPath("password").description("유저 비밀번호"),
                fieldWithPath("profile").description("유저 프로필사진"),
                fieldWithPath("name").description("유저 이름"),
                fieldWithPath("createdDate").description("가입 시간"),
                fieldWithPath("modifiedDate").description("수정 시간"),
        };
    }

}
