package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.User;
import com.example.finalproject.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> getAllUsers(){
        return authRepository.findAll();
    }


    public void deleteUser (Integer user_id) {
        User users = authRepository.findUserById(user_id);

        if(users == null) {
            throw new ApiException("User id not found");
        }

        authRepository.delete(users);
       // authRepository.deleteById(user_id);
    }



    /*Renad*/
    public List<User> getUsersByRole(Integer auth_id,String role) {
        User user=authRepository.findUserById(auth_id);
        if(user == null) {
            throw new ApiException("User id not found");
        }
        if(role.isEmpty()) {
            throw new ApiException("Role is empty");
        }
        return authRepository.findUserByRole(role);
    }



}
