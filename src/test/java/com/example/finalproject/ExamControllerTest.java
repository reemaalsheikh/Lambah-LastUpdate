package com.example.finalproject;

import com.example.finalproject.Controller.ExamController;
import com.example.finalproject.Model.Exam;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.ExamService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ExamController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})

//Renad
public class ExamControllerTest {

    @MockBean
    private ExamService examService;

    @Autowired
    private MockMvc mockMvc;

    private Exam exam1, exam2, exam3;
    private User user;
    private List<Exam> exams;


//    @BeforeEach
//    void setUp() {
//        user = new User(1, "renad", "12345", "renad@gmail.com", 25, "2000-01-01", "Female", "STUDENT");
//        exam1 = new Exam(1, "Math Exam", 85, 100, LocalDate.now());
//        exam2 = new Exam(2, "Science Exam", 90, 100, LocalDate.now());
//        exam3 = new Exam(3, "History Exam", 75, 100, LocalDate.now());
//        exams = Arrays.asList(exam1, exam2, exam3);
//    }

    @Test
    public void testGetAllExams() throws Exception {
        Mockito.when(examService.getAllExams()).thenReturn(exams);

        mockMvc.perform(get("/api/v1/exam/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Math Exam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Science Exam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("History Exam"));
    }

    @Test
    public void testGetMyExams() throws Exception {
        Mockito.when(examService.getMyExams(user.getId())).thenReturn(exams);

        mockMvc.perform(get("/api/v1/exam/get/my")
                        .principal(() -> user.getUsername())) // Simulate authenticated user
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Math Exam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Science Exam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("History Exam"));
    }
}
