//package com.ahmedatef.springboot.restcrud.controller;
//
//import com.ahmedatef.springboot.restcrud.dto.CourseDTO;
//import com.ahmedatef.springboot.restcrud.service.CourseServiceImpl;
//import com.ahmedatef.springboot.restcrud.service.InstructorServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api")
//public class CourseRestController {
//
//    private final CourseServiceImpl service;
//    private final InstructorServiceImpl instructorService;
//
//    @Autowired
//    public CourseRestController(CourseServiceImpl service, InstructorServiceImpl instructorService) {
//        this.service = service;
//        this.instructorService = instructorService;
//    }
//
////    @GetMapping("/courses")
////    public List<CourseDTO> getCourses() {
////        return service.findAll();
////    }
//
//    @GetMapping("/courses/{courseId}")
//    public CourseDTO getCourseById(@PathVariable UUID courseId) {
//        return service.findById(courseId);
//    }
//
////    @PostMapping("/courses")
////    public CourseDTO addCourse(@RequestBody CourseAddingRequest courseAddingRequest) {
////        CourseRequestDTO courseRequestDTO = courseAddingRequest.getCourseRequestDTO();
////        courseRequestDTO.setInstructorId(courseAddingRequest.getInstructorId());
////        return service.save(courseRequestDTO);
////    }
//
//    @PutMapping("/courses")
//    public CourseDTO updateCourse(@RequestBody CourseDTO course) {
//        CourseDTO dbCourse = service.findById(course.getId());
//        course.setInstructor(dbCourse.getInstructor());
//        return service.save(course);
//    }
//
//    @DeleteMapping("/courses/{courseId}")
//    public String deleteCourse(@PathVariable UUID courseId) {
//        service.findById(courseId);
//        service.deleteById(courseId);
//        return "Delete course id - " + courseId;
//    }
//}