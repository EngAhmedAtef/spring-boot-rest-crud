package com.ahmedatef.springboot.restcrud.repository;

import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorDetailsRepository extends JpaRepository<InstructorDetailsEntity, UUID> {
}
