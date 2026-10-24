package in.nikhil.CrudDTODemo.DTO;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDTO {
    @NotBlank(message = "Name can't be Null")
    @Size(min=2 , max = 50 , message = "Student name should be b/w 2 to 50 char")
    private String name;

    @Email(message = "Please use standard email format")
    @NotBlank(message = "email can't be blank")
    private String email;

    @Min(value = 18 , message = "Age should be min 18 years old")
    private int age;

    @Positive(message = "Roll number must be greater than 0")
    private int rollNo;

    @NotBlank
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
