package org.s1n7ax.silenium.core.impl;

import org.s1n7ax.silenium.core.NoSuchDriverManagerException;
import org.s1n7ax.silenium.core.WebDriverManager;
import org.s1n7ax.silenium.core.WebDriverManagerFactory;

/**
 * DefaultDriverManagerFactory
 */
public class DefaultDriverManagerFactory implements WebDriverManagerFactory {

	@Override
	public WebDriverManager getManager(final String driverType) {

		switch (driverType) {
		case "chrome": {
			return new DefaultChromeDriverManager();
		}

		case "firefox": {
			return new DefaultGeckoDriverManager();
		}

		default: {
			throw new NoSuchDriverManagerException("driver manager not found for driver type :: " + driverType);
		}
		}
	}

}
