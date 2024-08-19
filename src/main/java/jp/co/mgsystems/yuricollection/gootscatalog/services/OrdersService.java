package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Order;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.OrdersMapper;

@Service
public class OrdersService {

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    MessageSource messageSource;

    /**
     * 受注情報を保存する
     * @param order
     * @return 保存件数
     */
    @Transactional
    public int save(Order order) {
        if (order.getOrderId() == null) {
            // 登録
            return this.add(order);
        } else {
            // 更新
            return this.update(order);

        }
    }

    /**
     * 受注情報を登録する
     * @param order
     * @return 更新件数
     */
    @Transactional
    private int add(Order order) {
        int cnt =  ordersMapper.insert(order);
        // 登録できなかった場合
        if (cnt == 0) {
            throw new RuntimeException(
                messageSource.getMessage("error.Runtime",
                new String[] {"登録に失敗しました。"},
                Locale.JAPANESE)
            );
        }
        return cnt;
    }


    /**
     * 在庫情報を更新する
     * @param order
     * @return 更新件数
     */
    @Transactional
    private int update(Order order) {
        int cnt =  ordersMapper.update(order);
        // 更新できなかった場合(versionエラーの可能性あり)
        if (cnt == 0) {
            throw new OptimisticLockingFailureException(
                messageSource.getMessage("error.OptimisticLockingFailure",
                null,
                Locale.JAPANESE)
            );
        }
        // 2件以上更新は想定外(SQL不備の可能性あり)
        if (cnt > 1) {
            throw new RuntimeException(
                messageSource.getMessage("error.Runtime",
                new String[] {"2件以上更新されました"},
                Locale.JAPANESE)
            );
        }
        return cnt;
    }

    /**
     * 検索条件から受注情報を取得
     * @param searchCondition 検索条件
     * @return 在庫情報
     */
    public List<Order> getOrderByCondition(SearchForm searchCondition) {
        return ordersMapper.getOrderByCondition(searchCondition);
    }
}
