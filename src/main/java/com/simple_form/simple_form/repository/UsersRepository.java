package com.simple_form.simple_form.repository;

import java.util.Optional;
import com.simple_form.simple_form.model.UsersModel;
import org.springframework.data.jpa.repository.*;

public interface UsersRepository extends JpaRepository<UsersModel, Integer> {
    
    Optional<UsersModel> findByLoginAndPassword(String login, String password);
}
