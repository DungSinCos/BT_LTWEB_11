package com.mssv24133010.bookstore.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil_24133010 {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("bookstorePU");

    private JPAUtil_24133010() {}

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void close() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}
