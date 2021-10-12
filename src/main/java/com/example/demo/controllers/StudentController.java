package com.example.demo.controllers;

import com.example.demo.dto.StudentDto;
import com.example.demo.model.Student;
import com.example.demo.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/
@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Get a list of all students in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of all students in Json",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Student.class)) })
    })
    @GetMapping("/students")
    public List<StudentDto> getStudents(){
        return studentService.getStudents();
    }

    @Operation(summary = "Get a student by id in JSON format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content) })
    @GetMapping("/students/{id}")
    public Student getStudentById(@Parameter(description = "Id for the student to be retrieved",
                                       required = true)
                                @PathVariable Long id){
        logger.trace("Students are being retrieved");
        return studentService.getStudentById(id);
    }

    @Operation(summary = "Add a new student tot the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid student supplied",
                    content = @Content) } )
    @PostMapping("/students")
    public void saveStudent(@RequestBody  Student student){
        studentService.addNewStudent(student);
    }

    @Operation(summary = "Delete a student from the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content) })
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        studentService.deleteStudent(id);
    }

    @Operation(summary = "Change student data in the DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student data changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content) })
    @PutMapping("/students/{id}")
    public void updateStudent(@PathVariable("studentId") Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
        studentService.updateStudent(id, name, email);
    }
}
