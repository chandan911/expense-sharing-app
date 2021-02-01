package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhoneNumber(String phoneNumber);
}
