package jp.co.mgsystems.yuricollection.gootscatalog.beans;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 認証トークン情報
 */
@Data
@NoArgsConstructor
public class VerificationToken {
    // 認証トークンID
    private Long id;
    // トークン
    private String token;
    // ユーザID
    private Long userId;
    // トークン有効期限
    private LocalDateTime expirationTime;
}

