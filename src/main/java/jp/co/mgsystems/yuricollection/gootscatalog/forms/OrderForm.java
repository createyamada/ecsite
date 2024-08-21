package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import java.time.LocalDateTime;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
public class OrderForm {
    // 注文ID
    private Long orderId;
    // 注文ユーザID
    private Long userId;
    // 注文商品ID
    @NonNull
    private Long productId;
    // 商品名
    private String productName;   
    // ジャンル名
    private String genreName;
    // 注文個数
    private Integer orderCnt; 
    // 単価
    private Integer price; 
    // コメント
    private String comment; 
    // キャンセルフラグ
    private Boolean isCancel; 
    // 登録日
    private LocalDateTime createdAt;
    // 更新日
    private LocalDateTime updatedAt;
}
