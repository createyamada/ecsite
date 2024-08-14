package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import java.util.List;

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
    
} 
