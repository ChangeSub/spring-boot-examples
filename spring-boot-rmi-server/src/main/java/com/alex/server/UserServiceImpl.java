package com.alex.server;


import com.alex.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getUser(String name) {
        logger.info("receive message::"+name);
        return "result:"+name;
    }
}
