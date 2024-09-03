package com.bewellnesspring.alert.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final JavaMailSender mailSender;
    private final EmailService emailService;

//    private final String API_KEY = "06a2b14f9f934e9abb71f087d481fe32";
    @Value("${news.api.key}")
    private String API_KEY;    //건강 키워드, 한국에서, 오늘기준, 가장 인기많은 뉴스 로 필터링
    private final String TODAY = LocalDate.now().toString();
    private final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=건강&language=ko&from="
            + LocalDate.now().minusDays(1).toString() + "&to=" + TODAY + "&sortBy=popularity&apiKey=" + API_KEY;

    // 00월 00주차 라는 제목을 작성하기 위한 부분
    private final LocalDate date = LocalDate.now();
    private final int month = date.getMonthValue();
    private final WeekFields weekFields = WeekFields.of(Locale.KOREA.getDefault());
    private final int weekOfMonth = date.get(weekFields.weekOfMonth());


    private final RestTemplate restTemplate;

    private String buildNewsApiUrl() {
        return "https://newsapi.org/v2/everything?q=건강&language=ko&from="
                + LocalDate.now().minusDays(1).toString() + "&to=" + TODAY + "&sortBy=popularity&apiKey=" + API_KEY;
    }

    public void sendNewsEmail(String to) throws MessagingException, IOException {

        //yml에서 가져온 API 값이 null로 나와서 필드에서 초기화 하지 않고 동적으로 생성하도록 함
        String newsApiUrl = buildNewsApiUrl();

        //뉴스가져오기
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(newsApiUrl, String.class);

        // JSON 파싱
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode articles = root.path("articles");


        if (articles.isArray() && articles.size() > 0) {
            // 첫 번째 뉴스를 선택
            JsonNode article = articles.get(0);

            // HTML 내용 만들기
            String title = article.path("title").asText();
            String author = article.path("author").asText();
            String url = article.path("url").asText();
            String publishedAt = article.path("publishedAt").asText();

            String htmlContent = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">" // 문자 인코딩 설정
                    + "</head>"
                    + "<body>"
                    + "<h1>" + title + "</h1>"
                    + "<p><strong>Author:</strong> " + author + "</p>"
                    + "<p><strong>Published At:</strong> " + publishedAt + "</p>"
                    + "<p><a href=\"" + url + "\">Read more</a></p>"
                    + "</body>"
                    + "</html>";

            // 이메일 전송
            System.out.println("to = " + to);
            emailService.sendHtmlMessage(to, month + "월 " + weekOfMonth + "주차 건강뉴스레터 입니다.", htmlContent);
        }
    }


}
