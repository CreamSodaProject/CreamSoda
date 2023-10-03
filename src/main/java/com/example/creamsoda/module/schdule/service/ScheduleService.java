package com.example.creamsoda.module.schdule.service;

import com.example.creamsoda.module.schdule.dto.ScheduleRequest;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.schdule.model.ScheduleRepository;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.model.UserRepository;
import com.example.creamsoda.module.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    public ScheduleService(ScheduleRepository scheduleRepository, UserService userService) {
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public Schedule save(ScheduleRequest request, User user) {
        return scheduleRepository.save(request.toEntity(user));
    }
}
