package com.ahmedatef.springboot.restcrud.entity;

import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private @NonNull String name;

    @Column(name = "start_date")
    private @NonNull Timestamp startDate;

    @Column(name = "end_date")
    private @NonNull Timestamp endDate;

    @Column(name = "course_level")
    @Enumerated(EnumType.STRING)
    private @NonNull CourseLevel courseLevel;

    @Column(name = "is_started")
    private @NonNull Boolean isStarted;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instructor_id")
    @JsonBackReference
    private @NonNull InstructorEntity instructor;

    @ManyToMany(mappedBy = "courses")
    private Set<StudentEntity> students;

}