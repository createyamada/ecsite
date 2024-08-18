package jp.co.mgsystems.yuricollection.gootscatalog.beans;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品注文情報
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
     // 注文ID
    private Long orderId;
    // 注文ユーザID
    private Long userId;
    // 注文商品ID
    private Long productId;   
    // 注文個数
    private Integer orderCnt; 
    // キャンセルフラグ
    private Boolean isCancel; 
    // 登録日
    private LocalDateTime createdAt;
    // 更新日
    private LocalDateTime updatedAt;
}
