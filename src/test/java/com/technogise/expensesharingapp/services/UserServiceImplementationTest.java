package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.User;
import com.technogise.expensesharingapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplementationTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserServiceImpl mockUserService;

    @Test
    public void testGetUserByPhoneNumberWhenUserExist(){
        User user1 = new User("user1","123456","9999999999");
        User user2 = new User("user1","123457","9999999998");
        User user3 = new User("user1","123458","9999999997");

        List<User> userList = List.of(user1,user2,user3);

        Mockito.when(mockUserRepository.findAll()).thenReturn(userList);
        Optional<User> mayBeActualUser = mockUserService.getUserByPhoneNumber("9999999999");
        User actualUser = null;
        if(mayBeActualUser.isPresent()) {
            actualUser = mayBeActualUser.get();
        }
        Assertions.assertEquals(user1,actualUser);
    }

    @Test
    public void testGetUserByPhoneNumberWhenUserNotExist(){
        User user1 = new User("user1","123456","9999999999");
        User user2 = new User("user1","123457","9999999998");
        User user3 = new User("user1","123458","9999999997");

        List<User> userList = List.of(user1,user2,user3);

        Mockito.when(mockUserRepository.findAll()).thenReturn(userList);
        Optional<User> mayBeActualUser = mockUserService.getUserByPhoneNumber("9999999996");
        User actualUser = null;
        if(mayBeActualUser.isPresent()) {
            actualUser = mayBeActualUser.get();
        }
        Assertions.assertNull(actualUser);
    }
}
