package jp.co.mgsystems.yuricollection.gootscatalog.beans;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationToken {
    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expirationTime;
}

