package in.nikhil.CrudDTODemo.service;

import in.nikhil.CrudDTODemo.DTO.CreateStudentRequestDTO;
import in.nikhil.CrudDTODemo.DTO.CreateStudentResponseDTO;
import in.nikhil.CrudDTODemo.DTO.UpdateStudentRequestDTO;
import in.nikhil.CrudDTODemo.DTO.UpdateStudentResponseDTO;
import in.nikhil.CrudDTODemo.exception.DuplicateResourceException;
import in.nikhil.CrudDTODemo.exception.ResourceNotFoundException;
import in.nikhil.CrudDTODemo.repostitory.StudentRepository;
import in.nikhil.CrudDTODemo.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create
    public CreateStudentResponseDTO createStudent(CreateStudentRequestDTO studentRequestDto){
        // Business Logic
        Student student = mapToEntity(studentRequestDto);

        if(emailExists(student)){
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists");
        }

        // store to DB
        Student studentResp = studentRepository.save(student);

        return mapToDTO(studentResp);

    }

    // Get Student
    public CreateStudentResponseDTO getStudent(Long id){
        Student studentResp = studentRepository
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        return mapToDTO(studentResp);

    }
    // select * from student where id = 1 && deleted = false;


    // Get All Students
    public List<CreateStudentResponseDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream().map(this::mapToDTO).toList();
    }


    // Update Student Info
    public UpdateStudentResponseDTO updateStudent(UpdateStudentRequestDTO studentReq, Long id) {
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Student with id " + id + " not found"));


        existingStudent.setName(studentReq.getName());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student student =  studentRepository.save(existingStudent);

        return mapToUpdatedDTO(student);
    }


    // Delete an Entry
    public void deleteStudent(Long id) {
        Student existingStudent = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        studentRepository.delete(existingStudent);
    }

    // Soft Delete
    public void softDeleteStudent(Long id) {
        Student existingStudent = studentRepository
                                .findByIdAndDeletedIsFalse(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        existingStudent.setDeleted(true);      // here - get() is used to get student from optional.
        studentRepository.save(existingStudent);


    }

    private  Student mapToEntity(CreateStudentRequestDTO studentReqDto){
        Student student = new Student();
        student.setName(studentReqDto.getName());
        student.setAge(studentReqDto.getAge());
        student.setRollNo(studentReqDto.getRollNo());
        student.setEmail(studentReqDto.getEmail());
        student.setSubject(studentReqDto.getSubject());

        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);

        return  student;
    }

    private CreateStudentResponseDTO mapToDTO(Student student) {
        CreateStudentResponseDTO studentResponseDTO = new CreateStudentResponseDTO();

        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setSubject(student.getSubject());

        studentResponseDTO.setCreatedAt(student.getCreatedAt());
        studentResponseDTO.setUpdatedAt(student.getUpdatedAt());

        studentResponseDTO.setMessage("Student have been saved in Database");

        return  studentResponseDTO;
    }

    private UpdateStudentResponseDTO mapToUpdatedDTO(Student student) {
        UpdateStudentResponseDTO updateDTO = new  UpdateStudentResponseDTO();

        updateDTO.setName(student.getName());
        updateDTO.setAge(student.getAge());
        updateDTO.setRollNo(student.getRollNo());
        updateDTO.setEmail(student.getEmail());
        updateDTO.setSubject(student.getSubject());
        updateDTO.setMessage("Student have been saved in Database");
        updateDTO.setUpdatedAt(LocalDateTime.now());

        return  updateDTO;
    }

    private boolean emailExists(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }

}
