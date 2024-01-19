package org.launchcode.tutorconnector.models.data;

import org.launchcode.tutorconnector.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository <Student, Integer> {

    Student findByEmail(String email);
}

