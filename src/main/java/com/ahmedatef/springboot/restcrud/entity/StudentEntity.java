package com.ahmedatef.springboot.restcrud.entity;

import com.ahmedatef.springboot.restcrud.enums.Gender;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private @NonNull String firstName;

    @Column(name = "last_name")
    private @NonNull String lastName;

    @Column(name = "age")
    private @NonNull int age;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private @NonNull Gender gender;

    @Column(name = "email")
    private @NonNull String email;

    @Column(name = "phone_number")
    private @NonNull String phoneNumber;

    @Column(name = "national_id")
    private @NonNull String nationalId;

    @ManyToMany
    @JoinTable(
            name = "relations",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses;
}
