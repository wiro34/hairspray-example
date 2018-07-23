package com.github.wiro34.hairspray.example.ds;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.io.File;
import java.util.Arrays;

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

    @Deployment(name = "default")
    public static WebArchive deploy() {
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.github.wiro34.hairspray.example")
                .addAsLibraries(
                        pom.importRuntimeDependencies()
                                .resolve(
                                        "org.testng:testng",
                                        "com.github.wiro34:hairspray-jpa"
                                )
                                .withTransitivity()
                                .asFile()
                );
        Arrays.stream(new File("src/test/resources").listFiles()).forEach(war::addAsResource);
        System.out.println(war.toString(true));
        return war;
    }
}
