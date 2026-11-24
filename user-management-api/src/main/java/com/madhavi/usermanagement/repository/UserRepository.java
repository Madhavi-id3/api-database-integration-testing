
package com.madhavi.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.madhavi.usermanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

}
