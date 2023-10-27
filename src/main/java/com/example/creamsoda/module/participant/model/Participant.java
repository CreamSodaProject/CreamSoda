package com.example.creamsoda.module.participant.model;

import com.example.creamsoda.module.file.model.File;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "PARTICIPANT")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Schedule schedule;

}
