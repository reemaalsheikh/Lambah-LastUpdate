package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.CourseRepository;
import com.example.finalproject.Repository.SessionRepository;
import com.example.finalproject.Repository.TutorRepository;
import com.example.finalproject.Repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final TutorRepository tutorRepository;



    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    // Omar
    //Assign video to session
    public void addVideo(Video video, Integer sessionId, User user) {
        // Verify that the user is a tutor
        Tutor tutor = tutorRepository.findTutorById(user.getId());
        if (tutor == null) {
            throw new ApiException("Only tutors can add videos.");
        }

        // Fetch and validate session
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) {
            throw new ApiException("Session not found.");
        }

        // Ensure the tutor owns the session
        if (!session.getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only add videos to your own sessions.");
        }

        // Fetch and validate the course from the video object
        Course course = courseRepository.findCourseById(video.getCourse().getId());
        if (course == null) {
            throw new ApiException("Course not found.");
        }

        // Ensure the tutor owns the course
        if (!course.getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only add videos to your own courses.");
        }

        // Associate video with both the session and the course
        video.setSession(session);
        video.setCourse(course);

        // Save the video
        videoRepository.save(video);
    }



    public Video updateVideo(Integer id, Video video, User user) {
        Video v = videoRepository.findVideoById(id);
        if (v == null) {
            throw new ApiException("Video not found.");
        }
        if (!v.getSession().getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only update videos for your own sessions.");
        }
        v.setTitle(video.getTitle());
        v.setDescription(video.getDescription());
        v.setPrice(video.getPrice());
        return videoRepository.save(v);
    }

    public void deleteVideo(Integer id, User user) {
        Video v = videoRepository.findVideoById(id);
        if (v == null) {
            throw new ApiException("Video not found.");
        }
        // Ensure the video belongs to the current tutor
        if (!v.getSession().getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only delete videos from your own sessions.");
        }
        videoRepository.deleteById(id);
    }


    //Omar
    public Video getVideoById(Integer id, User user) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Video not found"));

        // Ensure the video belongs to the current tutor
        if (!video.getSession().getTutor().getId().equals(user.getId())) {
            throw new ApiException("Access denied. You can only view your own videos.");
        }

        return video;
    }
    //Omar
    public List<Video> getVideosByCourseId(Integer courseId, User user) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("Course not found.");
        }
        // Ensure that the tutor owns the course
        if (!course.getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only view videos for courses you own.");
        }

        return videoRepository.findAllByCourse(course);
    }
    //Omar
    public List<Video> getVideosByPriceRange(double minPrice, double maxPrice) {
        return videoRepository.findAllByPriceBetween(minPrice, maxPrice);
    }
    //Omar
    public List<Video> searchVideosByTitle(String keyword) {
        return videoRepository.findAllByTitleContainingIgnoreCase(keyword);
    }

    //Omar
    // Increase video price by a percentage
    public void increaseVideoPriceByPercentage(Integer id, double percentage, User user) {
        Video video = videoRepository.findVideoById(id);
        if (video == null) {
            throw new ApiException("Video not found.");
        }


        // Ensure the tutor owns the video
        if (!video.getSession().getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only update videos for your own sessions.");
        }

        double newPrice = video.getPrice() + (video.getPrice() * (percentage / 100));
        video.setPrice(newPrice);
        videoRepository.save(video);
    }

    //Omar
    // Decrease video price by a percentage
    public void decreaseVideoPriceByPercentage(Integer id, double percentage, User user) {
        Video video = videoRepository.findVideoById(id);
        if (video == null) {
            throw new ApiException("Video not found.");
        }
        // Ensure the tutor owns the video
        if (!video.getSession().getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only update videos for your own sessions.");
        }

        double newPrice = video.getPrice() - (video.getPrice() * (percentage / 100));
        if (newPrice < 0) {
            throw new ApiException("Price cannot be negative.");
        }
        video.setPrice(newPrice);
        videoRepository.save(video);
    }

    //Omar
    // delete all videos by course ID
    public void deleteVideosByCourseId(Integer courseId, User user) {
        Course course = courseRepository.findCourseById(courseId);
        if (course == null) {
            throw new ApiException("Course not found.");
        }

        // Ensure that the tutor owns the course
        if (!course.getTutor().getId().equals(user.getId())) {
            throw new ApiException("You can only delete videos from courses you own.");
        }

        List<Video> videos = videoRepository.findAllByCourse(course);
        if (videos.isEmpty()) {
            throw new ApiException("No videos found for the course.");
        }
        videoRepository.deleteAll(videos);
    }
}
