package com.example.creamsoda.module.schdule.controller;

import com.example.creamsoda.exception.Exception400;
import com.example.creamsoda.module.schdule.dto.ScheduleRequest;
import com.example.creamsoda.module.schdule.dto.ScheduleUpdate;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.schdule.service.ScheduleService;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final UserService userService;


    public ScheduleController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<List<Schedule>> scheduleList(@PathVariable Integer id) {

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            throw new Exception400("유저의 정보가 존재 하지 않습니다.");
        }

        List<Schedule> scheduleList = scheduleService.list();

        return ResponseEntity.ok().body(scheduleList);
    }

    @PostMapping
    public ResponseEntity<Schedule> saveSchedule(@Valid @RequestBody ScheduleRequest request, BindingResult errors) {

        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<User> optionalUser = userService.getUser(request.getUserId());
        if (optionalUser.isEmpty()) {
            throw new Exception400("유저의 정보가 존재 하지 않습니다.");
        }

        Schedule schedule = scheduleService.saveSchedule(request, optionalUser.get());

        return ResponseEntity.ok(schedule);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer id, @Valid @RequestBody ScheduleUpdate update, BindingResult errors) {

        if (errors.hasErrors()) {
            throw new Exception400(errors.getAllErrors().get(0).getDefaultMessage());
        }

        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            throw new Exception400("유저의 정보가 존재 하지 않습니다.");
        }

        Schedule schedule = scheduleService.updateSchedule(update, optionalUser.get());

        return ResponseEntity.ok().body(schedule);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Integer id) {

        // TODO : 유저 정보는 authentication 으로 가져오기
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()) {
            throw new Exception400("유저의 정보가 존재 하지 않습니다.");
        }

        Optional<Schedule> optionalSchedule = scheduleService.getSchedule(id);
        if (optionalSchedule.isEmpty()) {
            throw new Exception400("스케줄의 정보가 존재 하지 않습니다.");
        }

        scheduleService.deleteSchedule(id);

        return ResponseEntity.ok().body("스케줄 정보가 삭제되었습니다.");
    }
}
