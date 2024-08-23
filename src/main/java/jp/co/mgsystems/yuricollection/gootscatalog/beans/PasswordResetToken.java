package jp.co.mgsystems.yuricollection.gootscatalog.beans;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PasswordResetToken {
    // パスワード認証トークンID
    private Long id;
    // パスワード認証トークン
    private String token;
    // ユーザID
    private Long userId;
    // 有効期限
    private LocalDateTime expirationTime;
}
