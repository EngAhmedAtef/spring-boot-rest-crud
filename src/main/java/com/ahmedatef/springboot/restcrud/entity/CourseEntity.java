package com.ahmedatef.springboot.restcrud.entity;

import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "course_level")
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column(name = "is_started")
    private Boolean isStarted;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    @JsonBackReference
    private InstructorEntity instructor;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<StudentEntity> students;


//    @Override
//    public String toString() {
//        return "CourseEntity{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}