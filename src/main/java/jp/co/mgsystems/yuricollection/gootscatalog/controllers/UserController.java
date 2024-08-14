package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String init() {
        // ユーザメインページへのビュー名を返す
        return "user/main";
    }

    @GetMapping("/userEdit")
    public String initUserEdit() {
        // ユーザメインページへのビュー名を返す
        return "user/user_edit";
    }
}
