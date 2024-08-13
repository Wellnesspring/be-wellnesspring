package com.bewellnesspring.dbapi;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class ApiContorller {

    @Autowired
    private SportCategoryMapper sportCategoryMapper;

    @Autowired
    private FoodCategoryMapper foodCategoryMapper;


    @RequestMapping("/sportApi")
    public String saveSportApi() throws IOException {

        int affetedRows = 0;

        String result = "";
        String urlStr = "https://api.odcloud.kr/api/15068730/v1/uddi:12fe14fb-c8ca-47b1-9e53-97a93cb214ed?"
                + "page=1&perPage=260"
                + "&serviceKey=X%2BK%2FqJic5IGtMIij8SqaqTXrqno%2Blr%2FnYPhHzoJlGTbVDhXZKUj8nzkAcVxnnMXCXiaf0Od7VOfOnMfZoPAqkA%3D%3D";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            bf.close();

            result = sb.toString();

            // JSON 데이터 처리
            JSONObject jsonObject = new JSONObject(result);
            Long currentCount = jsonObject.getLong("currentCount");
            JSONArray dataArray = jsonObject.getJSONArray("data");

            // SportCategory 내에 데이터 삭제
            sportCategoryMapper.truncateSportCategory();

            // 처리된 데이터를 데이터베이스에 저장
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject tmp = dataArray.getJSONObject(i);
                SportCategory sportCategory = new SportCategory(
                        null, // id : null로 설정
                        tmp.getString("운동명"),
                        tmp.getDouble("MET 계수"));
                sportCategoryMapper.insertApitoSportCategory(sportCategory);
                if (i >= 0) {
                    affetedRows++;
                }
            }
            } catch(Exception e){
                e.printStackTrace();
            }
            return "sport_category db저장 완료 ! 저장된 column수 : "+affetedRows;
        }

    @RequestMapping("/foodApi")
    public String saveFoodApi() throws IOException {

        int affetedRows = 0;

        String result = "";

        String urlStr = "https://api.odcloud.kr/api/15050912/v1/uddi:0a633058-9843-40fe-93d0-b568f23b715e_201909261047?"
                + "page=1&perPage=638"
                + "&serviceKey=X%2BK%2FqJic5IGtMIij8SqaqTXrqno%2Blr%2FnYPhHzoJlGTbVDhXZKUj8nzkAcVxnnMXCXiaf0Od7VOfOnMfZoPAqkA%3D%3D";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            bf.close();

            result = sb.toString();

            // JSON 데이터 처리
            JSONObject jsonObject = new JSONObject(result);
            Long currentCount = jsonObject.getLong("currentCount");
            JSONArray dataArray = jsonObject.getJSONArray("data");

            // FoodCategory 내에 데이터 삭제
            foodCategoryMapper.truncateFoodCategory();

            // 처리된 데이터를 데이터베이스에 저장
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject tmp = dataArray.getJSONObject(i);
                FoodCategory foodCategory = new FoodCategory(
                        null, // id : null로 설정
                        tmp.getString("음식명"),
                        tmp.getDouble("1인분칼로리(kcal)"),
                        tmp.getDouble("나트륨(g)"),
                        tmp.getDouble("단백질(g)"),
                        tmp.getDouble("식이섬유(g)"),
                        tmp.getDouble("지방(g)"),
                        tmp.getDouble("콜레스트롤(g)"),
                        tmp.getDouble("탄수화물(g)"));
                foodCategoryMapper.insertApitoFoodCategory(foodCategory);
                if (i >= 0) {
                    affetedRows++;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return "Food_category db저장 완료 ! 저장된 column수 : "+affetedRows;
    }
    }

