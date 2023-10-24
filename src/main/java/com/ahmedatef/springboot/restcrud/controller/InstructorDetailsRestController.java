package com.ahmedatef.springboot.restcrud.controller;

import com.ahmedatef.springboot.restcrud.dto.DetailsAddLinkRequest;
import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorDetailsResponse;
import com.ahmedatef.springboot.restcrud.service.InstructorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/instructors/details")
public class InstructorDetailsRestController {

    private final InstructorDetailsService service;

    @Autowired
    public InstructorDetailsRestController(InstructorDetailsService service) {
        this.service = service;
    }

    @GetMapping
    public List<InstructorDetailsResponse> getInstructorDetails() {
        return service.findAll();
    }

    @GetMapping("/{detailsId}")
    public InstructorDetailsResponse getInstructorDetailsById(@PathVariable UUID detailsId) {
        return service.findById(detailsId);
    }

    @PostMapping
    public InstructorDetailsResponse addInstructorDetails(@RequestBody DetailsAddLinkRequest request) {
        return service.saveAndLinkInstructor(request);
    }

    @PutMapping
    public InstructorDetailsResponse updateInstructorDetails(@RequestBody InstructorDetailsDTO details) {
        return service.update(details);
    }

    @DeleteMapping("/{detailsId}")
    public String deleteInstructorDetails(@PathVariable UUID detailsId) {
        service.deleteById(detailsId);
        return "Deleted instructor details id - " + detailsId;
    }

}