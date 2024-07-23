package com.ali.animalsanctuary.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Custom implementation of the Spring Security {@link User} class that includes additional user details.
 *
 * This class extends {@link User} to add a custom field for storing the user ID. It is used to integrate
 * additional user information with Spring Security's authentication and authorization mechanisms.
 *
 * <p>Attributes:
 * <ul>
 *     <li>{@code id}: A unique identifier for the user.</li>
 * </ul>
 *
 * <p>Constructors:
 * <ul>
 *     <li>{@link #CustomUserDetails(Long, String, String, Collection)}: Initializes a new instance of the class with
 *     the specified ID, username, password, and authorities.</li>
 * </ul>
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #getId()}: Returns the unique identifier of the user.</li>
 * </ul>
 *
 * @see User
 * @see GrantedAuthority
 */

public class CustomUserDetails extends User {

    private final Long id;

    /**
     * Constructs a new {@code CustomUserDetails} instance with the specified ID, username, password, and authorities.
     *
     * @param id the unique identifier of the user.
     * @param username the username of the user.
     * @param password the password of the user.
     * @param authorities the authorities granted to the user.
     */
    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the unique ID of the user.
     */
    public Long getId() {
        return id;
    }
}
