package util;

import org.openqa.selenium.By;

/**
 * Locator
 */
public class Locator {

	public static String resolveStr(final String locator, final Object... args) {
		return String.format(locator, args);
	}

	public static By resolveByX(final String locator, final Object... args) {
		return By.xpath(resolveStr(locator, args));
	}
}
