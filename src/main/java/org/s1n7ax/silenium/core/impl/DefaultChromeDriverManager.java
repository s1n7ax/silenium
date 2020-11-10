package org.s1n7ax.silenium.core.impl;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.s1n7ax.silenium.core.NoSuchDriverException;
import org.s1n7ax.silenium.core.WebDriverManager;

/**
 * DefaultChromeDriverManager
 */
public class DefaultChromeDriverManager implements WebDriverManager {

	private ChromeDriverService service;

	@Override
	public void startService() {

		if (service == null)
			service = new ChromeDriverService.Builder().usingDriverExecutable(getDriverFile()).usingAnyFreePort()
					.build();

		if (service.isRunning())
			return;

		try {
			service.start();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stopService() {
		if (service != null && !service.isRunning())
			service.stop();
	}

	private File getDriverFile() {

		final var os = System.getProperty("os.name").toLowerCase();
		final var classloader = Thread.currentThread().getContextClassLoader();

		String filePath;

		switch (os) {
		case "linux": {
			filePath = "drivers/linux/chromedriver";
		}
			break;
		case "windows": {
			filePath = "drivers/windows/chromedriver.exe";
		}
			break;
		default:
			throw new NoSuchDriverException("web driver is not available for current os :: " + os);
		}

		try {
			return new File(classloader.getResource(filePath).getFile());
		} catch (final NullPointerException e) {
			e.printStackTrace();
			throw new NoSuchDriverException("web driver is not available in path :: " + filePath);
		}
	}

}
