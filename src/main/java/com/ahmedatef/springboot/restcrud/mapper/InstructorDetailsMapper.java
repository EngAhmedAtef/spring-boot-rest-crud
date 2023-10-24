package com.ahmedatef.springboot.restcrud.mapper;

import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsResponse;
import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;

public final class InstructorDetailsMapper {

    public static InstructorDetailsResponse mapToResponse(InstructorDetailsEntity entity) {
        InstructorDetailsDTO dto = new InstructorDetailsDTO(
                entity.getId(),
                entity.getYoutubeChannel(),
                entity.getHobbies()
        );
        return new InstructorDetailsResponse(
                entity.getInstructor().getId(),
                entity.getInstructor().getFirstName(),
                entity.getInstructor().getLastName(),
                dto
        );
    }

}