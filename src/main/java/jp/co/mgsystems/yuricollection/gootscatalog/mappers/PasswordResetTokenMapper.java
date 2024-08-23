package jp.co.mgsystems.yuricollection.gootscatalog.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import jp.co.mgsystems.yuricollection.gootscatalog.beans.PasswordResetToken;

@Mapper
public interface PasswordResetTokenMapper {
    @Insert("INSERT INTO password_reset_tokens (token, user_id, expiration_time) VALUES (#{token}, #{userId}, #{expirationTime})")
    void insertToken(PasswordResetToken token);

    @Select("SELECT * FROM password_reset_tokens WHERE token = #{token}")
    PasswordResetToken findByToken(String token);

    @Delete("DELETE FROM password_reset_tokens WHERE token = #{token}")
    void deleteByToken(String token);
}
