package com.bewellnesspring.dbapi.controller;

import com.bewellnesspring.dbapi.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiController {

//  @Autowired
  private final ApiService apiService;

    @RequestMapping("/sportApi")
    public String saveSportApi() {
        return apiService.saveSportApi();
    }

    @RequestMapping("/foodApi")
   public String saveFoodApi() {
        return apiService.saveFoodApi();
    }

}

