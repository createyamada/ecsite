package jp.co.mgsystems.yuricollection.gootscatalog.beans;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品在庫情報
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    // 在庫ID
    private Long stockId;
    // 在庫商品ID
    private Long productId;   
    // 在庫個数
    private Integer stockCnt; 
    // バージョン
    private Integer version;
    // 登録日
    private LocalDateTime createdAt;
    // 更新日
    private LocalDateTime updatedAt;
}
