package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.ProductForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.CommonService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.OrdersService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;

@Controller
public class UserProductController {
    
    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    CommonService commonService;



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
        commonService.setGenres(model);
        // 商品番号を条件に商品を検索
        Product product = productsService.getProductById(productId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(product, productForm);
        return "user/product";
    }

}
