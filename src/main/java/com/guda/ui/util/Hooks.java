package com.guda.ui.util;

import com.guda.ui.driver.DriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Dimension;
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
    public void setUp() throws Exception {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);

            return driverFactory;
        });

        driverFactoryThread.get().getDriver().manage().window().setSize(new Dimension(1920, 1024));
    }

    @After
    public void cleanUp(Scenario scenario) {
        if (scenario.isFailed()) {
            attachScreenShot();
            attachConsoleLog();
        }

        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }

    @Attachment("Failure screenshot")
    private static byte[] attachScreenShot() {
        return ((TakesScreenshot) Objects.requireNonNull(getDriver())).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment("Browser console log")
    private byte[] attachConsoleLog() {
        ArrayList consoleLogs = BrowserConsoleLog.consoleAllLogs(Objects.requireNonNull(getDriver()));
        String consoleLog = "CONSOLE LOG: ";

        if (consoleLogs.isEmpty())
            consoleLog += " NO CONSOLE LOGS DETECTED!";
        else
            for (Object log : consoleLogs)
                consoleLog += log;

        return consoleLog.getBytes();
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
