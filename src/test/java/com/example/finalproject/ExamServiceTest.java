package com.example.finalproject;

import com.example.finalproject.Model.Exam;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.ExamRepository;
import com.example.finalproject.Repository.TutorRepository;
import com.example.finalproject.Service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

//Renad
public class ExamServiceTest {

    @InjectMocks
    private ExamService examService;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private TutorRepository tutorRepository;

    private Tutor tutor;

    private Exam exam1, exam2, exam3;
    private List<Exam> exams;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setId(1);
        tutor.setFirstName("ahmed");

        exam1 = new Exam();
        exam1.setId(1);
        exam1.setName("Exam1");
        exam1.setScore(80);
        exam1.setMaxScore(100);
        exam1.setTutor(tutor);
        exam1.setDateTaken(LocalDate.now());

        exam2 = new Exam();
        exam2.setId(2);
        exam2.setName("Exam2");
        exam2.setScore(85);
        exam2.setMaxScore(100);
        exam2.setTutor(tutor);
        exam2.setDateTaken(LocalDate.now());

        exam3 = new Exam();
        exam3.setId(3);
        exam3.setName("Exam3");
        exam3.setScore(90);
        exam3.setMaxScore(100);
        exam3.setTutor(null);
        exam3.setDateTaken(LocalDate.now());

        exams = new ArrayList<>();
        exams.add(exam1);
        exams.add(exam2);
    }

    @Test
    public void getMyExamsTest() {
        when(tutorRepository.findTutorById(tutor.getId())).thenReturn(tutor);
        when(examRepository.findAllByTutor(tutor)).thenReturn(exams);
        List<Exam> result = examService.getMyExams(tutor.getId());
        assertEquals(2, result.size());
        assertEquals(exams, result);
        verify(tutorRepository, times(1)).findTutorById(tutor.getId());
        verify(examRepository, times(1)).findAllByTutor(tutor);
    }

    @Test
    public void getAllExamsTest() {
        when(examRepository.findAll()).thenReturn(exams);
        List<Exam> result = examService.getAllExams();
        assertEquals(3, result.size());
        assertEquals(exams, result);
        verify(examRepository, times(1)).findAll();
    }



}
