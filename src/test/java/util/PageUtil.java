package util;

import java.time.Duration;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

/**
 * PageUtil
 */
public interface PageUtil {

	default FluentWait<WebDriver> getWait(final WebDriver driver) {
		return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(60)).pollingEvery(Duration.ofMillis(300))
				.ignoring(ElementNotInteractableException.class);
	}
}
