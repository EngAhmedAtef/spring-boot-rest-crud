package com.ahmedatef.springboot.restcrud.dao;

import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}
