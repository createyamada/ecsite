package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserUpdateForm {
        // ユーザID
    private Long userId;

    // ユーザ名
    private String username;

    // パスワード
    private String password;

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

    // 作成日時
    private LocalDateTime createdAt;

    // 更新日時
    private LocalDateTime updatedAt;
}
