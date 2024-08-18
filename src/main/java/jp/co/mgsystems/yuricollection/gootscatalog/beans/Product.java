package jp.co.mgsystems.yuricollection.gootscatalog.beans;

import java.time.LocalDateTime;
// セッターゲッター等自動生成
import lombok.Data;
// コンストラクターを自動生成
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 商品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    // 商品ID
    private Long productId;
    // 商品名
    private String productName;
    // 価格
    private Integer price;
    // 在庫数
    private Integer stocks;
    // 受注数
    private Long orders;
    // コメント
    private String comment;
    // 商品ジャンル
    private Genre genre;
    // バージョン
    private Integer version;
    // 登録日
    private LocalDateTime createdAt;
    // 更新日
    private LocalDateTime updatedAt;
}
