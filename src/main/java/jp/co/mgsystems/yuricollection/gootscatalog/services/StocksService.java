package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Stock;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.StocksMapper;

@Service
public class StocksService {
    
    @Autowired
    StocksMapper stocksMapper;

    @Autowired
    MessageSource messageSource;

    /**
     * 検索条件から在庫情報を取得
     * @param searchCondition 検索条件
     * @return 在庫情報
     */
    public List<Stock> getStockByCondition(SearchForm searchCondition) {
        return stocksMapper.getStockByCondition(searchCondition);
    }

    /**
     * 在庫IDに紐づく在庫情報を取得する
     * @param stockId
     * @return 商品情報
     */
    public Stock getStockById(int stockId) {
        return stocksMapper.getStockById(stockId);
    }

    /**
     * 在庫情報を保存する
     * @param stock
     * @return 保存件数
     */
    @Transactional
    public int save(Stock stock) {
        if (stock.getStockId() == null) {
            // 登録
            return this.add(stock);
        } else {
            // 更新
            return this.update(stock);

        }
    }

    /**
     * 在庫情報を登録する
     * @param stock
     * @return 更新件数
     */
    @Transactional
    private int add(Stock stock) {
        int cnt =  stocksMapper.insert(stock);
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
     * @param stock
     * @return 更新件数
     */
    @Transactional
    private int update(Stock stock) {
        int cnt =  stocksMapper.update(stock);
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
     * 在庫情報を削除する
     * @param stock
     * @return 更新件数
     */
    @Transactional
    public int delete(Stock stock) {
        int cnt =  stocksMapper.delete(stock);
        // 削除できなかった場合(versionエラーの可能性あり)
        if (cnt == 0) {
            throw new OptimisticLockingFailureException(
                messageSource.getMessage("error.OptimisticLockingFailure",
                null,
                Locale.JAPANESE)
            );
        }
        // 2件以上削除は想定外(SQL不備の可能性あり)
        if (cnt > 1) {
            throw new RuntimeException(
                messageSource.getMessage("error.Runtime",
                new String[] {"2件以上削除されました"},
                Locale.JAPANESE)
            );
        }
        return cnt;
    }
}
