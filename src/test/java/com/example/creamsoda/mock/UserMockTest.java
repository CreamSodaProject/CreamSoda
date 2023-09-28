package com.example.creamsoda.mock;

import com.example.creamsoda.config.SecurityConfig;
import com.example.creamsoda.example.UserExample;
import com.example.creamsoda.module.user.controller.UserController;
import com.example.creamsoda.module.user.dto.UserJoinRequest;
import com.example.creamsoda.module.user.dto.UserLoginRequest;
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

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("유저 Mock 테스트")
@Import(SecurityConfig.class)
public class UserMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원가입 실패 Valid 오류")
    void userJoinFail() throws Exception {

        UserJoinRequest request = new UserJoinRequest("hohyeon", "1234", "hohyeon");

        // given
        given(this.userService.userJoin(request)).willReturn(request.toEntity());

        // When
        ResultActions perform = this.mockMvc.perform(
                post("/user/join")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("이메일 형식에 맞지 않습니다."))

        ;
    }

    @Test
    @DisplayName("회원가입 성공")
    void userJoinSuccess() throws Exception {

        UserJoinRequest request = new UserJoinRequest("hohyeon@naver.com", "1234", "hohyeon");

        // given
        given(this.userService.userJoin(request)).willReturn(request.toEntity());

        // When
        ResultActions perform = this.mockMvc.perform(
                post("/user/join")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value("hohyeon@naver.com"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.name").value("hohyeon"))

        ;
    }

    @Test
    @DisplayName("로그인 실패 Valid 오류")
    void userLoginFail() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest("David@naver.com", "");

        // given
        given(this.userService.userLogin(userLoginRequest)).willReturn(Optional.of(UserExample.user));

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/user/login")
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("비밀번호를 입력해주세요."))
                ;
    }

    @Test
    @DisplayName("로그인 실패 Service Empty")
    void userLoginFail2() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest("David@naver.com", "1234");

        // given
        given(this.userService.userLogin(userLoginRequest)).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/user/login")
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("이메일과 비밀번호를 다시 확인 해주세요."))
        ;
    }

    @Test
    @DisplayName("로그인 성공")
    void userLoginSuccess() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest("David@naver.com", "1234");

        // given
        given(this.userService.userLogin(userLoginRequest)).willReturn(Optional.of(UserExample.user));

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/user/login")
                        .content(objectMapper.writeValueAsString(userLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value("David@naver.com"))
                .andExpect(jsonPath("$.password").value("1234"))
        ;
    }
}
