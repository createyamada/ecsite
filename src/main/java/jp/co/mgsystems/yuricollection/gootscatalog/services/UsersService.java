package jp.co.mgsystems.yuricollection.gootscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.mgsystems.yuricollection.gootscatalog.beans.User;
import jp.co.mgsystems.yuricollection.gootscatalog.mappers.UsersMapper;

@Component
public class UsersService implements UserDetailsService {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersMapper.selectByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("ユーザが存在しない");
        }

        return user;
    }
    
}
