package com.ali.animalsanctuary.security;

import com.ali.animalsanctuary.entity.Role;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service class that implements {@link UserDetailsService} to provide custom user details retrieval for authentication.
 *
 * This class is responsible for loading user-specific data during authentication based on the user's email. It integrates
 * with the {@link UserRepository} to fetch user details from the database and maps user roles to authorities.
 *
 * <p>Dependencies:
 * <ul>
 *     <li>{@code UserRepository}: Repository interface used to access user data.</li>
 * </ul>
 *
 * <p>Constructors:
 * <ul>
 *     <li>{@link #CustomUserDetailsService(UserRepository)}: Initializes the service with the given {@code UserRepository}.</li>
 * </ul>
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #loadUserByUsername(String)}: Loads a {@link UserDetails} object by email. Throws {@link UsernameNotFoundException}
 *     if the user is not found.</li>
 *     <li>{@link #mapRolesToAuthorities(Collection)}: Converts a collection of {@link Role} objects to a collection of
 *     {@link GrantedAuthority} objects.</li>
 * </ul>
 *
 * @see UserDetailsService
 * @see UserDetails
 * @see UserRepository
 * @see CustomUserDetails
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@code CustomUserDetailsService} with the specified {@code UserRepository}.
     *
     * @param userRepository the repository used to fetch user data.
     */
    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Loads user-specific data by email. This method fetches the user from the repository and creates a {@link CustomUserDetails}
     * instance with the user's details and authorities. If the user is not found, a {@link UsernameNotFoundException} is thrown.
     *
     * @param email the email of the user to be loaded.
     * @return a {@link CustomUserDetails} instance containing the user's details and authorities.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) mapRolesToAuthorities(user.getRoles());

            System.out.println("User: " + user.getEmail() + ", Roles: " + authorities);

            return new CustomUserDetails(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    /**
     * Converts a collection of {@link Role} objects to a collection of {@link GrantedAuthority} objects.
     *
     * @param roles a collection of {@link Role} objects.
     * @return a collection of {@link GrantedAuthority} objects representing the roles.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
