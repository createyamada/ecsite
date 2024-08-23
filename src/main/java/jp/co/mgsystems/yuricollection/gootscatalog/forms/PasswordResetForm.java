package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import lombok.Data;

/*
 * パスワード変更画面の入力内容
 */
@Data
public class PasswordResetForm {
    // 古いパスワード
    private String oldPassword;

    // 新しいパスワード
    private String newPassword;

    // 新しいパスワード確認用
    private String newPasswordConf;
}
