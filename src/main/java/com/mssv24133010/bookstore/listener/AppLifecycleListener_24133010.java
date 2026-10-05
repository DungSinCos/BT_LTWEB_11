package com.mssv24133010.bookstore.listener;

import com.mssv24133010.bookstore.util.JPAUtil_24133010;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener_24133010 implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil_24133010.close();
    }
}
