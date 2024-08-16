package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserSaveForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;

@Controller
public class CommonController {
    
    @Autowired
    ProductsService productsService;

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
}
