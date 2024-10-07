package frontend;

import java.io.IOException;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import utility.MyKeys;

public class GetTheCode {

	public static Browser browser;

	public static Page getPage() {

		Playwright playwright = Playwright.create();
		browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
		BrowserContext context = browser.newContext();
		Page page = context.newPage();
		return page;
	}

	public static String getCode(String url) throws IOException, InterruptedException {

		Page page = getPage();

		page.navigate(url);
		page.fill("input[id='login-username']", MyKeys.username);
		page.fill("input[id='login-password']", MyKeys.password);
		page.click(".ButtonInner-sc-14ud5tc-0");
		page.waitForSelector("//textarea[@id='APjFqb']");

		String currentUrl = page.url();

		browser.close();
		return parseCode(currentUrl);

	}

	public static String parseCode(String url) {

		System.out.println(url);
		String[] code = url.split("=");
		return code[1];
	}

}
