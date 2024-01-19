package org.launchcode.tutorconnector.models.data;

import org.launchcode.tutorconnector.models.Login;
import org.launchcode.tutorconnector.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<Login, String> {

    Login findByEmail(String email);
}
