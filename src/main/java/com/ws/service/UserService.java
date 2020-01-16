package com.ws.service;
import com.ws.pojo.User;

/**
 * @author wusen
 * @date 2019/10/30 0030-上午 11:23
 */
public interface UserService {

    User checkUser(String username,String password);

}
