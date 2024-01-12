package org.launchcode.tutorconnector.models.data;

import org.launchcode.tutorconnector.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Login findByEmail (String email);

    Login findByRole(String role);



}
