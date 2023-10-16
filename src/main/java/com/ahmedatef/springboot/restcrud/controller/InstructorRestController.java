package com.ahmedatef.springboot.restcrud.controller;

import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.service.InstructorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InstructorRestController {

    private final InstructorServiceImpl instructorService;

    @Autowired
    public InstructorRestController(InstructorServiceImpl instructorService) {
        this.instructorService = instructorService;
    }

    // Expose the instructors endpoint and return a list of instructors
    @GetMapping("/instructors")
    public List<InstructorEntity> getInstructors() {
        return instructorService.findAll();
    }

    // Expose the instructors endpoint and return a single instructor
    @GetMapping("/instructors/{instructorId}")
    public InstructorEntity getInstructorById(@PathVariable int instructorId) {
        return instructorService.findById(instructorId);
    }

    // Expose the instructors endpoint and add a new instructor
    @PostMapping("/instructors")
    public InstructorEntity addInstructor(@RequestBody InstructorEntity instructor) {
        // Set the id to 0, so the merge() adds a new instructor
        instructor.setId(0);
        return instructorService.save(instructor);
    }

    // Expose the instructors endpoint to update an instructor
    @PutMapping("/instructors")
    public InstructorEntity updateInstructor(@RequestBody InstructorEntity instructor) {
        instructorService.findById(instructor.getId());
        return instructorService.save(instructor);
    }

    // Expose the instructors endpoint to delete an instructor by id
    @DeleteMapping("/instructors/{instructorId}")
    public String deleteInstructor(@PathVariable int instructorId) {
        instructorService.findById(instructorId);
        instructorService.deleteById(instructorId);
        return "Deleted employee id - " + instructorId;
    }

}