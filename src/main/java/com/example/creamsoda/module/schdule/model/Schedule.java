package com.example.creamsoda.module.schdule.model;

import com.example.creamsoda.module.schdule.common.ScheduleLabel;
import com.example.creamsoda.module.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Comment("스케줄 제목")
    private String title;

    @Comment("스케줄 메모")
    private String memo;

    @Comment("스케줄 TODO")
    private String todo;

    @Comment("라벨 색")
    @Enumerated(EnumType.STRING)
    private ScheduleLabel label; // enum 으로 예정

    @Comment("스케줄 시작시간")
    private LocalDateTime startTime;

    @Comment("스케줄 마무리시간")
    private LocalDateTime endTime;

    @Comment("참가자 유저")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
