package com.example.creamsoda.mock;

import com.example.creamsoda.config.SecurityConfig;
import com.example.creamsoda.core.WithMockCustomUser;
import com.example.creamsoda.example.ScheduleExample;
import com.example.creamsoda.example.UserExample;
import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.schdule.controller.ScheduleController;
import com.example.creamsoda.module.schdule.dto.ScheduleRequest;
import com.example.creamsoda.module.schdule.dto.ScheduleUpdate;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.schdule.service.ScheduleService;
import com.example.creamsoda.module.user.model.User;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("스케줄러 Mock 테스트")
@Import(SecurityConfig.class)
public class ScheduleMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ScheduleService scheduleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[실패] 스케줄 리스트 (인증 오류)")
    void scheduleListFail401() throws Exception {

        User user1 = new User(1, "David@naver.com", "1234", "David", "19960807");
        User user2 = new User(2, "Ho@naver.com", "1234", "Ho", "19960807");

        List<Schedule> scheduleList = List.of(
                new Schedule(1, "프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅", ScheduleLabel.RED
                        , LocalDateTime.of(2023, 9, 21, 20, 0)
                        , LocalDateTime.of(2023, 9, 22, 18, 0), user1)
                ,
                new Schedule(2, "프로젝트 진행 시작", "로그인 및 회원가입", "JWT 설정", ScheduleLabel.BLUE
                        , LocalDateTime.of(2023, 9, 23, 20, 0)
                        , LocalDateTime.of(2023, 9, 25, 18, 0), user2)

        );

        // given
        given(this.userService.getUser(user1.getId())).willReturn(Optional.empty());
        given(this.scheduleService.list()).willReturn(scheduleList);

        // when
        ResultActions perform = this.mockMvc.perform(
                get("/schedule/{id}", 3)
                        .accept(MediaType.APPLICATION_JSON)

        );

        // then
        perform.andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Unauthorized"))
                .andExpect(jsonPath("$.status").value("401"))
                .andExpect(jsonPath("$.detail").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.instance").value("/schedule/3"))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 리스트 (유저 정보 오류)")
    void scheduleListFail() throws Exception {

        User user1 = new User(1, "David@naver.com", "1234", "David", "19960807");
        User user2 = new User(2, "Ho@naver.com", "1234", "Ho", "19960807");

        List<Schedule> scheduleList = List.of(
                new Schedule(1, "프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅", ScheduleLabel.RED
                        , LocalDateTime.of(2023, 9, 21, 20, 0)
                        , LocalDateTime.of(2023, 9, 22, 18, 0), user1)
                ,
                new Schedule(2, "프로젝트 진행 시작", "로그인 및 회원가입", "JWT 설정", ScheduleLabel.BLUE
                        , LocalDateTime.of(2023, 9, 23, 20, 0)
                        , LocalDateTime.of(2023, 9, 25, 18, 0), user2)

        );

        // given
        given(this.userService.getUser(user1.getId())).willReturn(Optional.empty());
        given(this.scheduleService.list()).willReturn(scheduleList);

        // when
        ResultActions perform = this.mockMvc.perform(
                get("/schedule/{id}", 3)
                        .accept(MediaType.APPLICATION_JSON)

        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("유저의 정보가 존재 하지 않습니다."))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[성공] 스케줄 리스트 보기")
    void scheduleList() throws Exception {

        User user1 = new User(1, "David@naver.com", "1234", "David", "19960807");
        User user2 = new User(2, "Ho@naver.com", "1234", "Ho", "19960807");

        List<Schedule> scheduleList = List.of(
                new Schedule(1, "프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅", ScheduleLabel.RED
                        , LocalDateTime.of(2023, 9, 21, 20, 0)
                        , LocalDateTime.of(2023, 9, 22, 18, 0) , user1)
                ,
                new Schedule(2, "프로젝트 진행 시작", "로그인 및 회원가입", "JWT 설정", ScheduleLabel.BLUE
                        , LocalDateTime.of(2023, 9, 23, 20, 0)
                        , LocalDateTime.of(2023, 9, 25, 18, 0) , user2)

        );

        // given
        given(this.userService.getUser(user1.getId())).willReturn(Optional.of(user1));
        given(this.scheduleService.list()).willReturn(scheduleList);

        // when
        ResultActions perform = this.mockMvc.perform(
                get("/schedule/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)

        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("프로젝트 세팅 마무리"))
                .andExpect(jsonPath("$.[0].memo").value("JPA 테스트는 다 마무리 해야함!"))
                .andExpect(jsonPath("$.[0].todo").value("화이팅"))
                .andExpect(jsonPath("$.[0].label").value("RED"))
                .andExpect(jsonPath("$.[0].startTime").value("2023-09-21T20:00:00"))
                .andExpect(jsonPath("$.[0].endTime").value("2023-09-22T18:00:00"))
                .andExpect(jsonPath("$.[0].user.id").value(1))
                .andExpect(jsonPath("$.[0].user.email").value("David@naver.com"))
                .andExpect(jsonPath("$.[0].user.password").value("1234"))
                .andExpect(jsonPath("$.[0].user.name").value("David"))

                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("프로젝트 진행 시작"))
                .andExpect(jsonPath("$.[1].memo").value("로그인 및 회원가입"))
                .andExpect(jsonPath("$.[1].todo").value("JWT 설정"))
                .andExpect(jsonPath("$.[1].label").value("BLUE"))
                .andExpect(jsonPath("$.[1].startTime").value("2023-09-23T20:00:00"))
                .andExpect(jsonPath("$.[1].endTime").value("2023-09-25T18:00:00"))
                .andExpect(jsonPath("$.[1].user.id").value(2))
                .andExpect(jsonPath("$.[1].user.email").value("Ho@naver.com"))
                .andExpect(jsonPath("$.[1].user.password").value("1234"))
                .andExpect(jsonPath("$.[1].user.name").value("Ho"))
                ;
    }

    @Test
    @DisplayName("[실패] 스케줄 등록 실패 (인증 오류)")
    void scheduleSaveFail401() throws Exception {

        User user = UserExample.user;

        ScheduleRequest scheduleRequest = new ScheduleRequest("", "JPA 테스트는 다 마무리 해야함!", "화이팅"
                ,ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.saveSchedule(scheduleRequest, user)).willReturn(ScheduleExample.schedule);

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(scheduleRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Unauthorized"))
                .andExpect(jsonPath("$.status").value("401"))
                .andExpect(jsonPath("$.detail").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.instance").value("/schedule"))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 등록 (Valid)")
    void scheduleSaveFail() throws Exception {

        User user = UserExample.user;

        ScheduleRequest scheduleRequest = new ScheduleRequest("", "JPA 테스트는 다 마무리 해야함!", "화이팅"
                ,ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.saveSchedule(scheduleRequest, user)).willReturn(ScheduleExample.schedule);

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(scheduleRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("제목 입력은 필수 입니다."))
                ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 등록 (유저 정보 오류)")
    void scheduleFail2() throws Exception {

        User user = UserExample.user;

        ScheduleRequest scheduleRequest = new ScheduleRequest("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());
        given(this.scheduleService.saveSchedule(scheduleRequest, user)).willReturn(ScheduleExample.schedule);

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(scheduleRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("유저의 정보가 존재 하지 않습니다."))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[성공] 스케줄 등록")
    void scheduleSave() throws Exception {

        User user = UserExample.user;

        ScheduleRequest scheduleRequest = new ScheduleRequest("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.saveSchedule(scheduleRequest, user)).willReturn(ScheduleExample.schedule);

        // when
        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(scheduleRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
//                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("프로젝트 세팅 마무리"))
                .andExpect(jsonPath("$.memo").value("JPA 테스트는 다 마무리 해야함!"))
                .andExpect(jsonPath("$.todo").value("화이팅"))
                .andExpect(jsonPath("$.label").value("RED"))
                .andExpect(jsonPath("$.startTime").value("2023-09-21T20:00:00"))
                .andExpect(jsonPath("$.endTime").value("2023-09-22T18:00:00"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("David@naver.com"))
                .andExpect(jsonPath("$.user.password").value("1234"))
                .andExpect(jsonPath("$.user.name").value("David"))
        ;
    }

    @Test
    @DisplayName("[실패] 스케줄 수정 (인증 오류)")
    void scheduleUpdateFail401() throws Exception {

        User user = UserExample.user;

        ScheduleUpdate scheduleUpdate = new ScheduleUpdate("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "해결 못할듯?"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());
        given(this.scheduleService.updateSchedule(scheduleUpdate, user)).willReturn(scheduleUpdate.toEntity(user));

        // when
        ResultActions perform = this.mockMvc.perform(
                put("/schedule/{id}", 1)
                        .content(objectMapper.writeValueAsString(scheduleUpdate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Unauthorized"))
                .andExpect(jsonPath("$.status").value("401"))
                .andExpect(jsonPath("$.detail").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.instance").value("/schedule/1"))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 수정 (유저 정보 오류)")
    void scheduleUpdateFail() throws Exception {

        User user = UserExample.user;

        ScheduleUpdate scheduleUpdate = new ScheduleUpdate("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "해결 못할듯?"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());
        given(this.scheduleService.updateSchedule(scheduleUpdate, user)).willReturn(scheduleUpdate.toEntity(user));

        // when
        ResultActions perform = this.mockMvc.perform(
                put("/schedule/{id}", 1)
                        .content(objectMapper.writeValueAsString(scheduleUpdate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("유저의 정보가 존재 하지 않습니다."))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 수정 (Valid)")
    void scheduleUpdateFail2() throws Exception {

        User user = UserExample.user;

        ScheduleUpdate scheduleUpdate = new ScheduleUpdate("", "JPA 테스트는 다 마무리 해야함!", "해결 못할듯?"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());
        given(this.scheduleService.updateSchedule(scheduleUpdate, user)).willReturn(scheduleUpdate.toEntity(user));

        // when
        ResultActions perform = this.mockMvc.perform(
                put("/schedule/{id}", 1)
                        .content(objectMapper.writeValueAsString(scheduleUpdate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("제목 입력은 필수 입니다."))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[성공] 스케줄 수정")
    void scheduleUpdate() throws Exception {

        User user = UserExample.user;

        ScheduleUpdate scheduleUpdate = new ScheduleUpdate("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "해결 못할듯?"
                , ScheduleLabel.RED, "2023-09-21T20:00:00", "2023-09-22T18:00:00", user.getId());

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.updateSchedule(scheduleUpdate, user)).willReturn(scheduleUpdate.toEntity(user));

        // when
        ResultActions perform = this.mockMvc.perform(
                put("/schedule/{id}", 1)
                        .content(objectMapper.writeValueAsString(scheduleUpdate))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("프로젝트 세팅 마무리"))
                .andExpect(jsonPath("$.memo").value("JPA 테스트는 다 마무리 해야함!"))
                .andExpect(jsonPath("$.todo").value("해결 못할듯?"))
                .andExpect(jsonPath("$.label").value("RED"))
                .andExpect(jsonPath("$.startTime").value("2023-09-21T20:00:00"))
                .andExpect(jsonPath("$.endTime").value("2023-09-22T18:00:00"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("David@naver.com"))
                .andExpect(jsonPath("$.user.password").value("1234"))
                .andExpect(jsonPath("$.user.name").value("David"))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 삭제 (유저 정보 오류)")
    void DeleteScheduleFail() throws Exception {

        User user = UserExample.user;

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mockMvc.perform(
                delete("/schedule/{id}", 1)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("유저의 정보가 존재 하지 않습니다."))
                ;
    }
    @Test
    @DisplayName("[실패] 스케줄 삭제 (인증 오류)")
    void DeleteScheduleFail401() throws Exception {

        User user = UserExample.user;
        Schedule schedule = ScheduleExample.schedule;

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.getSchedule(schedule.getId())).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mockMvc.perform(
                delete("/schedule/{id}", 1)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Unauthorized"))
                .andExpect(jsonPath("$.status").value("401"))
                .andExpect(jsonPath("$.detail").value("인증되지 않았습니다"))
                .andExpect(jsonPath("$.instance").value("/schedule/1"))
        ;
    }


    @Test
    @WithMockCustomUser
    @DisplayName("[실패] 스케줄 삭제 (스케줄 정보 오류)")
    void DeleteScheduleFail2() throws Exception {

        User user = UserExample.user;
        Schedule schedule = ScheduleExample.schedule;

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.getSchedule(schedule.getId())).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mockMvc.perform(
                delete("/schedule/{id}", 1)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.detail").value("스케줄의 정보가 존재 하지 않습니다."))
        ;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("[성공] 스케줄 삭제")
    void DeleteScheduleSuccess() throws Exception {

        User user = UserExample.user;
        Schedule schedule = ScheduleExample.schedule;

        // given
        given(this.userService.getUser(user.getId())).willReturn(Optional.of(user));
        given(this.scheduleService.getSchedule(schedule.getId())).willReturn(Optional.of(schedule));

        // when
        ResultActions perform = this.mockMvc.perform(
                delete("/schedule/{id}", 1)
                        .with(csrf())
        );

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpectAll()
                .andExpect(content().string("스케줄 정보가 삭제되었습니다."));
        ;
    }
}
