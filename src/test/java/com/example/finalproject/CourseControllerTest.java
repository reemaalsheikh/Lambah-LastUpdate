package com.example.finalproject;
import com.example.finalproject.Controller.CourseController;
import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Student;
import com.example.finalproject.Service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CourseController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    private Course course1, course2;

    private List<Course> courses;

    private Student student1 , student2;

    @BeforeEach
    void setUp() {
        course1 = new Course(1, "Java Programming", "Learn Java from basics to advanced", "Online", "Programming", 180.0, 299.99, null, Set.of(), null, Set.of());
        course2 = new Course(2, "Data Science with Python", "Explore data science concepts with Python", "Offline", "Data Science", 240.0, 399.99, null, Set.of(), null, Set.of());
        courses = Arrays.asList(course1, course2);

        student1 = new Student();
        student1.setId(1);
        student1.setFirstName("Reema");
        student1.setLastName("Alsheikh");


        student2 = new Student();
        student2.setId(2);
        student2.setFirstName("Rawan");
        student2.setLastName("Alsheikh");
    }

    @Test
    public void testGetAllCourses() throws Exception {
        Mockito.when(courseService.getAllCourses()).thenReturn(courses);
        mockMvc.perform(get("/api/v1/course/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Java Programming"))
                .andExpect(jsonPath("$[1].name").value("Data Science with Python"));
    }


    @Test
    public void testAddCourse() throws Exception {
        Course newCourse = new Course(null, "Web Development Bootcamp", "Learn full-stack web development", "Online", "Web Development", 200.0, 499.99, null, Set.of(), null, Set.of());
        ObjectMapper objectMapper = new ObjectMapper();
        String courseJson = objectMapper.writeValueAsString(newCourse);

        mockMvc.perform(post("/api/v1/course/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
                        .principal(() -> "user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Course added successfully"));
    }


    @Test
    public void testUpdateCourse() throws Exception {
        Course updatedCourse = new Course(null, "M", "UpdatedDescription", "Offline", "Art", 120.0, 200.0, null, Set.of(), null, Set.of());
        ObjectMapper objectMapper = new ObjectMapper();
        String courseJson = objectMapper.writeValueAsString(updatedCourse);

        mockMvc.perform(put("/api/v1/course/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
                        .principal(() -> "user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Course updated successfully!"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/api/v1/course/delete/1")
                        .principal(() -> "user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Course deleted successfully!"));
    }



}

