package com.ahmedatef.springboot.restcrud.controller;

import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.service.CourseServiceImpl;
import com.ahmedatef.springboot.restcrud.service.InstructorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CourseRestController {

    private final CourseServiceImpl service;
    private final InstructorServiceImpl instructorService;

    @Autowired
    public CourseRestController(CourseServiceImpl service, InstructorServiceImpl instructorService) {
        this.service = service;
        this.instructorService = instructorService;
    }

    @GetMapping("/courses")
    public List<CourseEntity> getCourses() {
        return service.findAll();
    }

    @GetMapping("/courses/{courseId}")
    public CourseEntity getCourseById(@PathVariable UUID courseId) {
        return service.findById(courseId);
    }

    @PostMapping("/courses")
    public CourseEntity addCourse(@RequestBody CourseEntity course) {
        InstructorEntity instructor = course.getInstructor();
        InstructorEntity dbInstructor = instructorService.findById(instructor.getId());
        course.setInstructor(dbInstructor);

        return service.save(course);
    }

    @PutMapping("/courses")
    public CourseEntity updateCourse(@RequestBody CourseEntity course) {
        CourseEntity dbCourse = service.findById(course.getId());
        course.setInstructor(dbCourse.getInstructor());
        return service.save(course);
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable UUID courseId) {
        CourseEntity course = service.findById(courseId);
        service.deleteById(courseId);
        return "Delete course id - " + courseId;
    }
}