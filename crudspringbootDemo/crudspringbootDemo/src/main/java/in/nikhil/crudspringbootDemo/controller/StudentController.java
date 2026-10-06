package in.nikhil.crudspringbootDemo.controller;

import in.nikhil.crudspringbootDemo.entity.Student;
import in.nikhil.crudspringbootDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")

public class StudentController {

    private final StudentService  studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {  // Request body converts JSON to java class

        Student createdStudent = studentService.createStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // Read / Get
    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam Long id) {
        Student student = studentService.getStudent(id);

        if(student == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }



    // Read ALL / GetAll
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> studentsList = studentService.getAllStudents();

        if(studentsList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsList);
    }

    // Update
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student studentReq, @RequestParam Long id) {
        Student studentResp = studentService.updateStudent(studentReq,id);

        if(studentResp == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        boolean isDeleted  = studentService.deleteStudent(id);

        if(!isDeleted){
            return  ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok("Record Deleted");
    }

    // Soft Delete
    @PatchMapping("/soft-delete")
    public ResponseEntity<String> softDeleteStudent(@RequestParam  Long id){
        boolean isDeleted = studentService.softDeleteStudent(id);

        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Record Deleted");
    }

}


////    @GetMapping("/{id}")
////    public Student getStudent(){
//
//    }



