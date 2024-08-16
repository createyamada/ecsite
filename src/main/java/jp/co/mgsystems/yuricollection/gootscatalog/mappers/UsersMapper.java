package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.Product;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.forms.UserSaveForm;

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
     * @param user 認証ユーザ情報
     */
    public void authenticationCompleted(User user);
    
} 
