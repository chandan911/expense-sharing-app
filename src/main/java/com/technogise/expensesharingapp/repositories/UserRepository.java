package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
}
