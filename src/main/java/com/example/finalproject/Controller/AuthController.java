package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.User;
import com.example.finalproject.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @GetMapping("/get")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }


    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity delete( @PathVariable Integer user_id) {
        authService.deleteUser(user_id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
    }



    /*Renad*/
    @GetMapping("/get/users/role/{role}")
    public ResponseEntity getUsersByRole(@AuthenticationPrincipal User user, @PathVariable String role) {
        return ResponseEntity.status(200).body(authService.getUsersByRole(user.getId(),role));
    }

}
