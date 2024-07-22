package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Volunteer;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.repository.VolunteerRepository;
import com.ali.animalsanctuary.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public List<Volunteer> getVolunteersByUserId(Long userId) {
        return volunteerRepository.findAll().stream()
                .filter(volunteer -> volunteer.getUsers().stream()
                        .anyMatch(user -> user.getId().equals(userId)))
                .toList();
    }

    @Override
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }

    @Override
    public void addUserToVolunteer(Long volunteerId, User user) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer work not found"));
        volunteer.getUsers().add(user);
        volunteerRepository.save(volunteer);
    }
}
