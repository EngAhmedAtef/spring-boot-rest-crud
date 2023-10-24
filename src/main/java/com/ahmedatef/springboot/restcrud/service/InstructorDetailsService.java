package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.DetailsAddLinkRequest;
import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsResponse;
import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.exception.InstructorDetailsNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.mapper.InstructorDetailsMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.InstructorDetailsRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstructorDetailsService {

    private final InstructorDetailsRepository repository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorDetailsService(InstructorDetailsRepository repository, InstructorRepository instructorRepository) {
        this.repository = repository;
        this.instructorRepository = instructorRepository;
    }

    public List<InstructorDetailsResponse> findAll() {
        List<InstructorDetailsEntity> entities = repository.findAll();
        return entities.stream().map(InstructorDetailsMapper::mapToResponse).toList();
    }

    public InstructorDetailsResponse findById(UUID id) {
        Optional<InstructorDetailsEntity> optional = repository.findById(id);
        if (optional.isPresent())
            return InstructorDetailsMapper.mapToResponse(optional.get());
        else
            throw new InstructorDetailsNotFoundException("Instructor Details id not found - " + id);
    }

    public InstructorDetailsResponse saveAndLinkInstructor(DetailsAddLinkRequest request) {
        InstructorDetailsDTO details = request.getDetails();
        Optional<InstructorEntity> optional = instructorRepository.findById(request.getInstructorId());
        if (optional.isPresent()) {
            InstructorDetailsEntity detailsEntity = MapperUtil.map(details, InstructorDetailsEntity.class);
            detailsEntity.setInstructor(optional.get());
            InstructorDetailsEntity savedEntity = repository.save(detailsEntity);
            return InstructorDetailsMapper.mapToResponse(savedEntity);
        } else
            throw new InstructorNotFoundException("Instructor id not found - " + request.getInstructorId());
    }

    public InstructorDetailsResponse update(InstructorDetailsDTO dto) {
        Optional<InstructorDetailsEntity> optional = repository.findById(dto.getId());
        if (optional.isPresent()) {
            InstructorDetailsEntity entity = optional.get();
            entity.setHobbies(dto.getHobbies());
            entity.setYoutubeChannel(dto.getYoutubeChannel());
            InstructorDetailsEntity savedEntity = repository.save(entity);
            return InstructorDetailsMapper.mapToResponse(savedEntity);
        } else
            throw new InstructorDetailsNotFoundException("Instructor Details id not found - " + dto.getId());
    }

    public void deleteById(UUID id) {
        Optional<InstructorDetailsEntity> optional = repository.findById(id);
        if (optional.isPresent())
            repository.delete(optional.get());
        else
            throw new InstructorDetailsNotFoundException("Instructor Details id not found - " + id);
    }
}
