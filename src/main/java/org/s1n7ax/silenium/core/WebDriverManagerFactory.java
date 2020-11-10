package org.s1n7ax.silenium.core;

/**
 * WebDriverFactory
 */
public interface WebDriverManagerFactory {

	WebDriverManager getManager(String driverType);

}