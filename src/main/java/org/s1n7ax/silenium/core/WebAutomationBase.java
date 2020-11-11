package org.s1n7ax.silenium.core;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.s1n7ax.silenium.core.annotations.TestMeta;
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

	@Parameters({ "browser", "base.url", "timeout.implicit", "silenium.timeout.pageload", "silenium.timeout.script" })
	@BeforeMethod()
	protected synchronized final void defaultBeforeTest1(@Optional final String browser, @Optional final String baseURL,
			@Optional("0") final String implicitTimeout, @Optional("0") final String pageloadTimeout,
			@Optional("0") final String scriptTimeout, final Method method, final ITestContext context) {

		context.setAttribute("silenium.browser", browser);
		context.setAttribute("silenium.base.url", baseURL);

		var pageloadTimeoutLocal = Long.parseLong(pageloadTimeout);
		var implicitTimeoutLocal = Long.parseLong(implicitTimeout);
		var scriptTimeoutLocal = Long.parseLong(scriptTimeout);

		context.setAttribute("silenium.timeout.pageload", pageloadTimeoutLocal);
		context.setAttribute("silenium.timeout.implicit", implicitTimeoutLocal);
		context.setAttribute("silenium.timeout.script", scriptTimeoutLocal);

		var testMeta = method.getDeclaringClass().getAnnotation(TestMeta.class);

		if (browser == null)
			context.setAttribute("silenium.browser", testMeta.browser());

		if (baseURL == null)
			context.setAttribute("silenium.base.url", testMeta.baseURL());

		if (implicitTimeoutLocal < 1)
			context.setAttribute("silenium.timeout.implicit", testMeta.implicitTimeout());

		if (pageloadTimeoutLocal < 1)
			context.setAttribute("silenium.timeout.pageload", testMeta.pageloadTimeout());

		if (scriptTimeoutLocal < 1)
			context.setAttribute("silenium.timeout.script", testMeta.scriptTimeout());
	}

	@BeforeMethod()
	protected synchronized final void defaultBeforeTest2(final ITestContext context) {

		final var browser = (String) context.getAttribute("silenium.browser");
		final var baseURL = (String) context.getAttribute("silenium.base.url");
		final var implicitTimeout = (long) context.getAttribute("silenium.timeout.implicit");
		final var pageloadTimeout = (long) context.getAttribute("silenium.timeout.pageload");
		final var scriptTimeout = (long) context.getAttribute("silenium.timeout.script");

		final var driver = getWebDriver(browser);

		if (baseURL != null)
			driver.get(baseURL);

		if (implicitTimeout > 0)
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.MILLISECONDS);

		if (pageloadTimeout > 0)
			driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.MILLISECONDS);

		if (scriptTimeout > 0)
			driver.manage().timeouts().implicitlyWait(scriptTimeout, TimeUnit.MILLISECONDS);

		setDriver(driver);

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
