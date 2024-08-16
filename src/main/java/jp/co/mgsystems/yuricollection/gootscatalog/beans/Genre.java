package jp.co.mgsystems.yuricollection.gootscatalog.beans;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 商品ジャンル
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    // ジャンルID
    @NotNull
    private Long genreId;
    // ジャンル名
    private String genreName;
}


