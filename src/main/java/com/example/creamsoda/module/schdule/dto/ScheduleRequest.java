package com.example.creamsoda.module.schdule.dto;

import com.example.creamsoda.module.participant.model.Participant;
import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.util.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ScheduleRequest {
    // TODO 전체적인 수정필요

    @NotBlank(message = "제목 입력은 필수 입니다.")
    private String title;
    private String memo;
    private String todo;
    @NotNull(message = "라벨 선택은 필수 입니다.")
    private ScheduleLabel label;
    private String startTime;
    private String endTime;
    private Integer userId;


    // participant 잠시 빼둠 오류 이슈로
    public Schedule toEntity(Participant participant) {

        LocalDateTime startTimeParser = DateUtils.parseLocalDateTime(startTime);
        LocalDateTime endTimeParser = DateUtils.parseLocalDateTime(endTime);

        return new Schedule(title, memo, label, startTimeParser, endTimeParser);
    }

}
