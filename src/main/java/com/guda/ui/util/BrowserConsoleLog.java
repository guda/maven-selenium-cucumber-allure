package com.guda.ui.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.Date;

public class BrowserConsoleLog {
    public static ArrayList<String> consoleAllLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        ArrayList<String> consoleLogs = new ArrayList<>();

        logEntries.forEach(entry -> consoleLogs.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage()));

        return consoleLogs;
    }

    public static ArrayList<String> consoleSevereLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        ArrayList<String> consoleErrors = new ArrayList<>();

        for (LogEntry entry : logEntries) {
            if (entry.getLevel().toString().equalsIgnoreCase("SEVERE")) {
                consoleErrors.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            }
        }
        return consoleErrors;
    }
}
