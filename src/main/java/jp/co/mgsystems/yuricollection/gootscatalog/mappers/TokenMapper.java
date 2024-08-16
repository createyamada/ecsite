package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.VerificationToken;

@Mapper
public interface TokenMapper {

    @Insert("INSERT INTO verification_tokens(token, user_id, expiration_time) VALUES(#{token}, #{userId}, #{expirationTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertToken(VerificationToken token);

    @Select("SELECT * FROM verification_tokens WHERE token = #{token}")
    VerificationToken findByToken(String token);

    @Delete("DELETE FROM verification_tokens WHERE id = #{id}")
    void deleteToken(Long id);
}
