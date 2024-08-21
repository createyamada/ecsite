package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@Service
public class CommonService {
    
    @Autowired
    ProductsService productsService;

    /**
     * ジャンル一覧をモデルに追加する
     * @param searchForm
     * @param model
     */
    public void setGenres(Model model) {
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
    public void setProducts(SearchForm searchForm, Model model) {
        // 商品情報取得
        List<Product> products = new ArrayList<>();
        products = productsService.getProductByCondition(searchForm);

        // モデルに格納
        model.addAttribute("products", products);
    }

    /**
     * 検索条件をリセットする
     * @param searchForm
     * @param model
     */
    public void resetSearchForm(SearchForm searchForm, Model model) {
        // 検索条件を初期化
        searchForm = new SearchForm();
        // 情報検索しmodelに格納
        this.setGenres(model);
        this.setProducts(searchForm, model);
    }
}
