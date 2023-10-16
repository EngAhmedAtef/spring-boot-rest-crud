package com.ahmedatef.springboot.restcrud.dao;

import com.ahmedatef.springboot.restcrud.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
}
