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

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@SpringBootTest
public class OrdersServiceTest {
    

    // 処理の時間差で失敗になるため
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Autowired
    OrdersService service;
    

    @DisplayName("受注一覧取得 条件：　ジャンルID")
    @ParameterizedTest
    @CsvSource({"1, ネックレス","2, リング", "3, ピアス", "4, イヤリング", "5, ブレスレット", "6, その他小物"})
    void test001(Integer genreId, String genreName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(orders.size()).isGreaterThanOrEqualTo(1);
        for (Order order : orders) {
            assertThat(order.getGenreName()).isEqualTo(genreName);
        }
    }

    @DisplayName("受注一覧取得 条件：　ジャンルID 該当なし")
    @Test
    void test002() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setGenreId(99);
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 出力結果が存在しないか
        assertThat(orders.size()).isEqualTo(0);
    }

    @DisplayName("受注一覧取得 条件：　商品名・完全一致")
    @ParameterizedTest
    @CsvSource({"永久のネックレス","破滅のリング", "火事場のブレスレット"})
    void test003(String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(productName);
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(orders.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の受注情報が、検索結果の受注情報と等しいか
        for (Order order : orders) {
            assertThat(order.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("受注一覧取得 条件：　商品名・部分一致")
    @ParameterizedTest
    @CsvSource({"ネックレス, 永久のネックレス","破滅, 破滅のリング", "事場のブレスレ, 火事場のブレスレット"})
    void test004(String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setKeyword(keyword);
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(orders.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の受注情報が、検索結果の受注情報と等しいか
        for (Order order : orders) {
            assertThat(order.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("受注一覧取得 条件：　商品名 該当なし")
    @Test
    void test005() {
        SearchForm condition = new SearchForm();
        // 存在しないジャンルIDを設定
        condition.setKeyword("南条");
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 結果の出力が0か
        assertThat(orders.size()).isEqualTo(0);
    }

    @DisplayName("受注一覧取得 条件：　ジャンルID 商品名")
    @ParameterizedTest
    @CsvSource({"1,ネックレス, 永久のネックレス","2,破滅, 破滅のリング", "5,事場のブレスレ, 火事場のブレスレット"})
    void test006(Integer genreId, String keyword, String productName) {
        SearchForm condition = new SearchForm();
        condition.setGenreId(genreId);
        condition.setKeyword(keyword);
        condition.setUserId(1L);
        // 調査対象
        List<Order> orders = service.getOrderByCondition(condition);
        // 結果の出力が1件より多いか
        assertThat(orders.size()).isGreaterThanOrEqualTo(1);
        // 検索条件の受注情報が、検索結果の受注情報と等しいか
        for (Order order : orders) {
            assertThat(order.getProductName()).isEqualTo(productName);
        }
    }

    @DisplayName("受注一覧取得 条件：　条件なし　全権該当")
    @Test
    void test008() {
        SearchForm condition = new SearchForm();
        condition.setUserId(1L);
        // 調査対象
        List<Order> order = service.getOrderByCondition(condition);
        // 出力結果が正しいか
        assertThat(order.size()).isEqualTo(7);
    }

    @DisplayName("受注Noから受注情報取得 条件: 受注番号")
    @ParameterizedTest
    @CsvSource({"1, 永久のネックレス","5,破滅のリング", "10, 勝利のイヤリング"})
    void test009(int orderId, String productName) {
        // 調査対象
        Order orders = service.getOrderById(orderId);
        // 検索条件が、検索結果の受注情報と等しいか
        assertThat(orders.getProductName()).isEqualTo(productName);
    }

    @DisplayName("受注Noから受注情報取得 条件: 全項目確認")
    @Test
    void test010() {
        // 調査対象
        Order order = service.getOrderById(1);
        // 検索条件が、検索結果の受注情報と等しいか
        assertThat(order.getOrderId()).isEqualTo(1);
        assertThat(order.getProductId()).isEqualTo(1);
        assertThat(order.getProductName()).isEqualTo("永久のネックレス");
        assertThat(order.getOrderCnt()).isEqualTo(11);
        assertThat(order.getGenreName()).isEqualTo("ネックレス");
        // assertThat(products.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(order.getUpdatedAt()).isNull();
    }

    @DisplayName("受注情報更新確認")
    @Test
    @Transactional
    void test011() {
        Order before = service.getOrderById(1);
        before.setIsCancel(true);
        // 更新
        service.save(before);

        // 変更後の在庫情報を取得
        Order after = service.getOrderById(1);
        assertThat(after.getProductName()).isEqualTo("永久のネックレス");
        assertThat(after.getIsCancel()).isEqualTo(true);
    }

    @DisplayName("受注情報登録")
    @Test
    @Transactional
    void test013() {
        Order before = new Order();
        before.setUserId(1L);
        before.setProductId(1L);
        before.setOrderCnt(10);

        // 更新
        service.save(before);

        // 変更後の在庫情報を取得
        Order after = service.getOrderById(15);
        // 検索条件が、検索結果の受注情報と等しいか
        assertThat(after.getOrderId()).isEqualTo(15L);
        assertThat(after.getProductId()).isEqualTo(1);
        assertThat(after.getProductName()).isEqualTo("永久のネックレス");
        assertThat(after.getOrderCnt()).isEqualTo(10);
        assertThat(after.getGenreName()).isEqualTo("ネックレス");
        // assertThat(after.getCreatedAt().format(datetimeFormatter)).isEqualTo(LocalDate.now().format(datetimeFormatter));
        assertThat(after.getUpdatedAt()).isNull();
    }

}
