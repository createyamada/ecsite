package jp.co.mgsystems.yuricollection.gootscatalog.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Stock;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;


@SpringBootTest
public class StocksServiceTest {
    
    // 処理の時間差で失敗になるため
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Autowired
    StocksService service;
    

    @DisplayName("在庫一覧取得 条件：　ジャンルID")
    @ParameterizedTest
    @CsvSource({"1, ネックレス","2, リング", "3, ピアス", "4, イヤリング", "5, ブレスレット", "6, その他小物"})
    void test001(Integer genreId, String genreName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(stocks.size()).isGreaterThanOrEqualTo(1);
        for (Stock stock : stocks) {
            assertThat(stock.getGenreName()).isEqualTo(genreName);
        }
    }

    @DisplayName("在庫一覧取得 条件：　ジャンルID 該当なし")
    @Test
    void test002() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setGenreId(99);
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 出力結果が存在しないか
        assertThat(stocks.size()).isEqualTo(0);
    }

    @DisplayName("在庫一覧取得 条件：　商品名・完全一致")
    @ParameterizedTest
    @CsvSource({"永久のネックレス","破滅のリング", "火事場のブレスレット"})
    void test003(String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(productName);
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(stocks.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の在庫情報が、検索結果の在庫情報と等しいか
        for (Stock stock : stocks) {
            assertThat(stock.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("在庫一覧取得 条件：　商品名・部分一致")
    @ParameterizedTest
    @CsvSource({"ネックレス, 永久のネックレス","破滅, 破滅のリング", "事場のブレスレ, 火事場のブレスレット"})
    void test004(String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(keyword);
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(stocks.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の在庫情報が、検索結果の在庫情報と等しいか
        for (Stock stock : stocks) {
            assertThat(stock.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("在庫一覧取得 条件：　商品名 該当なし")
    @Test
    void test005() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setKeyword("南条");
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 結果の出力が0か
        assertThat(stocks.size()).isEqualTo(0);
    }

    @DisplayName("在庫一覧取得 条件：　ジャンルID 商品名")
    @ParameterizedTest
    @CsvSource({"1,ネックレス, 永久のネックレス","2,破滅, 破滅のリング", "5,事場のブレスレ, 火事場のブレスレット"})
    void test006(Integer genreId, String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        condition.setKeyword(keyword);
        // 調査対象
        List<Stock> stocks = service.getStockByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(stocks.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の在庫情報が、検索結果の在庫情報と等しいか
        for (Stock stock : stocks) {
            assertThat(stock.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("在庫一覧取得 条件：　条件なし　全権該当")
    @Test
    void test008() {
        SearchForm condition = new SearchForm();
        // 調査対象
        List<Stock> stock = service.getStockByCondition(condition);
        // 出力結果が正しいか
        assertThat(stock.size()).isEqualTo(12);
    }

    @DisplayName("在庫Noから在庫情報取得 条件: 在庫番号")
    @ParameterizedTest
    @CsvSource({"1, 永久のネックレス","3,破滅のリング", "7, 勝利のイヤリング"})
    void test009(int stockId, String productName) {
        // 調査対象
        Stock stocks = service.getStockById(stockId);
        // 検索条件が、検索結果の在庫情報と等しいか
        assertThat(stocks.getProductName()).isEqualTo(productName);
    }

    @DisplayName("在庫Noから在庫情報取得 条件: 全項目確認")
    @Test
    void test010() {
        // 調査対象
        Stock stock = service.getStockById(1);
        // 検索条件が、検索結果の在庫情報と等しいか
        assertThat(stock.getStockId()).isEqualTo(1);
        assertThat(stock.getProductId()).isEqualTo(1);
        assertThat(stock.getProductName()).isEqualTo("永久のネックレス");
        assertThat(stock.getStockCnt()).isEqualTo(10);
        assertThat(stock.getGenreName()).isEqualTo("ネックレス");
        assertThat(stock.getVersion()).isEqualTo(1);
        // assertThat(products.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(stock.getUpdatedAt()).isNull();
    }

    @DisplayName("在庫情報更新確認")
    @Test
    @Transactional
    void test011() {
        Stock before = service.getStockById(1);
        before.setStockCnt(100);
        // 更新
        service.save(before);

        // 変更後の在庫情報を取得
        Stock after = service.getStockById(1);
        assertThat(after.getProductName()).isEqualTo("永久のネックレス");
        assertThat(after.getStockCnt()).isEqualTo(100);
        assertThat(after.getVersion()).isEqualTo(before.getVersion() + 1);
    }

    @DisplayName("在庫情報登録")
    @Test
    @Transactional
    void test012() {
        Stock before = new Stock();
        before.setProductId(1L);
        before.setStockCnt(10);

        // 更新
        service.save(before);

        // 変更後の在庫情報を取得
        Stock after = service.getStockById(13);
        // 検索条件が、検索結果の在庫情報と等しいか
        assertThat(after.getStockId()).isEqualTo(13L);
        assertThat(after.getProductId()).isEqualTo(1);
        assertThat(after.getProductName()).isEqualTo("永久のネックレス");
        assertThat(after.getStockCnt()).isEqualTo(10);
        assertThat(after.getGenreName()).isEqualTo("ネックレス");
        assertThat(after.getVersion()).isEqualTo(1);
        // assertThat(after.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(after.getUpdatedAt()).isNull();
    }

    @DisplayName("在庫情報削除確認")
    @Test
    @Transactional
    void test013() {
        Stock before = service.getStockById(1);
        // 削除
        service.delete(before);

        // 変更後の在庫情報を取得
        Stock after = service.getStockById(1);
        assertThat(after).isNull();
    }

}
