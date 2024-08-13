package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import java.time.LocalDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import lombok.Data;


/*
 * 更新画面の入力内容
 */
@Data
public class ProductForm {
    // 商品ID
    private Integer productId;
    // 商品名
    @NotBlank
    @Size(min = 1, max= 50)
    private String productName;
    // 価格
    @NotNull
    @Min(0)
    @Max(99999999)
    private Integer price;
    // 在庫数
    @Min(0)
    @Max(1000)
    private Integer stocks;
    // 受注数
    @Min(0)
    @Max(1000)
    private Integer orders;
    // コメント
    @Size(max=200)
    private String comment;
    // 商品ジャンル
    @Valid
    private Genre genre;
    // バージョン
    private Integer version;
    // 登録日付
    private LocalDateTime createdAt;
}
