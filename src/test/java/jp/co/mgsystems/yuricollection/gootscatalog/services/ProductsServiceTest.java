package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductsServiceTest {

    // 処理の時間差で失敗になるため
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    @Autowired
    ProductsService service;

    @DisplayName("商品一覧取得 条件：　ジャンルID")
    @ParameterizedTest
    @CsvSource({"1, ネックレス","2, リング", "3, ピアス", "4, イヤリング", "5, ブレスレット", "6, その他小物"})
    void test001(Integer genreId, String genreName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(products.size()).isGreaterThanOrEqualTo(1);
        for (Product product : products) {
            assertThat(product.getGenre().getGenreName()).isEqualTo(genreName);
        }
    }

    @DisplayName("商品一覧取得 条件：　ジャンルID 該当なし")
    @Test
    void test002() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setGenreId(99);
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 出力結果が存在しないか
        assertThat(products.size()).isEqualTo(0);
    }

    @DisplayName("商品一覧取得 条件：　商品名・完全一致")
    @ParameterizedTest
    @CsvSource({"永久のネックレス","破滅のリング", "火事場のブレスレット"})
    void test003(String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(productName);
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(products.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の商品名が、検索結果の商品名と等しいか
        for (Product product : products) {
            assertThat(product.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("商品一覧取得 条件：　商品名・部分一致")
    @ParameterizedTest
    @CsvSource({"ネックレス, 永久のネックレス","破滅, 破滅のリング", "事場のブレスレ, 火事場のブレスレット"})
    void test004(String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(keyword);
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(products.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の商品名が、検索結果の商品名と等しいか
        for (Product product : products) {
            assertThat(product.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("商品一覧取得 条件：　商品名 該当なし")
    @Test
    void test005() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setKeyword("南条");
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 結果の出力が0か
        assertThat(products.size()).isEqualTo(0);
    }

    @DisplayName("商品一覧取得 条件：　ジャンルID 商品名")
    @ParameterizedTest
    @CsvSource({"1,ネックレス, 永久のネックレス","2,破滅, 破滅のリング", "5,事場のブレスレ, 火事場のブレスレット"})
    void test006(Integer genreId, String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        condition.setKeyword(keyword);
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(products.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の商品名が、検索結果の商品名と等しいか
        for (Product product : products) {
            assertThat(product.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("商品一覧取得 条件：　条件なし　全権該当")
    @Test
    void test008() {
        SearchForm condition = new SearchForm();
        // 調査対象
        List<Product> products = service.getProductByCondition(condition);
        // 出力結果が6件か
        assertThat(products.size()).isEqualTo(6);
    }

    @DisplayName("商品Noから商品情報取得 条件: 商品番号")
    @ParameterizedTest
    @CsvSource({"1, 永久のネックレス","2,破滅のリング", "5, 火事場のブレスレット"})
    void test009(int productId, String productName) {
        // 調査対象
        Product products = service.getProductById(productId);
        // 検索条件が、検索結果の商品名と等しいか
        assertThat(products.getProductName()).isEqualTo(productName);
    }

    @DisplayName("商品Noから商品情報取得 条件: 全項目確認")
    @Test
    void test010() {
        // 調査対象
        Product products = service.getProductById(1);
        // 検索条件が、検索結果の商品名と等しいか
        assertThat(products.getProductId()).isEqualTo(1);
        assertThat(products.getProductName()).isEqualTo("永久のネックレス");
        assertThat(products.getPrice()).isEqualTo(1200);
        assertThat(products.getStocks()).isEqualTo(10);
        assertThat(products.getOrders()).isEqualTo(20);
        assertThat(products.getComment()).isEqualTo("永遠を誓うことのできるネックレスです");
        assertThat(products.getGenre().getGenreId()).isEqualTo(1);
        assertThat(products.getVersion()).isEqualTo(1);
        assertThat(products.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(products.getUpdatedAt()).isNull();
    }

    @DisplayName("商品情報更新確認")
    @Test
    @Transactional
    void test011() {
        Product before = service.getProductById(1);
        before.setProductName("永遠のネックレス");
        // 更新
        service.save(before);

        // 変更後の商品名を取得
        Product after = service.getProductById(1);
        assertThat(after.getProductName()).isEqualTo("永遠のネックレス");
        assertThat(after.getVersion()).isEqualTo(before.getVersion() + 1);
    }

    @DisplayName("商品情報登録")
    @Test
    @Transactional
    void test012() {
        Product before = new Product();
        before.setProductName("カンタベリのロッド");
        before.setGenre(new Genre(6,"その他小物"));
        before.setPrice(70000);
        before.setStocks(50);
        before.setOrders(1);
        before.setComment("カンタベリ大司教が使っていたロッドです");
        // 更新
        service.save(before);

        // 変更後の商品名を取得
        Product after = service.getProductById(7);
        assertThat(after.getProductName()).isEqualTo("カンタベリのロッド");
        assertThat(after.getPrice()).isEqualTo(70000);
        assertThat(after.getStocks()).isEqualTo(50);
        assertThat(after.getOrders()).isEqualTo(1);
        assertThat(after.getComment()).isEqualTo("カンタベリ大司教が使っていたロッドです");
        assertThat(after.getGenre().getGenreId()).isEqualTo(6);
        assertThat(after.getVersion()).isEqualTo(1);
        assertThat(after.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(after.getUpdatedAt()).isNull();
    }

    @DisplayName("商品情報削除確認")
    @Test
    @Transactional
    void test013() {
        Product before = service.getProductById(1);
        before.setProductName("永遠のネックレス");
        // 削除
        service.delete(before);

        // 変更後の商品名を取得
        Product after = service.getProductById(1);
        assertThat(after).isNull();
    }
}
