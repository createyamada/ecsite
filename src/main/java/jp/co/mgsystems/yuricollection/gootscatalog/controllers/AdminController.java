package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String init() {
        // 管理者メインページへのビュー名を返す
        return "admin/main";
    }

    @GetMapping("/userManagement")
    public String getMethodName() {
        return "admin/user_management";
    }
    
}
