package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import lombok.Data;

/*
 * パスワード変更画面の入力内容
 */
@Data
public class PasswordResetForm {
    // ユーザ名
    private String username;

    // 新しいパスワード
    private String newPassword;

    // 新しいパスワード確認用
    private String newPasswordConf;
}
