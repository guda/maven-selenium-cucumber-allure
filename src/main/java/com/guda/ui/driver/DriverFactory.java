package com.guda.ui.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.guda.ui.driver.DriverType.CHROME;
import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;

@SuppressWarnings("unused")
public class DriverFactory {

    private RemoteWebDriver driver;
    private DriverType selectedDriverType;

    private String browser;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

    public DriverFactory() {
        DriverType driverType = CHROME;
        browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public RemoteWebDriver getDriver() throws Exception {
        if (null == driver) {
            instantiateWebDriver(selectedDriverType);
        }

        return driver;
    }

    public RemoteWebDriver getStoredDriver() {
        return driver;
    }

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
        //TODO add in a real logger instead of System.out
        System.out.println(" ");
        System.out.println("Local Operating System: " + operatingSystem);
        System.out.println("Local Architecture: " + systemArchitecture);
        System.out.println("Selected Browser: " + selectedDriverType);
        System.out.println("Connecting to Selenium Grid: " + useRemoteWebDriver);
        System.out.println(" ");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (proxyEnabled) {
            Proxy proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
            desiredCapabilities.setCapability(PROXY, proxy);
        }

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            desiredCapabilities.setBrowserName(selectedDriverType.toString());
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            setDriverBinarySystemProperty();
            driver = driverType.getWebDriverObject(desiredCapabilities);
        }
    }

    private void setDriverBinarySystemProperty() {

        if (operatingSystem.contains("WINDOWS")) {
            switch (browser) {
                case "CHROME":
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/windows/chromedriver.exe");
                case "FIREFOX":
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/windows/geckodriver.exe");
                case "IE":
                    System.setProperty("webdriver.ie.driver", "src/main/resources/drivers/windows/IEDriverServer.exe");
                case "EDGE":
                    System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/windows/MicrosoftWebDriver.exe");
                case "OPERA":
                    System.setProperty("webdriver.opera.driver", "src/main/resources/drivers/windows/operadriver.exe");
            }
        } else if (operatingSystem.contains("LINUX")) {
            switch (browser) {
                case "CHROME":
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/linux/chromedriver");
                case "FIREFOX":
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/linux/geckodriver");
                case "OPERA":
                    System.setProperty("webdriver.opera.driver", "src/main/resources/drivers/linux/operadriver");
            }

        } else if (operatingSystem.contains("MAC")) {
            switch (browser) {
                case "CHROME":
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/osx/chromedriver");
                case "FIREFOX":
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/osx/geckodriver");
                case "OPERA":
                    System.setProperty("webdriver.opera.driver", "src/main/resources/drivers/osx/operadriver");
            }
        }
    }
}

