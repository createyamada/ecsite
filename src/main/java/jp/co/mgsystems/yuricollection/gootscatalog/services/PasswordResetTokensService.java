package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.PasswordResetToken;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.PasswordResetTokenMapper;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.UsersMapper;

@Service
public class PasswordResetTokensService {
    

    @Autowired
    PasswordResetTokenMapper tokenMapper;

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    JavaMailSender mailSender;

    @Value("${app.server.url}")
    private String serverUrl;



    public String getByToken(String token) {
        PasswordResetToken passToken = tokenMapper.findByToken(token);
        User user = usersMapper.getByUserId(passToken.getUserId());
        return user.getUsername();
    }

    public void createPasswordResetTokenForUser(User user) {

        if(user == null) {
            throw new UsernameNotFoundException("ユーザが存在しない");
        }

        PasswordResetToken resetToken = new PasswordResetToken();

        String token = UUID.randomUUID().toString();
        resetToken.setToken(token);
        resetToken.setUserId(user.getUserId());
        resetToken.setExpirationTime(LocalDateTime.now().plusHours(24)); // トークンの有効期限を24時間に設定
        tokenMapper.insertToken(resetToken);

        sendPasswordUpdateVerificationEmail(user.getUsername(),token);
    }

    public boolean validatePasswordResetToken(String token) {
        PasswordResetToken passToken = tokenMapper.findByToken(token);

        if (passToken == null || passToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    public void deleteToken(String token) {
        tokenMapper.deleteByToken(token);
    }

    /**
     * パスワード認証登録メール送信処理
     * @param email
     * @param token
     */
    private void sendPasswordUpdateVerificationEmail(String email, String token) {
        String url = serverUrl + "/common/verifyPasswordUpdate?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("アカウント確認");
        mailMessage.setText("以下のリンクをクリックしてアカウントを確認してください: " + url);
        mailSender.send(mailMessage);
    }

}
