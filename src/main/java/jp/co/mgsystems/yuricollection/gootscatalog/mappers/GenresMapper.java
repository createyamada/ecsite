package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;

@Mapper
public interface GenresMapper {
    /*
     * 商品情報を全件検索
     * @return 商品情報リスト
    */
    public List<Genre> selectAll(); 
} 
