package com.example.creamsoda.module.schdule.dto;

import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.util.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleRequest(
        @NotBlank(message = "제목 입력은 필수 입니다.")
        String title,
        String memo,
        String todo,
        @NotNull(message = "라벨 선택은 필수 입니다.")
        ScheduleLabel label,
        String startTime,
        String endTime,
        Integer userId
) {
    public Schedule toEntity(User user) {

        LocalDateTime startTimeParser = DateUtils.parseLocalDateTime(startTime);
        LocalDateTime endTimeParser = DateUtils.parseLocalDateTime(endTime);

        return new Schedule(null, title, memo, todo, label, startTimeParser, endTimeParser, user);
    }
}
