package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.ProductForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserSaveForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserUpdateForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;

/*
 * ユーザの画面遷移のみ等シンプルな処理を記載
 */
@Controller
// @RequestMapping("/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    ProductsService productsService;


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
        User user = usersService.selectByUserId(loginUserId);
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


    /**
     * ユーザ情報新規登録
     * @param userSaveForm 登録ユーザ情報
     * @param model モデル
     * @return 遷移先
     */
    @PostMapping("/user/register")
    public String register(@ModelAttribute UserSaveForm userSaveForm ,Model model) {
        // パスワードのエンコード
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userSaveForm.getPassword());
        // 他のフィールド処理
        userSaveForm.setPassword(encodedPassword);

        User user = new User();
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(userSaveForm, user);
        usersService.register(user);
        // model.addAttribute("message", "登録が成功しました。メールを確認してアカウントを有効化してください。");
        return "redirect:/saveSuccess";
    }

    /**
     * 新規登録成功時の画面を返す画面
     * @return 遷移先
     */
    @RequestMapping("/user/saveSuccess")
    public String requestMethodName() {
        return "common/valification";
    }
    

    /**
     * ユーザアカウント利用可能化処理
     * @param token トークン
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/user/verify")
    public String verifyAccount(@RequestParam("token") String token, Model model) {
        boolean verified = usersService.verifyUser(token);
        if (verified) {
            model.addAttribute("message", "アカウントが有効化されました。ログインしてください。");
        } else {
            model.addAttribute("message", "トークンが無効です。もう一度やり直してください。");
        }
        return "common/login";
    }
    
}
