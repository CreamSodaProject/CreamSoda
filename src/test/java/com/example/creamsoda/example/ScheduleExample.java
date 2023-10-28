package com.example.creamsoda.example;

import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.schdule.model.Schedule;

import java.time.LocalDateTime;

public interface ScheduleExample {
    Schedule schedule = new Schedule(1, "프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!"
            , ScheduleLabel.RED, null, null, LocalDateTime.of(2023, 9, 21, 20, 0)
            , LocalDateTime.of(2023, 9, 22, 18, 0));
}
