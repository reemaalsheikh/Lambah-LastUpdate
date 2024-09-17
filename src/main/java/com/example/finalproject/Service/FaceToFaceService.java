package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.FaceToFaceRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaceToFaceService {

    private final FaceToFaceRepository faceRepository;
    private final SessionRepository sessionRepository;
    private final TutorRepository tutorRepository;


    public List<FaceToFace> getAllFaceMeeting() {
        return faceRepository.findAll();
    }


//Omar
    public void addFaceMeeting(FaceToFace face, Integer sessionId, User user) {
        // Find the tutor by user ID
        Tutor tutor = tutorRepository.findTutorById(user.getId());
        if (tutor == null) {
            throw new ApiException("Only tutors can add face-to-face meetings.");
        }

        // Find the session by sessionId
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found.");
        }

        // Ensure the tutor owns the session
        if (!session.getTutor().getId().equals(tutor.getId())) {
            throw new ApiException("You can only add meetings to your own sessions.");
        }
        face.setSession(session);
        face.setTutor(tutor);
        faceRepository.save(face);
    }

    public void updateFaceMeeting(Integer id, FaceToFace face, User user) {
        FaceToFace f = faceRepository.findFaceToFaceById(id);
        if (f == null) {
            throw new ApiException("Face-to-face meeting not found");
        }

        // Ensure that only the tutor who created the meeting can update it
        if (!f.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only update your own meetings");
        }

        f.setLocation(face.getLocation());
        f.setPrice(face.getPrice());
        faceRepository.save(f);
    }

    public void deleteFaceMeeting(Integer id, User user) {
        FaceToFace f = faceRepository.findFaceToFaceById(id);
        if (f == null) {
            throw new ApiException("Face-to-face meeting not found");
        }

        // Ensure that only the tutor who created the meeting can delete it
        if (!f.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("You can only delete your own meetings");
        }

        faceRepository.deleteById(id);
    }

    public FaceToFace findFaceById(Integer id, User user) {
        FaceToFace face = faceRepository.findFaceToFaceById(id);
        if (face == null) {
            throw new ApiException("Face-to-face meeting not found");
        }

        // Ensure the tutor is the one who created the meeting
        if (!face.getTutor().getUser().getId().equals(user.getId())) {
            throw new ApiException("Access denied: You can only view your own meetings.");
        }

        return face;
    }

}

