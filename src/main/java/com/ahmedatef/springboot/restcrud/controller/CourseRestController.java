package com.ahmedatef.springboot.restcrud.controller;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.ahmedatef.springboot.restcrud.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CourseService service;

    @Autowired
    public CourseRestController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        return service.findAll();
    }

    @GetMapping("/{courseId}")
    public CourseResponse getCourseById(@PathVariable UUID courseId) {
        return service.findById(courseId);
    }

    @PostMapping
    public CourseResponse addCourse(@RequestBody CourseAddAndLinkInstructorRequest addRequest) {
        return service.saveCourseAndLinkInstructor(addRequest);
    }

    @PutMapping
    public CourseResponse updateCourse(@RequestBody CourseDTO course) {
        return service.update(course);
    }

    @DeleteMapping("/{courseId}")
    public String deleteCourse(@PathVariable UUID courseId) {
        service.deleteById(courseId);
        return "Deleted course id - " + courseId;
    }

    @GetMapping("/students")
    public List<CourseNameStartDateEnrolledStudentsDTO> getNameStartDateEnrolledStudents() {
        return service.getNameStartDateEnrolledStudents();
    }

    @GetMapping("/level")
    public List<CourseLevelEnrolledStudents> getCourseLevelEnrolledStudents(@RequestParam("level") String level) {
        return service.getCourseLevelEnrolledStudents(level);
    }
}