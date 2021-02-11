package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    // Adding custom swagger documentation for list all students with paging
    @ApiOperation(value = "Return all students",
            response = Student.class,
            responseContainer = "List")

    // Please note there is no way to add students to course yet!

    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer",
                    paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string",
                    paramType = "query", value = "Sorting criteria in for format: property(,asc|desc.). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported.")})



    // paging and sorting
    // localhost:2019/students/students/paging/?page=1&size=10
    @GetMapping(value = "/students/paging",
            produces = {"application/json"})
    public ResponseEntity<?> ListAllStudentsByPage(@PageableDefault(page = 1,
            size = 3) Pageable pageable)
    {                        // findAllPageable(pageable.unpaged()) <- returns everything
        List<Student> myStudents = studentService.findAllPageable(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    // Adding custom swagger documentation for list all students
    @ApiOperation(value = "Return all students",
            response = Student.class,
            responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student List Found",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Student List Not Found",
                    response = ErrorDetail.class)})

    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents()
    {
        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Getting student by id",
            response = Student.class
            )
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student id Found",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Student id Not Found",
                    response = ErrorDetail.class)})

    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            // Adding custom swagger params for get student by id
            @ApiParam(value = "Student Id",
                    required = true,
                    example = "1") // example is always set as a string
            @PathVariable
                    Long StudentId)
    {
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value = "Getting Student By Name Containing",
            response = Student.class,
            responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student Name Found",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Student Name Not Found",
                    response = ErrorDetail.class)})

    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @PathVariable String name)
    {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "Adding a new student",
            response = Student.class
            )
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student Added",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Cannot add new student",
                    response = ErrorDetail.class)})

    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Updating student",
            response = Student.class
           )
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student updating completed",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Cannot update student",
                    response = ErrorDetail.class)})

    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @ApiParam(value = "Student Id",
            required = true,
            example = "1")
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid)
    {
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Deleting student",
            response = Student.class
    )
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student deleted",
            response = Student.class),
            @ApiResponse(code = 404,
                    message = "Cannot delete student",
                    response = ErrorDetail.class)})

    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @ApiParam(value = "Student Id",
                    required = true,
                    example = "1")
            @PathVariable
                    long Studentid)
    {
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
