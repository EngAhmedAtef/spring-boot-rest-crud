package com.ahmedatef.springboot.restcrud.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "instructor_details")
public class InstructorDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "youtube_channel")
    private String youtubeChannel;

    @Column(name = "hobbies", columnDefinition = "text[]")
    private String[] hobbies;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "instructor_id")
    private InstructorEntity instructor;
}
