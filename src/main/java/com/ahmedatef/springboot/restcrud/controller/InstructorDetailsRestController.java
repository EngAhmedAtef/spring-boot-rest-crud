//package com.ahmedatef.springboot.restcrud.controller;
//
//import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/instructors")
//public class InstructorDetailsRestController {
//
//    private final InstructorDetailsService service;
//    private final InstructorService instructorService;
//
//    @Autowired
//    public InstructorDetailsRestController(InstructorDetailsService service, InstructorService instructorService) {
//        this.service = service;
//        this.instructorService = instructorService;
//    }
//
//    @GetMapping("/details")
//    public List<InstructorDetailsEntity> getInstructorDetails() {
//        return service.findAll();
//    }
//
//    @GetMapping("/details/{detailsId}")
//    public InstructorDetailsEntity getInstructorDetailsById(@PathVariable UUID detailsId) {
//        return service.findById(detailsId);
//    }
//
////    @PostMapping("/details")
////    public InstructorDetailsEntity addInstructorDetails(@RequestBody InstructorDetailsEntity details) {
////        InstructorEntity instructor = details.getInstructor();
////        InstructorEntity dbInstructor = instructorService.findById(instructor.getId());
////        details.setInstructor(dbInstructor);
////        return service.save(details);
////    }
//
//    @PutMapping("/details")
//    public InstructorDetailsEntity updateInstructorDetails(@RequestBody InstructorDetailsEntity details) {
//        service.findById(details.getId());
//        return service.save(details);
//    }
//
//    @DeleteMapping("/details/{detailsId}")
//    public String deleteInstructorDetails(@PathVariable UUID detailsId) {
//        service.findById(detailsId);
//        service.deleteById(detailsId);
//        return "Deleted instructor details id - " + detailsId;
//    }
//
//}