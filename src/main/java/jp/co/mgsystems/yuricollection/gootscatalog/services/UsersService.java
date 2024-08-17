package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.VerificationToken;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.TokenMapper;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.UsersMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {


    @Autowired
    UsersMapper usersMapper;

    @Autowired
    private TokenMapper tokenMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usersMapper.selectByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("ユーザが存在しない");
        }

        if(user.getEnabled() == false) {
            throw new UsernameNotFoundException("ユーザ登録が完了していません");

        }

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersMapper.insert(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserId(user.getUserId());
        verificationToken.setExpirationTime(LocalDateTime.now());

        tokenMapper.insertToken(verificationToken);
        sendVerificationEmail(user.getUsername(), token);
    }

    private void sendVerificationEmail(String email, String token) {
        String url = "http://localhost:8080/user/verify?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("アカウント確認");
        mailMessage.setText("以下のリンクをクリックしてアカウントを確認してください: " + url);
        mailSender.send(mailMessage);
    }

    public boolean verifyUser(String token) {
        VerificationToken verificationToken = tokenMapper.findByToken(token);
        
        if (verificationToken == null || verificationToken.getExpirationTime().isAfter(LocalDateTime.now())) {
            return false;
        }

        User user = usersMapper.selectByUserId(verificationToken.getUserId());
        if (user == null) {
            return false;
        }

        user.setEnabled(true);
        usersMapper.authenticationCompleted(user);
        tokenMapper.deleteToken(verificationToken.getId());
        return true;
    }
    
}
