package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import org.apache.ibatis.annotations.Mapper;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;

@Mapper
public interface OrdersMapper {
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
