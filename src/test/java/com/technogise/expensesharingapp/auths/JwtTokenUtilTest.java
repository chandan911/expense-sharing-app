package com.technogise.expensesharingapp.auths;

import com.technogise.expensesharingapp.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenUtilTest {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Test
  public void testGenerateAuthTokenForUser() {
    User user = new User("demoUser", "password", "9898989898");
    user.setId((long) 1);
    String authToken = jwtTokenUtil.generateAuthTokenFor(user);
    Assertions.assertNotNull(authToken);
  }
}
