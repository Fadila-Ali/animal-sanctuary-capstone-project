package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Volunteer;

import java.util.List;

public interface VolunteerService {
    Volunteer saveVolunteer(Volunteer volunteer);
    Volunteer getVolunteerById(Long id);
    List<Volunteer> getAllVolunteers();
    List<Volunteer> getVolunteersByUserId(Long userId);
    void deleteVolunteer(Long id);
    void addUserToVolunteer(Long volunteerId, User user);
}
