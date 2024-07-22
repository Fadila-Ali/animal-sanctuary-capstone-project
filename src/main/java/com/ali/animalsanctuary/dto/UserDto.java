package com.ali.animalsanctuary.dto;

import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.entity.Volunteer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private  Long id;

    @NotEmpty(message = "Username is required")
    private  String username;

    @NotEmpty(message = "Email is required")
    @Email
    private  String email;

    @NotEmpty(message = "Password is required")
    private  String password;

    @NotEmpty(message = "Firstname is required")
    private  String firstname;

    @NotEmpty(message = "Lastname is required")
    private  String lastname;

    private String createdAt;
    private String updatedAt;

    private MultipartFile image;

    private List<Volunteer> volunteerTasks;

    private List<Visit> visits;

    public boolean isAdminRegistration() {
        return email.endsWith(("@admin.com"));
    }

}
