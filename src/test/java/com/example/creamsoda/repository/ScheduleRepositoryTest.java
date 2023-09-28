package com.example.creamsoda.repository;

import com.example.creamsoda.module.schdule.ScheduleLabel;
import com.example.creamsoda.module.schdule.model.Schedule;
import com.example.creamsoda.module.schdule.model.ScheduleRepository;
import com.example.creamsoda.module.user.model.User;
import com.example.creamsoda.module.user.model.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("스케줄 Repository 테스트")
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void init() {
        setup("프로젝트 세팅 마무리", "JPA 테스트는 다 마무리 해야함!", "화이팅",ScheduleLabel.RED,
                LocalDateTime.of(2023, 9, 20, 9, 0)
        , LocalDateTime.of(2023, 9, 21, 18, 0));
    }

    @Test
    @DisplayName("스케줄 findAll(Select) 테스트")
    void selectScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        Assertions.assertNotEquals(scheduleList.size(), 0);

        Schedule schedule = scheduleList.get(0);
        Assertions.assertEquals(schedule.getId(), 1);
        Assertions.assertEquals(schedule.getTitle(), "프로젝트 세팅 마무리");
        Assertions.assertEquals(schedule.getMemo(),"JPA 테스트는 다 마무리 해야함!");
        Assertions.assertEquals(schedule.getTodo(),"화이팅");
        Assertions.assertEquals(schedule.getLabel(), ScheduleLabel.RED);
        Assertions.assertEquals(schedule.getStartTime(), LocalDateTime.of(2023, 9, 20, 9, 0));
        Assertions.assertEquals(schedule.getEndTime(), LocalDateTime.of(2023, 9, 21, 18, 0));
    }

    @Test
    @DisplayName("스케줄 findById And merge(Update) 테스트")
    void selectScheduleAndUpdate() {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(1);

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();

            Assertions.assertEquals(schedule.getTitle(), "프로젝트 세팅 마무리");
            Assertions.assertEquals(schedule.getMemo(),"JPA 테스트는 다 마무리 해야함!");

            schedule.setTitle("프로젝트 세팅 마무리 수정");
            Schedule scheduleMerge = testEntityManager.merge(schedule);

            Assertions.assertEquals(scheduleMerge.getTitle(), "프로젝트 세팅 마무리 수정");
        } else {
            Assertions.assertNotNull(optionalSchedule.get());
        }
    }

    @Test
    @DisplayName("유저 save(Insert) And remove(Delete) 테스트")
    void ScheduleInsertAndDelete() {

        Schedule schedule = scheduleRepository.save(
                setup("풋살", "진짜 박살내기 ^^", "Todo~",ScheduleLabel.BLUE,
                        LocalDateTime.of(2023, 9, 21, 20, 0)
                        , LocalDateTime.of(2023, 9, 21, 22, 0) )
        );

        Assertions.assertEquals(schedule.getId(), 2);
        Assertions.assertEquals(schedule.getTitle(), "풋살");
        Assertions.assertEquals(schedule.getMemo(),"진짜 박살내기 ^^");
        Assertions.assertEquals(schedule.getTodo(),"Todo~");
        Assertions.assertEquals(schedule.getLabel(), ScheduleLabel.BLUE);
        Assertions.assertEquals(schedule.getStartTime(), LocalDateTime.of(2023, 9, 21, 20, 0));
        Assertions.assertEquals(schedule.getEndTime(), LocalDateTime.of(2023, 9, 21, 22, 0));

        Optional<Schedule> optionalSchedule = scheduleRepository.findById(schedule.getId());
        if (optionalSchedule.isPresent()) {
            Schedule deleteSchedule = optionalSchedule.get();
            testEntityManager.remove(deleteSchedule);

            Optional<Schedule> optionalDeleteUser = scheduleRepository.findById(deleteSchedule.getId());
            optionalDeleteUser.ifPresent(Assertions::assertNull);
        } else {
            Assertions.assertNotNull(optionalSchedule.get());
        }
    }

    public Schedule setup(String title, String memo, String todo, ScheduleLabel label, LocalDateTime startTime, LocalDateTime endTime) {
        Schedule schedule = new Schedule();
        schedule.setTitle(title);
        schedule.setMemo(memo);
        schedule.setTodo(todo);
        schedule.setLabel(label);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        return testEntityManager.persist(schedule);
    }
}
