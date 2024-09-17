package com.example.finalproject;

import com.example.finalproject.Model.Course;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.TutorRepository;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

//Reema
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TutorRepository tutorRepository;


    private Course course1, course2;
    private Tutor tutor;
    private List<Course> courses;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setId(1);
        tutor.setHasTakenExam(true);

        course1 = new Course();
        course1.setId(1);
        course1.setName("Course 1");
        course1.setTutor(tutor);
        course1.setPrice(100);

        course2 = new Course();
        course2.setId(2);
        course2.setName("Course 2");
        course2.setTutor(tutor);
        course2.setPrice(200);

        courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
    }

    @Test
    public void getAllCoursesTest() {
        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> courseList = courseService.getAllCourses();
        assertEquals(2, courseList.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void getMyCoursesTest() {
        when(tutorRepository.findTutorById(tutor.getId())).thenReturn(tutor);
        when(courseRepository.findAllByTutor(tutor)).thenReturn(courses);
        List<Course> tutorCourses = courseService.getMyCourses(tutor.getId());
        assertEquals(2, tutorCourses.size());
        verify(tutorRepository, times(1)).findTutorById(tutor.getId());
        verify(courseRepository, times(1)).findAllByTutor(tutor);
    }

    @Test
    public void addCourseTest() {
        when(tutorRepository.findTutorById(tutor.getId())).thenReturn(tutor);
        courseService.addCourse(course1, tutor.getId());
        verify(tutorRepository, times(1)).findTutorById(tutor.getId());
        verify(courseRepository, times(1)).save(course1);
    }

    @Test
    public void deleteCourseTest() {
        when(tutorRepository.findTutorById(tutor.getId())).thenReturn(tutor);
        when(courseRepository.findCourseById(course1.getId())).thenReturn(course1);
        courseService.deleteCourse(tutor.getId(), course1.getId());
        verify(courseRepository, times(1)).delete(course1);
    }

}
