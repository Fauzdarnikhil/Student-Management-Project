package in.nikhil.crudspringbootDemo.service;

import in.nikhil.crudspringbootDemo.entity.Student;
import in.nikhil.crudspringbootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository  studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create
    public Student createStudent(Student studentReq){
        // Business Logic
        studentReq.setDeleted(false);

        // store to DB
        return studentRepository.save(studentReq);

    }

    // Get Student
    public Student getStudent(Long id){
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);

        return studentResp.orElse(null);

    }
    // select * from student where id = 1 && deleted = false;


    // Get All Students
    public List<Student> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        return studentList;
    }


    // Update Student Info
    public Student updateStudent(Student studentReq, Long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()){
            return null;
        }

        Student StudentToSave = existingStudent.get();
        StudentToSave.setName(studentReq.getName());
        StudentToSave.setAge(studentReq.getAge());
        StudentToSave.setRollNo(studentReq.getRollNo());
        StudentToSave.setEmail(studentReq.getEmail());
        StudentToSave.setSubject(studentReq.getSubject());
        StudentToSave.setDeleted(false);

        return studentRepository.save(StudentToSave);
    }

    // Delete an Entry
    public boolean deleteStudent(Long id) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if(existingStudent.isEmpty()){
            return false;
        }

        studentRepository.deleteById(id);
        return true;
    }

    // Soft Delete
    public boolean softDeleteStudent(Long id) {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(existingStudent.isEmpty()){
            return false;
        }

        Student student = existingStudent.get();

        student.setDeleted(true);      // here - get() is used to get student from optional.
        studentRepository.save(student);
        return true;


    }
}
