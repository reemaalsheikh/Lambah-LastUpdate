package com.example.finalproject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.finalproject.Controller.VideoController;
import com.example.finalproject.Model.User;
import com.example.finalproject.Model.Video;
import com.example.finalproject.Service.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VideoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//Omar
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VideoService videoService;

    private List<Video> videos;

    private Video video1;
    private Video video2;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "tutor1", "password123", "TUTOR", "Male", LocalDate.of(1990, 1, 1), "Undergraduate", LocalDate.now());
        video1 = new Video(1, "Sample Video 1", "Description 1", 100.0, null, null, null);
        video2 = new Video(2, "Sample Video 2", "Description 2", 200.0, null, null, null);

        videos = Arrays.asList(video1, video2);
    }


    @Test
    public void testGetAllVideos() throws Exception {
        List<Video> videos = Arrays.asList(video1, video2);

        when(videoService.getAllVideos()).thenReturn(videos);

        mockMvc.perform(get("/api/v1/video/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Sample Video 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Sample Video 2"));
    }

    @Test
    public void testDeleteVideo() throws Exception {
        mockMvc.perform(delete("/api/v1/video/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Video deleted successfully"));
    }
}
