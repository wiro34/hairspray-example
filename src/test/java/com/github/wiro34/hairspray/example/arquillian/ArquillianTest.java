package com.github.wiro34.hairspray.example.arquillian;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ArquillianTest extends Arquillian {
    @Resource
    private UserTransaction transaction;

    @ArquillianResource
    private InitialContext initialContext;

    protected boolean isInContainer() {
        return (null != initialContext);
    }

    @BeforeMethod
    public void startTransaction() throws SystemException, NotSupportedException {
        if (isInContainer()) {
            transaction.begin();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void endTransaction() throws SystemException {
        if (isInContainer()) {
            transaction.rollback();
        }
    }
}
