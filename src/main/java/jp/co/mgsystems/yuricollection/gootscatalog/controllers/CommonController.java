package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    

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
     * メイン画面初期表示
     * @return 遷移先
     */
    @GetMapping("/")
    public String initMain() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            // ADMIN権限のユーザーがリダイレクトされるページ
            return "redirect:/admin/";  
        } else if (roles.contains("ROLE_USER")) {
            // USER権限のユーザーがリダイレクトされるページ
            return "redirect:/user/";       
        } else {
            // デフォルトのリダイレクト先
            return "redirect:/user/";                
        }
    }
}
