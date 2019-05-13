package com.alex.init;

import com.alex.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserInit implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    /**
     * 项目启动就会执行
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        System.out.println(userService.getUser("test"));
    }
}
