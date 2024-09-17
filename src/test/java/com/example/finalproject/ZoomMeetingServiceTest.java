package com.example.finalproject;


import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.StudentRepository;
import com.example.finalproject.Repository.TutorRepository;
import com.example.finalproject.Repository.ZoomMeetingRepository;
import com.example.finalproject.Service.ZoomMeetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZoomMeetingServiceTest {

    @Mock
    private ZoomMeetingRepository zoomMeetingRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TutorRepository tutorRepository;

    @InjectMocks
    private ZoomMeetingService zoomMeetingService;

    private ZoomMeeting zoomMeeting;
    private User tutorUser;
    private Tutor tutor;
    private Session session;
    private Student student;

    @BeforeEach
    public void setUp() {
        // Create the tutor user
        tutorUser = new User(1, "tutor1", "password", "TUTOR", "Male", LocalDate.of(1990, 1, 1), "Undergraduate", LocalDate.now());

        // Create the tutor linked to the user
       // tutor = new Tutor(1, "tutor1", "password", "TUTOR", "0512345678", null, 3.5, null, null, true, true, tutorUser, null, null, null, null, null, null, null);

        // Create the session, ensuring the tutor is linked
        session = new Session(1, 20, "description", "VIDEO", 10, 10, null, null,  tutor, null, null, null, null, null);

        // Create the student
        student = new Student(1, "student1", "password", "STUDENT", true, null, null, null, null, null, null, null, null, null);

        // Create the Zoom meeting linked to the tutor, student, and session
       // zoomMeeting = new ZoomMeeting(1, "http://zoomlink.com", 150.0, new Date(), student, tutor, session);
    }

    @Test
    public void testAddZoomMeeting_Success() {
        // Mock the repository to return the tutor and session
        when(tutorRepository.findTutorById(anyInt())).thenReturn(tutor);
        when(sessionRepository.findSessionById(anyInt())).thenReturn(session);
        when(zoomMeetingRepository.save(any(ZoomMeeting.class))).thenReturn(zoomMeeting);

        // Perform the test and verify the success case
        //assertDoesNotThrow(() -> zoomMeetingService.addZoom(zoomMeeting, session.getId(), tutorUser));

        verify(zoomMeetingRepository, times(1)).save(zoomMeeting);
    }

//
//    @Test
//    public void testAddZoomMeeting_TutorNotFound() {
//        when(tutorRepository.findTutorById(anyInt())).thenReturn(null);
//
//        ApiException exception = assertThrows(ApiException.class, () ->
//                zoomMeetingService.addZoom(zoomMeeting, session.getId(), tutorUser));
//
//        assertEquals("Only tutors can add Zoom meetings.", exception.getMessage());
//    }

//    @Test
//    public void testAddZoomMeeting_SessionNotFound() {
//        when(tutorRepository.findTutorById(anyInt())).thenReturn(tutor);
//        when(sessionRepository.findSessionById(anyInt())).thenReturn(null);
//
//        ApiException exception = assertThrows(ApiException.class, () ->
//                zoomMeetingService.addZoom(zoomMeeting, session.getId(), tutorUser));
//
//        assertEquals("Session not found.", exception.getMessage());
//    }

    @Test
    public void testUpdateZoomMeeting_Success() {
        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(zoomMeeting);
        when(zoomMeetingRepository.save(any(ZoomMeeting.class))).thenReturn(zoomMeeting);

        ZoomMeeting updatedZoom = zoomMeetingService.updateZoom(zoomMeeting.getMeetingId(), zoomMeeting, tutorUser);

        assertNotNull(updatedZoom);
        verify(zoomMeetingRepository, times(1)).save(zoomMeeting);
    }

    @Test
    public void testUpdateZoomMeeting_ZoomNotFound() {
        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () ->
                zoomMeetingService.updateZoom(zoomMeeting.getMeetingId(), zoomMeeting, tutorUser));

        assertEquals("Zoom meeting not found.", exception.getMessage());
    }

    @Test
    public void testDeleteZoomMeeting_Success() {
        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(zoomMeeting);

        assertDoesNotThrow(() -> zoomMeetingService.deleteZoom(zoomMeeting.getMeetingId(), tutorUser));

        verify(zoomMeetingRepository, times(1)).deleteById(zoomMeeting.getMeetingId());
    }

    @Test
    public void testDeleteZoomMeeting_ZoomNotFound() {
        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () ->
                zoomMeetingService.deleteZoom(zoomMeeting.getMeetingId(), tutorUser));

        assertEquals("Zoom meeting not found.", exception.getMessage());
    }

//    @Test
//    public void testAssignZoomToStudent_Success() {
//        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(zoomMeeting);
//        when(tutorRepository.findTutorById(anyInt())).thenReturn(tutor);
//        when(studentRepository.findStudentById(anyInt())).thenReturn(student);
//        when(studentRepository.findStudentById(anyInt())).thenReturn(student);
//
//        assertDoesNotThrow(() -> zoomMeetingService.assignZoomToStudent(zoomMeeting.getMeetingId(), student.getId(), tutorUser));
//
//        verify(zoomMeetingRepository, times(1)).save(zoomMeeting);
//    }

//    @Test
//    public void testAssignZoomToStudent_ZoomNotFound() {
//        when(zoomMeetingRepository.findZoomMeetingByMeetingId(anyInt())).thenReturn(null);
//
//        ApiException exception = assertThrows(ApiException.class, () ->
//                zoomMeetingService.assignZoomToStudent(zoomMeeting.getMeetingId(), student.getId(), tutorUser));
//
//        assertEquals("Zoom meeting not found.", exception.getMessage());
//    }
}
