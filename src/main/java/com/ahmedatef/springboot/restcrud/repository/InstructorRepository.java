package com.ahmedatef.springboot.restcrud.repository;

import com.ahmedatef.springboot.restcrud.dto.InstructorNameAndCourseNamesDTO;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<InstructorEntity, Integer> {

    @Query("""
            SELECT NEW com.ahmedatef.springboot.restcrud.dto.InstructorNameAndCourseNamesDTO
            (i.firstName || ' ' || i.lastName, LISTAGG(c.name, ','))
            FROM InstructorEntity i JOIN i.courses c GROUP BY (i.firstName || ' ' || i.lastName)
            """)
    List<InstructorNameAndCourseNamesDTO> getInstructorCoursesNames();

    InstructorEntity findByPhoneNumber(String phoneNumber);

}