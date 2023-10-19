package com.ahmedatef.springboot.restcrud.controller;

import com.ahmedatef.springboot.restcrud.dto.InstructorAndEnrolledStudentsDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorNameAndCourseNamesDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorResponse;
import com.ahmedatef.springboot.restcrud.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorRestController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorRestController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<InstructorResponse> getInstructors() {
        return instructorService.findAll();
    }

    @GetMapping("/{instructorId}")
    public InstructorResponse getInstructorById(@PathVariable int instructorId) {
        return instructorService.findById(instructorId);
    }

    @PostMapping
    public InstructorResponse addInstructor(@RequestBody InstructorDTO instructor) {
        return instructorService.save(instructor);
    }

    @PutMapping
    public InstructorResponse updateInstructor(@RequestBody InstructorDTO instructor) {
        return instructorService.update(instructor);
    }

    @DeleteMapping("/{instructorId}")
    public String deleteInstructor(@PathVariable int instructorId) {
        instructorService.deleteById(instructorId);
        return "Deleted instructor id - " + instructorId;
    }

    @GetMapping("/courses")
    public List<InstructorNameAndCourseNamesDTO> getInstructorAndCourses() {
        return instructorService.getInstructorAndCourses();
    }

    @GetMapping("/students")
    public List<InstructorAndEnrolledStudentsDTO> getInstructorAndEnrolledStudents() {
        return instructorService.getInstructorAndEnrolledStudents();
    }

}