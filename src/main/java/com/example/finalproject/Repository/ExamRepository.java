package com.example.finalproject.Repository;

import com.example.finalproject.Model.Exam;
import com.example.finalproject.Model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Exam findExamById(Integer id);

    List<Exam> findAllByTutor(Tutor tutor);
}
