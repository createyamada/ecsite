package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@Mapper
public interface ProductsMapper {
    /*
     * 商品情報を全件検索
     * @return 商品情報リスト
     */
    public List<Product> selectAll();

    /*
     * 商品情報を指定条件検索
     * @return 商品情報リスト
     */
    public List<Product> getProductByCondition(SearchForm searchCondition);

    /*
     * 商品情報を商品IDから検索
     * @return 商品情報
     */
    public Product getProductById(int productId);

    /*
     * 商品情報を更新
     * @return 更新件数
     */
    public int update(Product product);

    /**
     * 商品情報を登録する
     * @param product　商品情報
     * @return　登録件数
     */
    public int insert(Product product);

    /**
     * 商品情報を削除する
     * @param product　商品情報
     * @return　登録件数
     */
    public int delete(Product product);
} 
