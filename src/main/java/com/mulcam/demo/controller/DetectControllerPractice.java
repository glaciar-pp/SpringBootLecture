//package com.mulcam.demo.controller;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.mulcam.demo.entity.FileEntity;
//
//@Controller
//@RequestMapping("/detect2")
//public class DetectControllerPractice {
////반쪽짜리 답,..필요한건 얼추 들어갔지만 객체 탐지 결과가 안나옴
//	@Value("${naver.accessId}") private String accessId;
//	@Value("${naver.secretKey}") private String secretKey;
//	
//	@GetMapping("/naverForm2")
//	public String uploadForm() {
//		return "detect2/naverForm2";
//	}
//	
//	@PostMapping("/naverForm2") 
//	public String upload(@RequestParam MultipartFile[] files, Model model) {
//		List<FileEntity> list = new ArrayList<>();
//		for (MultipartFile file: files) {
//			FileEntity fe = new FileEntity();
//			fe.setFileName(file.getOriginalFilename());
//			fe.setContentType(file.getContentType());
//			list.add(fe);
//			
//			// 물리적 저장
//			File fileName = new File(file.getOriginalFilename());
//			try {
//				file.transferTo(fileName);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		model.addAttribute("uploadFiles2", list);
//		return "detect2/naverForm2";
//	}
//	
//	@Value("${spring.servlet.multipart.location}")
//	String uploadDir;
//	
//	@GetMapping("/naver2")
//	public String naver(Model model, FileEntity fe) throws Exception {
//		String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
//		
//		URL url = new URL(apiURL);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setUseCaches(false);
//		conn.setDoOutput(true);
//		conn.setDoInput(true);
//		conn.setRequestMethod("POST");		//우리가 사진을 보내는거라 POST인건데 이 부분은 생략 가능!
//		
//		
//		// multipart request
//		String boundary = "---" + System.currentTimeMillis() + "---";
//		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//		conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
//		conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
//		
//		//파일 전송 준비
//		OutputStream os = conn.getOutputStream();
//		PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
//		String LF = "\n";		//line feed
//		File uploadFile = new File(uploadDir + "/");
//		String fileName = uploadFile.getName();
//		out.append("--" + boundary).append(LF);
//		out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LF);
//		out.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LF);
//		out.append(LF);
//		out.flush();
//		
//		//실제 파일을 읽어서 전송
//		FileInputStream fis = new FileInputStream(uploadFile);
//		byte[] buffer = new byte[4096];
//		int bytesRead = -1;	//다 읽으면 -1이 나오는 내용 설정
//		while ((bytesRead = fis.read(buffer)) != -1)
//			os.write(buffer, 0, bytesRead);		//buffer의 처음부터 읽은 데이터 수
//		fis.close();
//		os.flush();
//		out.append(LF);
//		out.append("--" + boundary + "--").append(LF).flush(); //요걸 안넣어주면 끝을 몰라서 헤매게 됨..!
//		out.close();
//		
//		//응답 결과 확인
//		int responseCode = conn.getResponseCode();
//		if (responseCode != 200)		//200 이 정상코드니까 안나오면 에러
//			System.out.println("error!!!!!!! responseCode= " + responseCode);
//		//결과 확인
//		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//		StringBuffer sb = new StringBuffer(); //여럿이서 쓸땐 버퍼, 혼자쓰면 빌더
//		String line;
//		while ((line = br.readLine()) != null)
//			sb.append(line);
//		br.close();
//		
//		model.addAttribute("fileName", fileName);
//		model.addAttribute("jsonResult", sb.toString());
//		
//		return "detect2/naverResult2";
//		
//		
//	}
//	
//}
//
//
//
//
//
//
