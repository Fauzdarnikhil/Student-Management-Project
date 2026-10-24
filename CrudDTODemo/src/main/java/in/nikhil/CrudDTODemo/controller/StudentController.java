package in.nikhil.CrudDTODemo.controller;

import in.nikhil.CrudDTODemo.DTO.CreateStudentRequestDTO;
import in.nikhil.CrudDTODemo.DTO.CreateStudentResponseDTO;
import in.nikhil.CrudDTODemo.DTO.UpdateStudentRequestDTO;
import in.nikhil.CrudDTODemo.DTO.UpdateStudentResponseDTO;
import in.nikhil.CrudDTODemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create
    @PostMapping
    public ResponseEntity<CreateStudentResponseDTO> createStudent(@Valid @RequestBody CreateStudentRequestDTO studentRequestDto) {  // Request body converts JSON to java class

        CreateStudentResponseDTO createdStudent = studentService.createStudent(studentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // Read / Get
    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDTO> getStudent(@PathVariable Long id) {
        CreateStudentResponseDTO student = studentService.getStudent(id);

        return ResponseEntity.ok(student);
    }

    // Read ALL / GetAll
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> getAllStudents(){
        List<CreateStudentResponseDTO> studentsList = studentService.getAllStudents();

        return ResponseEntity.ok(studentsList);
    }

    // Update
    @PutMapping
    public ResponseEntity<UpdateStudentResponseDTO> updateStudent(@Valid @RequestBody UpdateStudentRequestDTO studentReq,
                                                                 @RequestParam Long id) {

        UpdateStudentResponseDTO studentResp = studentService.updateStudent(studentReq,id);

        return ResponseEntity.ok(studentResp);
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        studentService.deleteStudent(id);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Soft Delete
    @PatchMapping("/soft-delete")
    public ResponseEntity<String> softDeleteStudent(@RequestParam  Long id){
        studentService.softDeleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}


////    @GetMapping("/{id}")
////    public Student getStudent(){
//
//    }



