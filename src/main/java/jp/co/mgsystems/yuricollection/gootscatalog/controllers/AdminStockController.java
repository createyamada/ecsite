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
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Stock;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.StockForm;
import jp.co.mgsystems.yuricollection.gootscatalog.services.CommonService;
import jp.co.mgsystems.yuricollection.gootscatalog.services.StocksService;


@Controller
public class AdminStockController {

    @Autowired
    StocksService stocksService;


    @Autowired
    CommonService commonService;

    /**
     * 検索条件から商品を検索する
     * @param searchForm
     * @param model
     * @return 遷移先
     */
    @RequestMapping("/admin/stocks")
    public String stocks(@Validated SearchForm searchForm, Model model) {
        // 情報検索しmodelに格納
        commonService.setGenres(model);
       // 在庫情報取得
       List<Stock> stocks = new ArrayList<>();
       stocks = stocksService.getStockByCondition(searchForm);
        // モデルに格納
        model.addAttribute("stocks", stocks);
        // テンプレートを返す
        return "admin/stock_list";
    }

    /**
     * 在庫一覧画面の検索条件をクリアする
     * @param searchForm
     * @param model
     * @return 遷移先
     */
    @GetMapping("/admin/stocks/reset")
    public String reset(SearchForm searchForm, Model model) {
        commonService.setGenres(model);
       // 在庫情報取得
       List<Stock> stocks = new ArrayList<>();
       stocks = stocksService.getStockByCondition(searchForm);
        // モデルに格納
        model.addAttribute("stocks", stocks);
        // テンプレートを返す
        return "admin/stock_list";
    }


    /**
     * 更新画面初期表示
     * @param stockId 在庫ID
     * @param productForm　入力内容
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/admin/stocks/{stockId}")
    public String initUpdate(@PathVariable("stockId") Integer productId, @ModelAttribute StockForm stockForm, Model model) {
        // ジャンル情報取得
        commonService.setGenres(model);
        // 在庫番号を条件に在庫を検索
        Stock stock = stocksService.getStockById(productId);
        // 検索結果を入力内容に詰め替える
        BeanUtils.copyProperties(stock, stockForm);
        return "admin/stock";
    }

    /**
     * 在庫情報を保存する
     * @param stockForm 入力内容
     * @param result BindingResult
     * @param model　Model
     * @return　遷移先
     */
    @PostMapping("/admin/stocks/save")
    public String save(@Validated StockForm stockForm, BindingResult result, Model model) {
        try {
            // 情報検索しmodelに格納
            commonService.setGenres(model);
            if (result.hasErrors()) {
                // 入力チェックでエラーがある場合
                return "admin/stock";
            }
            Stock stock = new Stock();
            // 入力内容を詰め替える
            BeanUtils.copyProperties(stockForm, stock);
            stocksService.save(stock);

        } catch(OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            return "admin/stock";
        }

        // リダイレクト(/productsにアクセスしなおし情報取得の上在庫画面に遷移)
        return "redirect:/admin/stocks";
    }

    /**
     * 在庫情報を削除する
     * @param stockForm 削除内容
     * @param result BindingResult
     * @param model　Model
     * @return　遷移先
     */
    @PostMapping("/admin/stocks/delete")
    public String delete(@ModelAttribute StockForm stockForm, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                // 入力チェックでエラーがある場合
                return "admin/stock";
            }
            Stock stock = new Stock();
            // 入力内容を詰め替える
            BeanUtils.copyProperties(stockForm, stock);
            stocksService.delete(stock);
        } catch(OptimisticLockingFailureException e) {
            result.addError(new ObjectError("global", e.getMessage()));
            // 情報検索しmodelに格納
            commonService.setGenres(model);
            return "admin/stock";
        }

        // リダイレクト(/stocksにアクセスしなおし情報取得の上一覧画面に遷移)
        return "redirect:/admin/stocks";
    }

    /**
     * 在庫更新画面初期表示
     * @param stockForm　入力情報
     * @param model model
     * @return 遷移先
     */
    @GetMapping("/admin/stocks/new")
    public String initNew(@ModelAttribute StockForm stockForm, Model model) {
        // ジャンル情報取得
        commonService.setGenres(model);
        return "admin/stock";
    }
}
