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

/**
 * Data Transfer Object (DTO) for transferring {@link} data between layers.
 *
 * This class is used to encapsulate the data of a {@link} entity for use in data transfer operations,
 * including user registration, updates, and retrieval. It includes validation constraints to ensure that
 * the required fields are provided and properly formatted.
 *
 * <p>Fields:
 * <ul>
 *     <li>{@link #id}: The unique identifier of the user.</li>
 *     <li>{@link #username}: The username of the user (required).</li>
 *     <li>{@link #email}: The email address of the user (required and must be a valid email format).</li>
 *     <li>{@link #password}: The password for the user (required).</li>
 *     <li>{@link #firstname}: The first name of the user (required).</li>
 *     <li>{@link #lastname}: The last name of the user (required).</li>
 *     <li>{@link #createdAt}: The timestamp of when the user was created.</li>
 *     <li>{@link #updatedAt}: The timestamp of when the user was last updated.</li>
 *     <li>{@link #volunteerTasks}: A list of {@link Volunteer} tasks associated with the user.</li>
 *     <li>{@link #visits}: A list of {@link Visit} entities associated with the user.</li>
 * </ul>
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #isAdminRegistration()}: Determines if the user registration email indicates an admin account.</li>
 * </ul>
 *
 * @see Visit
 * @see Volunteer
 */

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

//    private MultipartFile profile_img;

    private List<Volunteer> volunteerTasks;

    private List<Visit> visits;

    /**
     * Determines if the registration email indicates an admin account.
     *
     * @return {@code true} if the email ends with "@admin.com", {@code false} otherwise.
     */
    public boolean isAdminRegistration() {

        return email.endsWith(("@admin.com"));
    }

}
