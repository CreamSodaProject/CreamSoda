package com.example.creamsoda.mock;

import com.example.creamsoda.config.SecurityConfig;
import com.example.creamsoda.module.schdule.controller.ScheduleController;
import com.example.creamsoda.module.schdule.service.ScheduleService;
import com.example.creamsoda.module.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ScheduleController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayName("스케줄러 Mock 테스트")
@Import(SecurityConfig.class)
public class ScheduleMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

}
