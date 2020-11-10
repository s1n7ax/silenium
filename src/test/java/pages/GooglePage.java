package pages;

import static org.testng.Assert.assertTrue;
import static util.Locator.resolveByX;
import static util.Locator.resolveStr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import util.PageUtil;

/**
 * GooglePage
 */
public class GooglePage implements PageUtil {
	private final WebDriver driver;

	@FindBy(name = "q")
	public WebElement txtSearch;

	@FindBy(xpath = "//*[@value='Google Search']")
	public WebElement btnSearch;

	public String lnkCommonSearchResult = "//a/h3/span[.='%s']";

	public GooglePage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void search(final String value) {

		txtSearch.sendKeys(value);

		getWait(driver).until(driver -> {
			btnSearch.click();
			return true;
		});
	}

	public void validateSearchResult(final String expectedLink) {

		final var links = driver.findElements(resolveByX(lnkCommonSearchResult, expectedLink));
		assertTrue(links.size() > 0, resolveStr("search results [%s] not found", expectedLink));

	}
}
