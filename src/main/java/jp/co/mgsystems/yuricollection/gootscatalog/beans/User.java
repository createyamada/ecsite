package jp.co.mgsystems.yuricollection.gootscatalog.beans;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * ユーザ情報
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    
    // ユーザID
    private Long userId;

    // ユーザ名
    private String username;

    // パスワード
    private String password;

    // 権限
    private String role;

    // 姓
    private String firstName;

    // 名
    private String lastName;

    // 郵便番号
    private String zip;
    
    // 電話番号
    private String telno;

    // 都道府県
    private String prefectures;
    
    // 市町村
    private String city;

    // 住所
    private String address;
    
    // アカウント初期化処理完了フラグ
    private Boolean enabled;

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;

    public User(Long userId, String username, String password, String role, String firstName, String lastName, String zip, String telno, String prefectures, String city, String address, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zip = zip;
        this.telno = telno;
        this.prefectures = prefectures;
        this.city = city;
        this.address = address;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public Long getUserId() {
        return this.userId;
    }
}
