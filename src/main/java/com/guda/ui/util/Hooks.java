package com.guda.ui.util;

import com.guda.ui.driver.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hooks {
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverFactoryThread;

    @Before
    public void setUp() {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);

            return driverFactory;
        });
    }

    @After
    public void cleanUp(Scenario scenario) {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }

    public static RemoteWebDriver getDriver() {
        try {
            return driverFactoryThread.get().getDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
