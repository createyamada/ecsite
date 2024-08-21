package jp.co.mgsystems.yuricollection.gootscatalog.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.OrderForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.ProductForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.CommonService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.OrdersService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.UsersService;

@Controller
public class UserOrderController {
    
    @Autowired
    UsersService usersService;

    @Autowired
    ProductsService productsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    CommonService commonService;

    @GetMapping("/user/purchaseHistories")
    public String initPurchaseHistories(@Validated SearchForm searchForm, Model model) {
        // 情報検索しmodelに格納
        commonService.setGenres(model);
        // ログイン中のユーザIDを取得しセットする
        searchForm.setUserId(usersService.getLogInUserId());
       // 在庫情報取得
       List<Order> orders = new ArrayList<>();
       orders = ordersService.getOrderByCondition(searchForm);
        // モデルに格納
        model.addAttribute("orders", orders);
        // テンプレートを返す
        return "user/purchase_histories";
    }


    /**
     * ユーザ商品購入処理
     * @param productForm 購入商品情報
     * @return　遷移先
     */
    @PostMapping("/user/purchase")
    public String purchase(ProductForm productForm) {
        Order order = new Order();
        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();
        order.setUserId(loginUserId);
        order.setProductId(productForm.getProductId());
        // 登録
        ordersService.save(order);
        return "user/main";
    }

    /**
     * 受注一覧画面の検索条件をクリアする
     * @param searchForm
     * @param model
     * @return 遷移先
     */
    @GetMapping("/user/orders/reset")
    public String reset(SearchForm searchForm, Model model) {
        commonService.setGenres(model);
        // ログイン中のユーザIDを取得しセットする
        Long loginUserId = usersService.getLogInUserId();
        searchForm.setUserId(loginUserId);
       // 在庫情報取得
       List<Order> orders = new ArrayList<>();
       orders = ordersService.getOrderByCondition(searchForm);
        // モデルに格納
        model.addAttribute("orders", orders);
        // テンプレートを返す
        return "user/purchase_histories";
    }


    /**
     * 受注更新画面初期表示
     * @param orderId 受注ID
     * @param productForm　入力内容
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/user/orders/{orderId}")
    public String initUpdate(@PathVariable("orderId") Integer orderId, @ModelAttribute OrderForm orderForm, Model model) {
        // ジャンル情報取得
        commonService.setGenres(model);
        // 在庫番号を条件に在庫を検索
        Order order = ordersService.getOrderById(orderId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(order, orderForm);
        return "user/purchase_history";
    }

    /**
     * 在庫情報を保存する
     * @param orderForm 入力内容
     * @param result BindingResult
     * @param model　Model
     * @return　遷移先
     */
    @PostMapping("/user/orders/save")
    public String save(@Validated OrderForm orderForm, BindingResult result, Model model) {
        try {
            // 情報検索しmodelに格納
            commonService.setGenres(model);
            if (result.hasErrors()) {
                // 入力チェックでエラーがある場合
                return "user/order";
            }
            Order order = new Order();
            // 入力内容を詰め替える
            BeanUtils.copyProperties(orderForm, order);
            ordersService.save(order);

        } catch(OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            return "user/order";
        }

        // リダイレクト(/ordersにアクセスしなおし情報取得の上在庫画面に遷移)
        return "redirect:/user/purchaseHistories";
    }
}
