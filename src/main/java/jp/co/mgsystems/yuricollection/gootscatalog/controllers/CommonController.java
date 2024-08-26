package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.PasswordResetForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserSaveForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.CommonService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.OrdersService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.PasswordResetTokensService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;

@Controller
public class CommonController {
    
    @Autowired
    ProductsService productsService;

    @Autowired
    UsersService usersService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    CommonService commonService;

    @Autowired
    PasswordResetTokensService passwordResetTokensService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ログイン画面初期表示
     * @return 遷移先
     */
    @GetMapping("/login")
    public String initLogin() {
        return "common/login";
    }

    /**
     * ログアウト画面初期表示
     * @return 遷移先
     */
    @GetMapping("/logout")
    public String initLogout() {
        return "common/logout";
    }

    /**
     * ユーザ作成処理
     * @return 遷移先
     */
    @PostMapping("/userCreate")
    public String initUserCreate(Model model) {
        model.addAttribute("userSaveForm", new UserSaveForm());
        return "common/user_create";
    }
    
    /**
     * メイン画面初期表示
     * @return 遷移先
     */
    @GetMapping("/")
    public String initMain(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        // 商品情報取得
        List<Product> products = new ArrayList<>();
        products = productsService.getLatestProduct();
        // モデルに格納
        model.addAttribute("products", products);
        if (roles.contains("ROLE_ADMIN")) {
            // ADMIN権限のユーザーがリダイレクトされるページ
            return "admin/main";  
        } else if (roles.contains("ROLE_USER")) {
            // USER権限のユーザーがリダイレクトされるページ
            return "user/main";       
        } else {
            // デフォルトのリダイレクト先
            return "redirect:/user/";                
        }
    }



    /**
     * パスワード変更画面遷移
     * ログインしていない場合：メールアドレス入力画面遷移
     * ログインしているメール送信処理にリダイレクト
     * @return　遷移先
     */
    @PostMapping("/common/password/updateDisp")
    public String passwordUpdate() {

        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();

        if(loginUserId == null) {
            // ログイン状態でないとき
            return "common/password_update_mail_input";
        }
        // ログイン状態の時
        return "redirect:/common/sendPassUpdateMail";
    }


    /**
     * パスワード認証メール送信
     * @return 遷移先
     */
    @RequestMapping("/common/sendPassUpdateMail")
    public String sendPassUpdateMail(@ModelAttribute PasswordResetForm passwordResetForm) {

        User user = null;

        if(passwordResetForm.getUsername() == null) {
            // ログイン中のユーザIDを取得しセットする
            Long loginUserId = usersService.getLogInUserId();
            user = usersService.getByUserId(loginUserId);
        } else {

            // 入力値のユーザを検索し取得
            user = usersService.getByUsername(passwordResetForm.getUsername());
        }

        passwordResetTokensService.createPasswordResetTokenForUser(user);
        return "user/pass_update_mail_complete";
    }

    /**
     * パスワードトークン認証処理
     * @param token トークン
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/common/verifyPasswordUpdate")
    public String verifyPasswordUpdate(@RequestParam("token") String token, @ModelAttribute PasswordResetForm passwordResetForm, Model model) {
        boolean verified = passwordResetTokensService.validatePasswordResetToken(token);
        if (verified == false) {
            // 認証が確認できない場合認証できない画面に遷移
            return "common/not_valification";
        }
        // トークンからユーザ名を取得しセット
        String username = passwordResetTokensService.getByToken(token);
        passwordResetForm.setUsername(username);
        return "user/password_update";
    }


    /**
     * パスワード変更
     * @param passwordResetForm パスワード変更情報
     * @param model モデル
     * @return 遷移先
     */
    @PostMapping("/common/passwordUpdate")
    public String passwordUpdate(@ModelAttribute PasswordResetForm passwordResetForm ,Model model) {
        User user = usersService.getByUsername(passwordResetForm.getUsername());
        // パスワード入力値をセット
        user.setPassword(passwordEncoder.encode(passwordResetForm.getNewPassword()));
        usersService.passwordUpdate(user);
        return "/common/password_update_success";
    }



    /**
     * ユーザ情報新規登録
     * @param userSaveForm 登録ユーザ情報
     * @param model モデル
     * @return 遷移先
     */
    @PostMapping("/common/register")
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
        return "redirect:/common/saveSuccess";
    }

    /**
     * 新規登録成功時の画面を返す画面
     * @return 遷移先
     */
    @RequestMapping("/common/saveSuccess")
    public String saveSuccess() {
        return "common/valification";
    }
    

    /**
     * ユーザアカウント利用可能化処理
     * @param token トークン
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/common/verify")
    public String verifyAccount(@RequestParam("token") String token, Model model) {
        boolean verified = usersService.verifyUser(token);
        if (verified == false) {
            // 認証が確認できない場合認証できない画面に遷移
            return "common/not_valification";
        }
        return "common/login";
    }
}
