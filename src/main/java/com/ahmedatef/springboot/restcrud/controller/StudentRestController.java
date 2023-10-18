//package com.ahmedatef.springboot.restcrud.controller;
//
//import com.ahmedatef.springboot.restcrud.entity.StudentEntity;
//import com.ahmedatef.springboot.restcrud.service.StudentServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api")
//public class StudentRestController {
//
//    private final StudentServiceImpl service;
//
//    @Autowired
//    public StudentRestController(StudentServiceImpl service) {
//        this.service = service;
//    }
//
//    @GetMapping("/students")
//    public List<StudentEntity> getStudents() {
//        return service.findAll();
//    }
//
//    @GetMapping("/students/{studentId}")
//    public StudentEntity getStudentById(@PathVariable UUID studentId) {
//        return service.findById(studentId);
//    }
//
//    @PostMapping("/students")
//    public StudentEntity addStudent(@RequestBody StudentEntity student) {
//        return service.save(student);
//    }
//
//    @PutMapping("/students")
//    public StudentEntity updateStudent(@RequestBody StudentEntity student) {
//        service.findById(student.getId());
//        return service.save(student);
//    }
//
//    @DeleteMapping("/students/{studentId}")
//    public String deleteStudent(@PathVariable UUID studentId) {
//        service.findById(studentId);
//        service.deleteById(studentId);
//        return "Deleted student id - " + studentId;
//    }
//
//}
