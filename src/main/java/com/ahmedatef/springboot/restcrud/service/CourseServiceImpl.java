//package com.ahmedatef.springboot.restcrud.service;
//
//import com.ahmedatef.springboot.restcrud.dto.CourseDTO;
//import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
//import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
//import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
//import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class CourseServiceImpl implements CourseService {
//
//    private final CourseRepository repository;
//
//    @Autowired
//    public CourseServiceImpl(CourseRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public List<CourseDTO> findAllCourses() {
//        List<CourseEntity> dbCourses = repository.findAll();
//        List<CourseDTO> courses = new ArrayList<>();
//        dbCourses.forEach(dbCourse -> courses.add(MapperUtil.map(dbCourse, CourseDTO.class)));
//        return courses;
//    }
//
//    @Override
//    public Optional<CourseDTO> findCourseById(UUID id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void deleteCourseById(UUID id) {
//
//    }
//
//    @Override
//    public CourseDTO saveCourseAndLinkInstructor(CourseAddingRequest courseAddingRequest) {
//        return null;
//    }
//
//    @Override
//    public CourseDTO updateCourse(CourseUpdateRequest courseUpdateRequest) {
//        return null;
//    }
//
//    public <D> CourseDTO findById(D id) {
//        Optional<CourseEntity> dbCourse = repository.findById((UUID) id);
//        if (dbCourse.isPresent())
//            return MapperUtil.map(dbCourse.get(), CourseDTO.class);
//        else
//            throw new CourseNotFoundException("Course id not found - " + id);
//    }
//
//    public CourseDTO save(CourseDTO entity) {
//        CourseEntity savedEntity = repository.save(MapperUtil.map(entity, CourseEntity.class));
//        return MapperUtil.map(savedEntity, CourseDTO.class);
//    }
//
//    public <D> void deleteById(D id) {
//        repository.deleteById((UUID) id);
//    }
//}
