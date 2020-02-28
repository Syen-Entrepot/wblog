package com.ws.service.impl;

import com.ws.dao.UserRepository;
import com.ws.pojo.User;
import com.ws.service.UserService;
import com.ws.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wusen
 * @date 2019/10/30 0030-下午 15:30
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }

    @Override
    public String checkUserEmail(String username) {
        String user = userRepository.checkUserEmail(username);
        return user;
    }
}
