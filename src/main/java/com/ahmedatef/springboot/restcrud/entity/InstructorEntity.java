package com.ahmedatef.springboot.restcrud.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "instructor")
public class InstructorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private @NonNull String firstName;

    @Column(name = "last_name")
    private @NonNull String lastName;

    @Column(name = "email")
    private @NonNull String email;

    @Column(name = "phone_number")
    private @NonNull String phoneNumber;

    @Column(name = "title")
    private @NonNull String title;

}
