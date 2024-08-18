package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.ProductForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.OrdersService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



// @RequestMapping("/user")
@Controller
public class UserProductController {
    
    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    OrdersService ordersService;

    @GetMapping("/user/purchaseHistory")
    public String initProductHistory() {
        Integer userId = 1; 
        return "user/purchase_history";
    }

    /**
     * 商品詳細初期表示
     * @param productId　商品番号
     * @param productForm　入力内容
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/user/products/{productId}")
    public String initProductDetail(@PathVariable("productId") Integer productId, @ModelAttribute ProductForm productForm, Model model) {
        // ジャンル情報取得
        this.setGenres(model);
        // 商品番号を条件に商品を検索
        Product product = productsService.getProductById(productId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(product, productForm);
        return "user/product";
    }


    @PostMapping("/user/purchase")
    public String purchase(ProductForm productForm) {
        Order order = new Order();
        // 入力内容を詰め替える
        // BeanUtils.copyProperties(productForm, order);
        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();
        order.setUserId(loginUserId);
        order.setProductId(productForm.getProductId());
        order.setOrderCnt(productForm.getOrders());
        // 登録
        ordersService.save(order);
        return "user/main";
    }
    


    /**
     * ジャンル一覧をモデルに追加する
     * @param searchForm
     * @param model
     */
    private void setGenres(Model model) {
        // ブランド情報取得
        List<Genre> genres = new ArrayList<>();
        genres = productsService.getGenre();

        // モデルに格納
        model.addAttribute("genres", genres);
    }


}
