package tests;

import org.s1n7ax.silenium.core.WebAutomationBase;
import org.s1n7ax.silenium.core.annotations.TestMeta;
import org.testng.annotations.Test;

import pages.GooglePage;

@TestMeta(browser = "chrome", baseURL = "https://www.google.com/", pageloadTimeout = 30000, implicitTimeout = 10000, scriptTimeout = 120000)
public class GoogleSearchTests extends WebAutomationBase {

	@Test
	public void varifyBasicSearch() {

		final var driver = getDriver();
		final var googleSearch = new GooglePage(driver);

		googleSearch.search("nvidia RTX 3090");
		googleSearch.validateSearchResult("GeForce RTX 3090 Graphics Card | NVIDIA");

	}

}
