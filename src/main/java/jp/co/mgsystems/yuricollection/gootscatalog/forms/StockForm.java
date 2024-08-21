package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockForm {
    // 在庫ID
    private Long stockId;
    // 在庫商品ID
    @NotNull
    private Long productId;  
    // 商品名
    private String productName;
    // ジャンル名
    private String genreName;
    // 在庫個数
    // 在庫数
    @NotNull
    @Min(0)
    @Max(1000)
    private Integer stockCnt; 
    // バージョン
    private Integer version;
    // 登録日
    private LocalDateTime createdAt;
    // 更新日
    private LocalDateTime updatedAt;
}
