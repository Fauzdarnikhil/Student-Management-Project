package in.nikhil.crudspringbootDemo.repository;

import in.nikhil.crudspringbootDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndDeletedIsFalse(Long id);
    List<Student> findByDeletedIsFalse();







}


/*
Create --> save()
Read All --> findAll()
Read One --> find()
Delete --> deleteById()
update -->  save()
           existById
*/
