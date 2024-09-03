package com.bewellnesspring.dbapi.service;

import com.bewellnesspring.dbapi.model.repository.FoodCategoryMapper;
import com.bewellnesspring.dbapi.model.repository.SportCategoryMapper;
import com.bewellnesspring.dbapi.model.vo.FoodCategory;
import com.bewellnesspring.dbapi.model.vo.SportCategory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class ApiService {

//    @Autowired
    private final SportCategoryMapper sportCategoryMapper;
//    @Autowired
    private final FoodCategoryMapper foodCategoryMapper;


    public String saveSportApi() {
        Logger logger = (Logger) LoggerFactory.getLogger(ApiService.class);
        int affetedRows = 0;

        String result = "";
        String urlStr = "https://api.odcloud.kr/api/15068730/v1/uddi:12fe14fb-c8ca-47b1-9e53-97a93cb214ed?"
                + "page=1&perPage=260"
                + "&serviceKey=X%2BK%2FqJic5IGtMIij8SqaqTXrqno%2Blr%2FnYPhHzoJlGTbVDhXZKUj8nzkAcVxnnMXCXiaf0Od7VOfOnMfZoPAqkA%3D%3D";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");

            // JSON 데이터 처리
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            Long currentCount = jsonObject.get("currentCount").getAsLong();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            // SportCategory 내에 데이터 삭제
            sportCategoryMapper.truncateSportCategory();

            // 처리된 데이터를 데이터베이스에 저장
            for (int i = 0; i < dataArray.size(); i++) {
                JsonObject tmp = dataArray.get(i).getAsJsonObject();
                SportCategory sportCategory = new SportCategory(
                        null, // id : null로 설정
                        tmp.get("운동명").getAsString(),
                        tmp.get("MET 계수").getAsDouble());
                sportCategoryMapper.insertApitoSportCategory(sportCategory);
                if (i >= 0) {
                    affetedRows++;
                }
            }
            logger.info(dataArray.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
        return "sport_category db저장 완료 ! 저장된 column수 : "+affetedRows;
    }

    public String saveFoodApi() {
        Logger logger = (Logger) LoggerFactory.getLogger(ApiService.class);

        int affetedRows = 0;

        String result = "";

        String urlStr = "https://api.odcloud.kr/api/15050912/v1/uddi:0a633058-9843-40fe-93d0-b568f23b715e_201909261047?"
                + "page=1&perPage=638"
                + "&serviceKey=X%2BK%2FqJic5IGtMIij8SqaqTXrqno%2Blr%2FnYPhHzoJlGTbVDhXZKUj8nzkAcVxnnMXCXiaf0Od7VOfOnMfZoPAqkA%3D%3D";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");

            // JSON 데이터 처리
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            Long currentCount = jsonObject.get("currentCount").getAsLong();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            // FoodCategory 내에 데이터 삭제
            foodCategoryMapper.truncateFoodCategory();

            // 처리된 데이터를 데이터베이스에 저장
            for (int i = 0; i < dataArray.size(); i++) {
                JsonObject tmp = dataArray.get(i).getAsJsonObject();
                FoodCategory foodCategory = new FoodCategory(
                        null, // id : null로 설정
                        tmp.get("음식명").getAsString(),
                        tmp.get("1인분칼로리(kcal)").getAsDouble(),
                        tmp.get("나트륨(g)").getAsDouble(),
                        tmp.get("단백질(g)").getAsDouble(),
                        tmp.get("식이섬유(g)").getAsDouble(),
                        tmp.get("지방(g)").getAsDouble(),
                        tmp.get("콜레스트롤(g)").getAsDouble(),
                        tmp.get("탄수화물(g)").getAsDouble());
                foodCategoryMapper.insertApitoFoodCategory(foodCategory);
                if (i >= 0) {
                    affetedRows++;
                }
            }
            logger.info(dataArray.toString());
        } catch(Exception e){
            e.printStackTrace();
        }
        return "Food_category db저장 완료 ! 저장된 column수 : "+affetedRows;
    }
}
