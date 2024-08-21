package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import org.apache.ibatis.annotations.Mapper;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;

@Mapper
public interface UsersMapper {

    /**
     * ユーザー名からユーザ情報を検索する
     * @param username　ユーザ名
     * @return　ユーザ情報
     */
    public User selectByUsername(String username);

    /**
     * ユーザーIDからユーザ情報を検索する
     * @param userId ユーザＩＤ
     * @return　ユーザ情報
     */
    public User selectByUserId(Long userId);


    /**
     * 
     * @param user 新規登録ユーザ情報
     */
    public void insert(User user);

    /**
     * 
     * @param user 新規登録ユーザ情報
     * @return int 更新件数
     */
    public int update(User user);


    /**
     * 
     * @param user 認証ユーザ情報
     */
    public void authenticationCompleted(User user);
    
} 
