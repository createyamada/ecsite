package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserUpdateForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.PasswordResetTokensService;


/*
 * ユーザの画面遷移のみ等シンプルな処理を記載
 */
@Controller
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    PasswordResetTokensService passwordResetTokensService;

    /**
     * ユーザメイン画面初期表示
     * @return 遷移先
     */
    @GetMapping("/user")
    public String init() {
        // ユーザメインページへのビュー名を返す
        return "user/main";
    }


    /**
     * ユーザ編集画面初期表示
     * @return　遷移先
     */
    @GetMapping("/user/userEdit")
    public String initUserEdit(@ModelAttribute UserUpdateForm userUpdateForm, Model model) {
        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();
        // ユーザ番号を条件にユーザを検索
        User user = usersService.getByUserId(loginUserId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(user, userUpdateForm);

        // ユーザメインページへのビュー名を返す
        return "user/user_edit";
    }

      /**
     * ユーザ編集画面初期表示
     * @return　遷移先
     */
    @PostMapping("/user/update")
    public String update(@ModelAttribute UserUpdateForm userUpdateForm, Model model) {
        
        User user = new User();
        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();
        userUpdateForm.setUserId(loginUserId);

        // 入力内容を詰め替える
        BeanUtils.copyProperties(userUpdateForm, user);
        usersService.update(user);


        // ユーザメインページへのビュー名を返す
        return "user/user_edit";
    }  



    
}
