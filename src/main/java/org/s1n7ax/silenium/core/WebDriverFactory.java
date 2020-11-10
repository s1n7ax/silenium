package org.s1n7ax.silenium.core;

import org.openqa.selenium.WebDriver;

/**
 * WebDriverFactory
 */
public interface WebDriverFactory {

	WebDriver getDriver(String browser);

}