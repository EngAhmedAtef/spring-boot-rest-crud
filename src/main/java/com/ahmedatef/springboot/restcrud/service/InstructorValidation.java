package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class InstructorValidation {

    private final InstructorRepository repository;

    public InstructorValidation(InstructorRepository repository) {
        this.repository = repository;
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        return (repository.findByPhoneNumber(phoneNumber) != null);
    }

    public boolean validateEmailAddress(String emailAddress) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }

    public boolean validateYoutubeChannel(String youtubeChannel) {
        return (youtubeChannel != null && !youtubeChannel.isEmpty() && !youtubeChannel.isBlank());
    }

}