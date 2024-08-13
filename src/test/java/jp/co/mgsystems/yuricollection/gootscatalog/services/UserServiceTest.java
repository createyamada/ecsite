package jp.co.mgsystems.yuricollection.gootscatalog.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    UsersService service;

    @DisplayName("ユーザ取得成功")
    @Test
    void test001() throws Exception {
        UserDetails user = service.loadUserByUsername("admin");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches("pass", user.getPassword())).isTrue();
    }
}