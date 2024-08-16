package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@Mapper
public interface ProductsMapper {
    /**
     * すべての商品を取得
     * @param
     * @return 商品情報リスト
     */
    public List<Product> selectAll();

    /**
     * 商品情報を指定条件検索
     * @param searchCondition 
     * @return 商品情報リスト
     */
    public List<Product> getProductByCondition(SearchForm searchCondition);

    /**
     * 商品情報を商品IDから検索
     * @param productId 商品ID
     * @return 商品情報
     */
    public Product getProductById(int productId);

    /**
     * 最新の商品情報を5件取得
     * @param 
     * @return 商品情報
     */
    public List<Product> getLatestProduct();

    /**
     * 商品情報を更新
     * @param product 商品情報
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
