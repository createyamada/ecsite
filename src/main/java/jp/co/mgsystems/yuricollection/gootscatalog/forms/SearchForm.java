package jp.co.mgsystems.yuricollection.gootscatalog.forms;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchForm {
    // ジャンルID
    private Integer genreId;
    // キーワード
    @Size(min = 2,max = 10)
    private String keyword;
}
