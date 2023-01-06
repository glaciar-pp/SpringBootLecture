package com.mulcam.demo.crawling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InterparkExercise {

	public static void main(String[] args) throws IOException {
		//인터파크 도서 크롤링하기
		String url = "http://book.interpark.com/display/collectlist.do?_method=BestsellerHourNew201605&bestTp=1&dispNo=028#";
		//site에 접속해서 html 데이터를 가져온 후 Parsing
		Document doc = Jsoup.connect(url).get();
		// 찾고자 하는 항목들
		Elements lis = doc.select(".rankBestContentList >ol > li"); //복수 형태라기 보단 리스트 모음집
		
/**		아래의 내용은 각각 항목 미리 한번 해보고 반복시에는 커멘트 처리
 * 
		System.out.println(lis.size());
		Element li = lis.get(12);	//책 순서...0부터
		System.out.println(li.toString());
		String title = li.select(".itemName").text().strip();
		String author = li.select(".author").text().strip();
		String company = li.select(".company").text().strip();
		System.out.println(title + ", " + author + ", " + company);
		
		//이미지 주소 알아내기
//		Element img = li.selectFirst(".coverImage").selectFirst("img");
//		String src = img.attr("src");
		String src = li.select(".coverImage img").attr("src");	//자손 selector
		System.out.println(src);
		//스트링 조작
		String price_ = li.select(".price > em").text().strip();
		int price = Integer.parseInt(price_.replace(",", ""));
		System.out.println(price_);
		
		// 순위
		Elements spans = li.select(".rankNumber.digit2").select("span");
		String rank_ = "";
		for(Element span: spans) {
			String classes = span.attr("class").strip();
			//System.out.println(classes);
			rank_ += classes.substring(classes.length() - 1);
		}
		//지정한 책의 순위와 동일한 숫자를 구할 수 있음
		int rank = Integer.parseInt(rank_);
		System.out.println(rank_);	
		
		//데이터 정리
		Interpark book = new Interpark(rank, src, title, author, company, price);
		System.out.println(book);
		**/
		//반목문으로 데이터 정리
		List<Interpark> list = new ArrayList<>();
		for (Element li: lis) {
			Elements spans = li.select(".rankNumber.digit2").select("span");
			String rank_ = "";
			for (Element span: spans) {
				String classes = span.attr("class").strip();
				rank_ += classes.substring(classes.length() - 1);
			}
			int rank = Integer.parseInt(rank_);
			String src = li.select(".coverImage img").attr("src");
			String title = li.select(".itemName").text().strip();
			String author = li.select(".author").text().strip();
			String company = li.select(".company").text().strip();
			String price_ = li.select(".price > em").text().strip();
			int price = Integer.parseInt(price_.replace(",", ""));
			Interpark book = new Interpark(rank, src, title, author, company, price);
			list.add(book);
		}
		list.forEach(x -> System.out.println(x));
	}

}
