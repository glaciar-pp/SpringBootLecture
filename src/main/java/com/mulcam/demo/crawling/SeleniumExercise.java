package com.mulcam.demo.crawling;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;	//앞으로 이 기능을 이용해 코딩을 하게될 것임
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumExercise {
	//Driver
	private static WebDriver driver;
	//Properties
	private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	private static final String WEB_DRIVER_PATH = "/DevTools/chromedriver/chromedriver.exe";
	
	public static void main(String[] args) throws Exception {
		//Driver Setup
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		driver = new ChromeDriver(options);
		
		String url = "https://www.nfa.go.kr/nfa/introduce/status/firestationidfo/";
		driver.get(url);
		Thread.sleep(3000);	//3초 지연, 코드가 다 들어갈 수 있게 지연시키는 것!
		
		WebElement inputBox = driver.findElement(By.cssSelector("#searchKeyword")); 
		//한개만 찾을땐 .findElement, 여러개는 -s 붙임
		inputBox.sendKeys("서울");
		WebElement searchBtn = driver.findElement(By.cssSelector("#fsSearchBtn"));
		searchBtn.click();
		Thread.sleep(2000);
		
		// //*[@id="listForm"]/div/section/div/p/strong[2]
		String xpath = "//*[@id=\"listForm\"]/div/section/div/p/strong[2]";	//건수
		String num_ = driver.findElement(By.xpath(xpath)).getText().strip();	//100건
		int num = Integer.parseInt(num_.substring(0, num_.length() - 1));
		int pages = (int) Math.ceil(num / 10.);
		System.out.println(pages);
		
		/*
		// 페이지 이동은 셀레니움, 페이지 내에서 작업은 JSoup 으로 가능함
		Document doc = Jsoup.parse(driver.getPageSource());
		Elements lis = doc.select(".stations-list > li");
		//System.out.println(lis);
		Element li = lis.get(0);
		String name = li.select("title").text().strip();
		String addr = li.select("address").text().strip();
		String tel = li.select(".tel").text().strip();
		
		FireStation fs = new FireStation(name, addr, tel);
		System.out.println(fs);
		
		
		//2 페이지로 이동
		// //*[@id="listForm"]/div/section/ul/li[1]/div/div/ul/li[4]/a
		String page2 = "//*[@id=\"listForm\"]/div/section/ul/li[1]/div/div/ul/li[4]/a";
		driver.findElement(By.xpath(page2)).click();
		Thread.sleep(2000);
		
		Document doc = Jsoup.parse(driver.getPageSource());
		Elements lis = doc.select(".stations-list > li");
		//System.out.println(lis);
		Element li = lis.get(0);
		String name = li.select("title").text().strip();
		String addr = li.select("address").text().strip();
		String tel = li.select(".tel").text().strip();
		
		FireStation fs = new FireStation(name, addr, tel);
		System.out.println(fs);
		
		// next 페이지로 이동
		driver.findElement(By.cssSelector(".next_page")).click();
		Thread.sleep(2000);
		*/
		/* 한꺼번에 모든 소방서 정보를 가져오는 코드 */
		List<FireStation> list = new ArrayList<>();
		for(int page = 1; page <= pages; page++) {
			if(page > 1 && page % 2 == 0) {
				driver.findElement(By.xpath("//*[@id=\"listForm\"]/div/section/ul/li[1]/div/div/ul/li[4]/a")).click();
				Thread.sleep(1000);
			}
			if(page > 1 && page % 2 == 1) {
				driver.findElement(By.cssSelector(".next_page")).click();
				Thread.sleep(1000);
				}
			Document doc = Jsoup.parse(driver.getPageSource());
			Elements lis = doc.select(".stations-list > li");
				for (Element li : lis) {
					String name = li.select(".title").text().strip();
					String addr = li.select("address").text().strip();
					String tel = li.select(".tel").text().strip();
					FireStation fs = new FireStation(name, addr, tel);
					list.add(fs);
				}
			}
			System.out.println(list.size());
			driver.close();
		
	}

}
// 셀레니움은 특성상 잘 안돌아가면 보통은 타이밍 문제가 있다고 한다.