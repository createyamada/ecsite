package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    MessageSource messageSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.server.url}")
    private String serverUrl;


    /**
     * ユーザIDからユーザ情報を取得
     * @param userId
     * @return
     */
    public User getByUserId(Long userId) {
        return usersMapper.getByUserId(userId);
    }

    /**
     * ユーザ名からユーザ情報を取得
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        return usersMapper.getByUsername(username);
    }


    /**
     * ログインIDからログインユーザ名を取得する
     * @return ユーザ名
     */
    public Long getLogInUserId() {
        String login_username = this.getLogInUsername();
        User user = usersMapper.getByUsername(login_username);
        return user.getUserId();
    }

    /**
     * ログイン中のユーザ名を取得する
     * @return ユーザ名
     */
    private String getLogInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = usersMapper.getByUsername(username);
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

    /**
     * ユーザ登録処理
     * @param user
     */
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


    /**
     * 認証登録メール送信処理
     * @param email
     * @param token
     */
    private void sendVerificationEmail(String email, String token) {
        String url = serverUrl + "/common/verify?token=" + token;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("アカウント確認");
        mailMessage.setText("以下のリンクをクリックしてアカウントを確認してください: " + url);
        mailSender.send(mailMessage);
    }


    /**
     * トークン認証処理
     * @param token
     * @return　認証結果
     */
    public boolean verifyUser(String token) {
        VerificationToken verificationToken = tokenMapper.findByToken(token);
        
        if (verificationToken == null || verificationToken.getExpirationTime().isAfter(LocalDateTime.now())) {
            return false;
        }

        User user = usersMapper.getByUserId(verificationToken.getUserId());
        if (user == null) {
            return false;
        }

        user.setEnabled(true);
        usersMapper.authenticationCompleted(user);
        tokenMapper.deleteToken(verificationToken.getId());
        return true;
    }


    /**
     * ユーザ情報を更新する
     * @param user
     * @return 更新件数
     */
    @Transactional
    public int update(User user) {
        int cnt =  usersMapper.update(user);
        // 更新できなかった場合(versionエラーの可能性あり)
        if (cnt == 0) {
            throw new OptimisticLockingFailureException(
                messageSource.getMessage("error.OptimisticLockingFailure",
                null,
                Locale.JAPANESE)
            );
        }
        // 2件以上更新は想定外(SQL不備の可能性あり)
        if (cnt > 1) {
            throw new RuntimeException(
                messageSource.getMessage("error.Runtime",
                new String[] {"2件以上更新されました"},
                Locale.JAPANESE)
            );
        }
        return cnt;
    }

    @Transactional
    public void passwordUpdate(User user) {
        int cnt = usersMapper.passwordUpdate(user);
        // 更新できなかった場合(versionエラーの可能性あり)
        if (cnt == 0) {
            throw new OptimisticLockingFailureException(
                messageSource.getMessage("error.OptimisticLockingFailure",
                null,
                Locale.JAPANESE)
            );
        }
        // 2件以上更新は想定外(SQL不備の可能性あり)
        if (cnt > 1) {
            throw new RuntimeException(
                messageSource.getMessage("error.Runtime",
                new String[] {"2件以上更新されました"},
                Locale.JAPANESE)
            );
        }
    }
    
}
