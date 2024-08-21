package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;

@Mapper
public interface OrdersMapper {

    /**
     * 検索条件から受注情報を検索する
     * @param searchCondition 検索条件
     * @return 在庫情報
     */
    public List<Order> getOrderByCondition(SearchForm searchCondition);

    /**
     * 受注情報を受注IDから検索
     * @param orderId 受注ID
     * @return 受注情報
     */
    public Order getOrderById(int orderId);


    /**
     * 受注情報を更新
     * @param order 商品情報
     * @return 更新件数
     */
    public int update(Order order);

    /**
     * 受注情報を登録する
     * @param order 在庫情報
     * @return　登録件数
     */
    public int insert(Order order);

}
