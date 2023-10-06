package com.example.creamsoda.integrated;

import com.example.creamsoda.example.UserExample;
import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.schdule.dto.ScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

@DisplayName("ScheduleController Test")
public class ScheduleControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("[실패] 스케줄 목록 (인증 오류)")
    void scheduleListFail401() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/schedule/{id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(
                        document("schedule-list-401",
                                responseFields(getFailResponseField())
                        )
                );

    }

    @Test
    @DisplayName("[성공] 스케줄 목록")
    void scheduleList() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/schedule/{id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", getUser())
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("schedule-list",
                        responseFields().and(getScheduleListField())
                        )
                );

    }

    @Test
    @DisplayName("[실패] 스케줄 등록 (인증 오류)")
    void scheduleSaveFail401() throws Exception {

        ScheduleRequest request = new ScheduleRequest("풋살차는날", "날씨가 좋았으면 좋겠다.", "풋살화 세탁맡기기", ScheduleLabel.RED
                , "2023-09-21T20:00:00", "2023-09-22T20:00:00", UserExample.user.getId());

        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(
                        document("schedule-save-401",
                                requestFields((getScheduleSaveRequestField())),
                                responseFields().and((getFailResponseField()))
                        )
                );
    }

    @Test
    @DisplayName("[실패] 스케줄 등록 (Valid 오류)")
    void scheduleSaveFail() throws Exception {

        ScheduleRequest request = new ScheduleRequest("", "날씨가 좋았으면 좋겠다.", "풋살화 세탁맡기기", ScheduleLabel.RED
                , "2023-09-21T20:00:00", "2023-09-22T20:00:00", UserExample.user.getId());

        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", getUser())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("schedule-save-valid",
                                requestFields((getScheduleSaveRequestField())),
                                responseFields().and((getFailResponseField()))
                        )
                );
    }


    @Test
    @DisplayName("[성공] 스케줄 등록")
    void scheduleSave() throws Exception {

        ScheduleRequest request = new ScheduleRequest("풋살차는날", "날씨가 좋았으면 좋겠다.", "풋살화 세탁맡기기", ScheduleLabel.RED
                , "2023-09-21T20:00:00", "2023-09-22T20:00:00", UserExample.user.getId());

        ResultActions perform = this.mockMvc.perform(
                post("/schedule")
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", getUser())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("schedule-save",
                                requestFields((getScheduleSaveRequestField())),
                                responseFields().and((getScheduleSaveResponseField())
                                )
                        )

                );
    }

    private FieldDescriptor[] getScheduleListField() {
        return new FieldDescriptor[] {
                fieldWithPath("[].id").description("스케줄 ID"),
                fieldWithPath("[].title").description("스케줄 제목"),
                fieldWithPath("[].memo").description("메모"),
                fieldWithPath("[].todo").description("TODO 리스트"),
                fieldWithPath("[].label").description("라벨"),
                fieldWithPath("[].startTime").description("시작기간"),
                fieldWithPath("[].endTime").description("마감기간"),
                fieldWithPath("[].user.id").description("유저 ID"),
                fieldWithPath("[].user.email").description("유저 이메일"),
                fieldWithPath("[].user.password").description("유저 비밀번호"),
                fieldWithPath("[].user.name").description("유저 이름"),
                fieldWithPath("[].user.birthDate").description("유저 생년월일"),
                fieldWithPath("[].user.createdDate").description("가입 시간"),
                fieldWithPath("[].user.modifiedDate").description("수정 시간"),
        };
    }

    private FieldDescriptor[] getScheduleSaveRequestField() {
        return new FieldDescriptor[]{
                fieldWithPath("title").description("제목"),
                fieldWithPath("memo").description("메모"),
                fieldWithPath("todo").description("todo 리스트"),
                fieldWithPath("label").description("라벨"),
                fieldWithPath("startTime").description("시작시간"),
                fieldWithPath("endTime").description("마감시간"),
                fieldWithPath("userId").description("유저ID"),
        };
    }

    private FieldDescriptor[] getScheduleSaveResponseField() {
        return new FieldDescriptor[]{
                fieldWithPath("id").description("스케줄 ID"),
                fieldWithPath("title").description("제목"),
                fieldWithPath("memo").description("메모"),
                fieldWithPath("todo").description("todo 리스트"),
                fieldWithPath("label").description("라벨"),
                fieldWithPath("startTime").description("시작시간"),
                fieldWithPath("endTime").description("마감시간"),
                fieldWithPath("user.id").description("유저 id"),
                fieldWithPath("user.email").description("유저 이메일"),
                fieldWithPath("user.password").description("유저 비밀번호"),
                fieldWithPath("user.name").description("유저 이름"),
                fieldWithPath("user.birthDate").description("유저 생년월일"),
                fieldWithPath("user.createdDate").description("가입 시간"),
                fieldWithPath("user.modifiedDate").description("수정 시간"),
        };
    }
}
