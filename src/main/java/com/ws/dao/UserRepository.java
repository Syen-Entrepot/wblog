package com.ws.dao;

import com.ws.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author wusen
 * @date 2019/10/30 0030-下午 15:32
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

    @Query(value = "select u.email from User u where u.username =?1")
    String checkUserEmail(String username);

}
