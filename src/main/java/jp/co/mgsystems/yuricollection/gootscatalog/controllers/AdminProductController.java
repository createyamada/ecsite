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
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.ProductForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.ProductsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
// @RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    ProductsService productsService;

    /**
     * 検索条件から商品を検索する
     * @param searchForm
     * @param model
     * @return 遷移先
     */
    @RequestMapping("/admin/products")
    public String products(@Validated SearchForm searchForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックでエラーがある場合
            return "admin/product_list";
        }
        // 情報検索しmodelに格納
        this.setGenres(model);
        // 商品情報取得
        setProducts(searchForm, model);
        // テンプレートを返す
        return "admin/product_list";
    }


    /**
     * 検索条件をクリアする
     * @param searchForm
     * @param model
     * @return 遷移先
     */
    @GetMapping("/admin/products/reset")
    public String reset(SearchForm searchForm, Model model) {
        // 検索条件を初期化
        searchForm = new SearchForm();
        // 情報検索しmodelに格納
        this.setGenres(model);
        this.setProducts(searchForm, model);
        // テンプレートを返す
        return "admin/product_list";
    }

    /**
     * 更新画面初期表示
     * @param productId　商品番号
     * @param productForm　入力内容
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/admin/products/{productId}")
    public String initUpdate(@PathVariable("productId") Integer productId, @ModelAttribute ProductForm productForm, Model model) {
        // ジャンル情報取得
        setGenres(model);
        // 商品番号を条件に商品を検索
        Product product = productsService.getProductById(productId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(product, productForm);
        return "admin/product";
    }




    /**
     * 商品情報を保存する
     * @param productForm 入力内容
     * @param result BindingResult
     * @param model　Model
     * @return　遷移先
     */
    @PostMapping("/admin/products/save")
    public String save(@Validated ProductForm productForm, BindingResult result, Model model) {
        try {
            // 情報検索しmodelに格納
            this.setGenres(model);
            if (result.hasErrors()) {
                // 入力チェックでエラーがある場合
                return "product";
            }
            Product product = new Product();
            // 入力内容を詰め替える
            BeanUtils.copyProperties(productForm, product);
            productsService.save(product);
        } catch(OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            return "admin/product";
        }

        // リダイレクト(/productsにアクセスしなおし情報取得の上商品画面に遷移)
        return "redirect:/admin/products";
    }

    /**
     * 商品情報を削除する
     * @param productForm 削除内容
     * @param result BindingResult
     * @param model　Model
     * @return　遷移先
     */
    @PostMapping("/admin/products/delete")
    public String delete(@ModelAttribute ProductForm productForm, BindingResult result, Model model) {
        try {
            Product product = new Product();
            // 入力内容を詰め替える
            BeanUtils.copyProperties(productForm, product);
            productsService.delete(product);
        } catch(OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            // 情報検索しmodelに格納
            this.setGenres(model);
            return "product";
        }

        // リダイレクト(/productsにアクセスしなおし情報取得の上一覧画面に遷移)
        return "redirect:/admin/products";
    }

    /**
     * 更新画面初期表示
     * @param productId　商品番号
     * @param productForm　入力内容
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/admin/products/new")
    public String initNew(@ModelAttribute ProductForm productForm, Model model) {
        // ジャンル情報取得
        setGenres(model);
        return "admin/product";
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

    /**
     * 検索条件の商品一覧をモデルに追加する
     * @param searchForm
     * @param model
     */
    private void setProducts(SearchForm searchForm, Model model) {
        // 商品情報取得
        List<Product> products = new ArrayList<>();
        products = productsService.getProductByCondition(searchForm);

        // モデルに格納
        model.addAttribute("products", products);
    }
    
}
