package com.example.finalproject.Config;

import org.springframework.context.annotation.Bean;
import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //Password
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        //User name
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // Disable CSRF for testing purposes (not for production!)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()


                //Reema
                //Auth
                //get All users
                .requestMatchers("/api/v1/auth/get").hasAuthority("ADMIN")
                //delete user
                .requestMatchers("/api/v1/auth/delete/{user_id}").hasAuthority("ADMIN")

                //Tutor
                .requestMatchers("/api/v1/tutor/get").permitAll()
                //Tutor register
                .requestMatchers("/api/v1/tutor/register").permitAll()
                .requestMatchers("/api/v1/tutor/update").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/tutor/delete").hasAuthority("TUTOR")
                //getAllTutorsWithRecommendations
                .requestMatchers("/api/v1/tutor/tutorsWithRecommendations").permitAll()

                //Certificates
                .requestMatchers("/api/v1/certificate/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/certificate/get-my").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/certificate/add").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/certificate/update/{certificate_id}").hasAnyAuthority("ADMIN","TUTOR")
                .requestMatchers("/api/v1/certificate/delete/{certificate_id}").hasAnyAuthority("ADMIN","TUTOR")
                .requestMatchers("/api/v1/certificate/issueCertificate/{certificate_id}").hasAuthority("TUTOR")

                //Student
                .requestMatchers("/api/v1/student/get").hasAuthority("ADMIN") //+tutor??
                .requestMatchers("/api/v1/student/register").permitAll()
                .requestMatchers("/api/v1/student/update").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/student/delete").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/student/studentEnrollment/{course_id}").hasAnyAuthority("STUDENT", "ADMIN")


                //Course
                .requestMatchers("/api/v1/course/get").permitAll()
                .requestMatchers("api/v1/course/get-My-Courses").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/course/add").hasAnyAuthority("TUTOR", "ADMIN")
                .requestMatchers("/api/v1/course/update/{course_id}").hasAnyAuthority("TUTOR", "ADMIN")
                .requestMatchers("/api/v1/course/delete/{course_id}").hasAnyAuthority("TUTOR", "ADMIN")
                .requestMatchers("/api/v1/course/courseFilter/{minPrice}/{maxPrice}").permitAll()
                .requestMatchers("api/v1/course/findCoursesByLearningMethod/{learningMethod}").permitAll()
                .requestMatchers("api/v1/course/mostPopularCourses").permitAll()

                //Document
                .requestMatchers("/api/v1/document/add/{session_id}").hasAnyAuthority("TUTOR", "ADMIN")
                .requestMatchers("/api/v1/document/assignDocumentToStudent/{doc_id}").hasAnyAuthority("STUDENT","ADMIN")


                  //Exam
                .requestMatchers("/api/v1/exam/assignExamToTutor/{exam_id}").hasAuthority("TUTOR")

                //orders
                .requestMatchers("/api/v1/orders/applyDiscount/{order_id}").hasAuthority("ADMIN")

                //review
                .requestMatchers("/api/v1/review/assignReviewToTutor/{tutor_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/review/assignReviewToCourse/{course_id}").hasAuthority("STUDENT")

                //session
                .requestMatchers("/api/v1/session/add-session-by-tutor/{course_id}").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/session/add-session-by-admin/{course_id}/{tutor_id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/session/get-my").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/session/assignSessionToStudent/{session_id}").hasAuthority("STUDENT")

                //zoom
                .requestMatchers("/api/v1/zoom/add/{session_id}").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/zoom/assignZoomToStudent/{zoom_id}").hasAuthority("STUDENT")

          //Reema




                //Omar
                // Document permissions
                .requestMatchers("/api/v1/document/get").hasAnyAuthority("STUDENT", "TUTOR", "ADMIN")
                .requestMatchers("/api/v1/document/update/", "/api/v1/document/delete/").hasAuthority("TUTOR")
                // Session permissions
                .requestMatchers( "/api/v1/session/update/", "/api/v1/session/delete/", "/api/v1/session/start/", "/api/v1/session/cancel/", "/api/v1/session/end/", "/api/v1/session/block/")
                .hasAuthority("TUTOR") // Only tutors can manage sessions
                .requestMatchers("/api/v1/session/get").hasAnyAuthority( "ADMIN","TUTOR")//reema
                .requestMatchers("/api/v1/session/assign/**").hasAnyAuthority("ADMIN", "TUTOR")
                .requestMatchers( "/api/v1/session/students/", "/api/v1/session/max-participants/")
                .hasAnyAuthority("TUTOR", "STUDENT") // Students can view session details

                // Face-to-Face permissions
                .requestMatchers("/api/v1/face/").hasAnyAuthority("TUTOR","ADMIN")

                // Video permissions
                .requestMatchers("/api/v1/video/add/", "/api/v1/video/update/", "/api/v1/video/delete/", "/api/v1/video/increase-price/", "/api/v1/video/decrease-price/", "/api/v1/video/delete-by-course/")
                .hasAuthority("TUTOR") // Only tutors can manage videos
                .requestMatchers("/api/v1/video/get/", "/api/v1/video/price-range/", "/api/v1/video/search/")
                .hasAnyAuthority("TUTOR", "STUDENT") // Both tutors and students can view and search videos

                // Zoom permissions
                .requestMatchers("/api/v1/zoom/add/", "/api/v1/zoom/update/", "/api/v1/zoom/delete/", "/api/v1/zoom/assignZoomToStudent/")
                .hasAuthority("TUTOR") // Only tutors can manage Zoom meetings
                .requestMatchers("/api/v1/zoom/get/").hasAnyAuthority("TUTOR", "STUDENT") // Both tutors and students can view Zoom meetings
          //Omar


                /////////////////////////////// //Renad/////////////////////////////////////////////////

                //Auth
                .requestMatchers("/api/v1/auth/get/users/role/{role}").hasAuthority("ADMIN")

                //Review
                .requestMatchers("/api/v1/review/get").permitAll()
                .requestMatchers("api/v1/review/update/{review_id}").hasAnyAuthority("TUTOR", "ADMIN")
                .requestMatchers("/api/v1/review/delete/{review_id}").hasAuthority("ADMIN")

                //Club
                .requestMatchers("/api/v1/club/get").permitAll()
                .requestMatchers("/api/v1/club/add/{studentId}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/update/{club_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/delete/{club_id}").hasAuthority("ADMIN")

                .requestMatchers("/api/v1/club/get/club/{name}").permitAll()
                .requestMatchers("/api/v1/club/get/details/{club_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/club/students/{club_id}").permitAll()
                .requestMatchers("/api/v1/club/capacity/{capacity}").hasAuthority("STUDENT")


                //UsedItem
                .requestMatchers("/api/v1/usedItem/get").permitAll()
                .requestMatchers("/api/v1/usedItem/add/{sellerId}").hasAnyAuthority("TUTOR", "STUDENT")
                .requestMatchers("/api/v1/usedItem/update/{usedItem_id}").hasAnyAuthority("TUTOR", "STUDENT")
                .requestMatchers("/api/v1/usedItem/delete/{usedItem_id}").hasAuthority("ADMIN")


                //Orders
                .requestMatchers("/api/v1/orders/get").permitAll()
                .requestMatchers("/api/v1/orders/add").hasAnyAuthority("TUTOR", "STUDENT")
                .requestMatchers("/api/v1/orders/update/{orders_id}").hasAnyAuthority("TUTOR","STUDENT")
                .requestMatchers("/api/v1/orders/delete/{orders_id}").hasAnyAuthority("TUTOR","STUDENT")
                .requestMatchers("/api/v1/orders/get/order/{status}").permitAll()
                .requestMatchers("/api/v1/orders/changeStatus/{order_id}/{status}").hasAuthority("ADMIN")


                //Exam
                .requestMatchers("/api/v1/exam/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/exam/get/my").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/exam/add").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/exam/update/{exam_id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/exam/delete/{exam_id}").hasAuthority("ADMIN")



                //myCourse endpoint
                .requestMatchers("/api/v1/course/get/course/{course_id}").hasAuthority("TUTOR")
                .requestMatchers("/api/v1/course/get/tutor/{tutor_id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/course/get/subject/{subject}").permitAll()
                .requestMatchers("/api/v1/course/get/details/{course_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/course/get/course/reviews/{course_id}").permitAll()

                //myTutor endpoint
                .requestMatchers("/api/v1/tutor/get/name/{firstName}").permitAll()
                .requestMatchers("/api/v1/tutor/get/tutor/reviews/{tutor_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/tutor/get/avg/{tutor_id}").hasAuthority("STUDENT")
                .requestMatchers("/api/v1/tutor/get/tutor/products/{tutor_id}").permitAll()
                .requestMatchers("/api/v1/tutor/get/tutors/{gpa}").hasAuthority("STUDENT")





                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic(); // Use basic authentication (for testing purposes)
        return http.build();
    }

}

