package org.s1n7ax.silenium.core.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.s1n7ax.silenium.core.NoSuchDriverException;
import org.s1n7ax.silenium.core.WebDriverFactory;

/**
 * DefaultWebDriverFactory
 */
public class DefaultWebDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver getDriver(final String driverType) {
		switch (driverType) {
		case "chrome": {
			return new ChromeDriver();
		}

		case "firefox": {
			return new FirefoxDriver();
		}

		default:
			throw new NoSuchDriverException("web driver not found for driver type :: " + driverType);
		}
	}

}