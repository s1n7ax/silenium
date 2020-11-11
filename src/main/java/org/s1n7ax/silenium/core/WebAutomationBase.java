package org.s1n7ax.silenium.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;
import org.s1n7ax.silenium.core.impl.DefaultDriverManagerFactory;
import org.s1n7ax.silenium.core.impl.DefaultWebDriverFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class WebAutomationBase implements WebDriverManagerFactoryConfigurer, WebDriverFactoryConfigurer {

	private final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	private final Map<String, WebDriverManager> driverManagerMap = new ConcurrentHashMap<>();

	protected final WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	private void setDriver(final WebDriver driver) {
		threadLocalDriver.set(driver);
	}

	@Override
	public WebDriverManagerFactory getWebDriverManagerFactory() {
		return new DefaultDriverManagerFactory();
	}

	@Override
	public WebDriverFactory getWebDriverFactory() {
		return new DefaultWebDriverFactory();
	}

	@Parameters({ "browser", "base.url", "timeout.implicit" })
	@BeforeSuite()
	protected synchronized final void defaultBeforeSuite(@Optional("chrome") final String browser,
			@Optional() final String baseURL, @Optional("10000") final String implicitTimeout,
			final ITestContext context) {

		context.setAttribute("silenium.browser", browser);
		context.setAttribute("silenium.base.url", baseURL);
		context.setAttribute("silenium.timeout.implicit", Long.parseLong(implicitTimeout));

	}

	@Parameters({ "browser", "base.url", "timeout.implicit" })
	@BeforeMethod()
	protected synchronized final void defaultBeforeTest(@Optional("firefox") final String browser,
			@Optional() final String baseURL, @Optional("10000") final String implicitTimeout,
			final ITestContext context) {

		context.setAttribute("silenium.browser", browser);
		context.setAttribute("silenium.base.url", baseURL);
		context.setAttribute("silenium.timeout.implicit", Long.parseLong(implicitTimeout));

		setDriver(getWebDriver(browser));

		if (baseURL != null)
			getDriver().get(baseURL);

	}

	@AfterMethod
	protected final void defaultAfterTest() {
		getDriver().quit();
	}

	@AfterSuite
	protected final void defaultAfterSuite() {
		driverManagerMap.forEach((key, driverManager) -> {
			driverManager.stopService();
		});
	}

	private synchronized final WebDriver getWebDriver(final String browser) {

		final var driverManager = getWebDriverManagerFactory().getManager(browser);

		driverManager.startService();
		driverManagerMap.put(browser, driverManager);

		return getWebDriverFactory().getDriver(browser);
	}

}
