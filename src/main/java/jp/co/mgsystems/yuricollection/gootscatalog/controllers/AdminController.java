package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// @RequestMapping("/admin")
public class AdminController {

    @GetMapping("/admin")
    public String init() {
        // 管理者メインページへのビュー名を返す
        return "admin/main";
    }

    @GetMapping("/admin/userManagement")
    public String getMethodName() {
        return "admin/user_management";
    }
    
}
