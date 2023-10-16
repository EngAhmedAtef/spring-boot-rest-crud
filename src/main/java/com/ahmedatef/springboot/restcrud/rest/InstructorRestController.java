package com.ahmedatef.springboot.restcrud.rest;

import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
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
        // Set the id to 0, so we ensure that the merge() will add a new instructor instead of updating
        // in case the client sends an id
        instructor.setId(0);
        return instructorService.save(instructor);
    }

    // Expose the instructors endpoint to update an instructor
    @PutMapping("/instructors")
    public InstructorEntity updateInstructor(@RequestBody InstructorEntity instructor) {
        InstructorEntity dbInstructor = instructorService.findById(instructor.getId());

        if (dbInstructor == null)
            throw new InstructorNotFoundException("Instructor id not found - " + instructor.getId());

        return instructorService.save(instructor);
    }

    // Expose the instructors endpoint to delete an instructor by id
    @DeleteMapping("/instructors/{instructorId}")
    public String deleteInstructor(@PathVariable int instructorId) {
        InstructorEntity instructor = instructorService.findById(instructorId);

        if (instructor == null)
            throw new InstructorNotFoundException("Instructor id not found - " + instructorId);

        instructorService.deleteById(instructorId);

        return "Deleted employee id - " + instructorId;
    }

}