package com.guda.ui.util;

import com.guda.ui.driver.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (scenario.isFailed())
            attachScreenShot();
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }

    @Attachment()
    private static byte[] attachScreenShot() {
        return ((TakesScreenshot) Objects.requireNonNull(getDriver())).getScreenshotAs(OutputType.BYTES);
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
