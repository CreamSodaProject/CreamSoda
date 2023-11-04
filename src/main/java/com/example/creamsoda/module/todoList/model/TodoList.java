package com.example.creamsoda.module.todoList.model;

import com.example.creamsoda.common.BaseTime;
import com.example.creamsoda.module.schdule.model.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TODOLIST")
public class TodoList extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Schedule todo;
}
