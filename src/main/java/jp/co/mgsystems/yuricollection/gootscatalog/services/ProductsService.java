package jp.co.mgsystems.yuricollection.gootscatalog.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.SearchForm;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.Genre;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.ProductsMapper;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.GenresMapper;

@Service
public class ProductsService {
    
    @Autowired
    ProductsMapper productsMapper;

    @Autowired
    GenresMapper genresMapper;

    @Autowired
    MessageSource messageSource;

    /**
     * 商品情報をすべて取得する
     * @return 商品情報
     */
    public List<Product> getProduct() {
        return productsMapper.selectAll();
    }

    /**
     * 検索条件に合った商品情報を取得する
     * @param searchCondition
     * @return 商品情報
     */
    public List<Product> getProductByCondition(SearchForm searchCondition) {
        return productsMapper.getProductByCondition(searchCondition);
    }

    /**
     * 最近登録された5件の商品情報を取得
     * @return 商品情報
     */
    public List<Product> getLatestProduct() {
        return productsMapper.getLatestProduct();
    }
    

    /**
     * 商品IDに紐づく商品情報を取得する
     * @param productId
     * @return 商品情報
     */
    public Product getProductById(int productId) {
        return productsMapper.getProductById(productId);
    }

    /**
     * ジャンルを一覧取得する
     * @return ジャンル情報
     */
    public List<Genre> getGenre() {
        return genresMapper.selectAll();
    }

    /**
     * 商品情報を更新する
     * @param product
     * @return 更新件数
     */
    @Transactional
    private int update(Product product) {
        int cnt =  productsMapper.update(product);
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
     * 商品情報を登録する
     * @param product
     * @return 更新件数
     */
    @Transactional
    private int add(Product product) {
        int cnt =  productsMapper.insert(product);
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
     * 商品情報を削除する
     * @param product
     * @return 更新件数
     */
    @Transactional
    public int delete(Product product) {
        int cnt =  productsMapper.delete(product);
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



    /**
     * 商品情報を保存する
     * @param product
     * @return 保存件数
     */
    @Transactional
    public int save(Product product) {
        if (product.getProductId() == null) {
            // 登録
            return this.add(product);
        } else {
            // 更新
            return this.update(product);

        }
    }

}
