package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Stock;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@Mapper
public interface StocksMapper {
    

    /**
     * 在庫情報を更新
     * @param stock 商品情報
     * @return 更新件数
     */
    public int update(Stock stock);

    /**
     * 在庫情報を登録する
     * @param stock 在庫情報
     * @return　登録件数
     */
    public int insert(Stock stock);

    
    /**
     * 検索条件から在庫情報を検索する
     * @param searchCondition 検索条件
     * @return 在庫情報
     */
    public List<Stock> getStockByCondition(SearchForm searchCondition);

}
