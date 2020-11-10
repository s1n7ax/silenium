package tests;

import org.s1n7ax.silenium.core.WebAutomationBase;
import org.testng.annotations.Test;

import pages.GooglePage;

public class GoogleSearchTests extends WebAutomationBase {

	@Test
	public void varifyBasicSearch() {

		final var driver = getDriver();
		final var googleSearch = new GooglePage(driver);

		driver.get("https://www.google.com/");
		googleSearch.search("nvidia RTX 3090");
		googleSearch.validateSearchResult("GeForce RTX 3090 Graphics Card | NVIDIA");

	}
}
