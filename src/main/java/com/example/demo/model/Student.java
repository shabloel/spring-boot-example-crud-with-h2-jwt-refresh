package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 0, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 0, max = 20)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @NotBlank
    @Size(min = 0, max = 20)
    private String email;

    @Transient
    private String interests;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "student")
    private Set<CourseRegistration> courseRegistrations;

    @ManyToMany
    @JoinTable(
            name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Teacher> teachers;

}
