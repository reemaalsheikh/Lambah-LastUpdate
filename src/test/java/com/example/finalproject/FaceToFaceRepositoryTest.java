package com.example.finalproject;

import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.FaceToFaceRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

//Omar
public class FaceToFaceRepositoryTest {

    @Autowired
    FaceToFaceRepository faceToFaceRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    StudentRepository studentRepository;

    User user;

    Tutor tutor;
    Session session;
    FaceToFace faceToFace;
    Student student;

    @BeforeEach
    void setUp() {
        user = new User(1, "JohnDoe", "password123", "TUTOR", "Male", LocalDate.of(1990, 1, 1), "Undergraduate", LocalDate.now());

//        tutor = new Tutor(1, "Tutor Name", "password123", "TUTOR",
//                "0512345678", null, 4.5, null, null,
//                true, true, user, null, null, null,
//                null, null, null, null);

        session = new Session(
                1, 2.0, "Session 1", "FACE_TO_FACE",
                10, 150.0, "ACTIVE", null, tutor, null,
                null, null, null, null
        );

        faceToFace = new FaceToFace(1, "Meeting Room 1", 50, session, tutor, student);

        // Save the required entities
        tutorRepository.save(tutor);
        sessionRepository.save(session);
        faceToFaceRepository.save(faceToFace);
    }


    @Test
    public void saveFaceToFaceTest() {
        // Assert that the FaceToFace entity was saved and can be found
        Optional<FaceToFace> savedFaceToFace = faceToFaceRepository.findById(faceToFace.getId());
        assertThat(savedFaceToFace).isPresent();
        assertThat(savedFaceToFace.get().getLocation()).isEqualTo(faceToFace.getLocation());
    }

    @Test
    public void deleteFaceToFaceTest() {
        // Delete the FaceToFace entity
        faceToFaceRepository.delete(faceToFace);

        // Assert that the entity is no longer in the database
        Optional<FaceToFace> deletedFaceToFace = faceToFaceRepository.findById(faceToFace.getId());
        assertThat(deletedFaceToFace).isEmpty();
    }
}

