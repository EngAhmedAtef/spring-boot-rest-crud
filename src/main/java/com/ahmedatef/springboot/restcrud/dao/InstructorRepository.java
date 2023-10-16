package com.ahmedatef.springboot.restcrud.dao;

import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Integer> {
}
