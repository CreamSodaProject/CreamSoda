package com.example.creamsoda.module.schdule.service;

import com.example.creamsoda.module.schdule.dto.ScheduleRequest;
import com.example.creamsoda.module.schdule.dto.ScheduleUpdate;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.schdule.model.ScheduleRepository;
import com.example.creamsoda.module.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public Schedule saveSchedule(ScheduleRequest request, User user) {
        System.out.println("테스트 : " + request);
        System.out.println("테스트 : " + user);

        return scheduleRepository.save(request.toEntity(user));
    }

    public Schedule updateSchedule(ScheduleUpdate update, User user) {
        return scheduleRepository.save(update.toEntity(user));
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);

    }

    public Optional<Schedule> getSchedule(Integer id) {
        return scheduleRepository.findById(id);
    }
}
