package com.example.finalproject;

import com.example.finalproject.Model.Exam;
import com.example.finalproject.Model.Tutor;
import com.example.finalproject.Repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest

//Renad
public class ExamRepositoryTest {

    @Autowired
    private ExamRepository examRepository;

    private Tutor tutor;
    private Exam exam1, exam2, exam3;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setId(1);
        tutor.setFirstName("renad");

        exam1 = new Exam();
        exam1.setId(1);
        exam1.setName("Math Exam");
        exam1.setScore(85);
        exam1.setMaxScore(100);
        exam1.setDateTaken(LocalDate.now());
        exam1.setTutor(tutor);

        exam2 = new Exam();
        exam2.setId(2);
        exam2.setName("Science Exam");
        exam2.setScore(90);
        exam2.setMaxScore(100);
        exam2.setDateTaken(LocalDate.now());
        exam2.setTutor(tutor);

        exam3 = new Exam();
        exam3.setId(3);
        exam3.setName("History Exam");
        exam3.setScore(75);
        exam3.setMaxScore(100);
        exam3.setDateTaken(LocalDate.now());
        exam3.setTutor(null);

        examRepository.save(exam1);
        examRepository.save(exam2);
        examRepository.save(exam3);
    }

    @Test
    void testFindExamById() {
        Exam foundExam = examRepository.findExamById(exam1.getId());
        assertThat(foundExam).isNotNull();
        assertThat(foundExam.getName()).isEqualTo("Math Exam");
    }

//    @Test
//    void testFindAllByTutor() {
//        List<Exam> exams = examRepository.findAllByTutor(tutor);
//        assertThat(exams).hasSize(2);
//        assertThat(exams).extracting(Exam::getName).containsExactly("Math Exam", "Science Exam");
//    }
//
//    @Test
//    void testFindAllByTutor_NoExams() {
//        Tutor otherTutor = new Tutor();
//        otherTutor.setId(2);
//        otherTutor.setFirstName("ahmed");
//        List<Exam> exams = examRepository.findAllByTutor(otherTutor);
//        assertThat(exams).isEmpty();
//    }

}
