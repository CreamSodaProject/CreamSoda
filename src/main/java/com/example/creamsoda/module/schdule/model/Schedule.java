package com.example.creamsoda.module.schdule.model;

import com.example.creamsoda.common.BaseTime;
import com.example.creamsoda.module.day.model.Day;
import com.example.creamsoda.module.file.model.FileInfo;
import com.example.creamsoda.module.participant.model.Participant;
import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.todoList.model.TodoList;
import com.example.creamsoda.module.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class Schedule extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("스케줄 제목")
    private String title;

    @Comment("스케줄 메모")
    private String memo;

    @Comment("라벨 색")
    @Enumerated(EnumType.STRING)
    private ScheduleLabel label;

    @Comment("사진 출처")
    @ManyToOne
    private FileInfo fileInfo;

    @Comment("하루")
    @ManyToOne
    private Day day;

    @Comment("스케줄 시작시간")
    private LocalDateTime startTime;

    @Comment("스케줄 마무리시간")
    private LocalDateTime endTime;

    public Schedule(String title, String memo,  ScheduleLabel label, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.memo = memo;
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
