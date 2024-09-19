package com.bewellnesspring.sport.controller;

import com.bewellnesspring.certification.model.vo.User;
import com.bewellnesspring.sport.Service.SportPlanService;
import com.bewellnesspring.sport.Service.SportRecordService;
import com.bewellnesspring.sport.model.repository.SportPlanMapper;
import com.bewellnesspring.sport.model.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sport")
public class SportController {

    private final SportPlanService sportPlanService;
    private final SportRecordService sportRecordService;
    private final SportPlanMapper sportPlanMapper;


    @PostMapping("/save/plan")
    private ResponseEntity<?> saveSportPlan(@RequestBody SportDTO sportDTO) {

        sportPlanService.planSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 저장완료");
    }

    @PostMapping("/save/record")
    private ResponseEntity<?> saveSportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.recordSave(sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 저장완료");
    }

    @PostMapping("/modify/plan")
    private ResponseEntity<?> modifySportPlan(@RequestBody SportDTO sportDTO) {
        System.out.println("sportDTO = " + sportDTO);
        System.out.println("sportDTO.getItems() = " + sportDTO.getItems());

        sportPlanService.modifyPlan(sportDTO.getId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 수정완료");
    }
    @PostMapping("/modify/record")
    private ResponseEntity<?> modifySportRecord(@RequestBody SportDTO sportDTO) {

        sportRecordService.modifyRecord(sportDTO.getSportRecordId(), sportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 수정완료");
    }

    @DeleteMapping("/delete/plan/{id}")
    private ResponseEntity<?> deleteSportPlan(@PathVariable Long id) {

        System.out.println("삭제된 계획id = " + id);

        sportPlanService.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK).body("운동계획 삭제완료");
    }

    @DeleteMapping("/delete/record")
    private ResponseEntity<?> deleteSportRecord(@PathVariable Long id) {

        sportRecordService.deleteRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body("운동기록 삭제완료");
    }

    @PostMapping("/save/succes")
    private ResponseEntity<?> saveSuccess(@RequestBody SportDTO sportDTO) {
        sportRecordService.successPlan(sportDTO.getSportPlanId());
        return ResponseEntity.status(HttpStatus.OK).body("계획실행 후 기록에 저장");
    }

    @GetMapping("/plan/view")
    public ResponseEntity<?> getSportPlan(HttpSession session) {
//        // 세션에서 사용자 정보 가져오기
//        User user = (User) session.getAttribute("user");  // 세션에 저장된 사용자 정보
//
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
//        }

        User user = new User();
        user.setUserId("yoohwanjoo@nate.com");


        // 사용자 ID를 기반으로 운동 계획 조회
//        List<SportPlanDTO> sportPlans = sportPlanMapper.findAllByUserId(user.getUserId());

        //운동의 이름까지 가져오기
        List<SportPlanDTO> sportPlans = sportPlanMapper.findSportPlanWithName(user.getUserId());

        if (sportPlans != null && !sportPlans.isEmpty()) {
            return ResponseEntity.ok(sportPlans);
        } else {
            return ResponseEntity.badRequest().body("운동 계획이 없습니다.");
        }
    }

    @GetMapping("/plan/{id}")
    private ResponseEntity<SportPlanDTO> getSportPlanById(@PathVariable Long id) {
        SportPlanDTO sportPlan = sportPlanService.getSportPlanById(id);
        List<SportItemDTO> sportItems = sportPlanService.getSportItemById(id); // 운동 항목을 ID로 조회

        if(sportPlan != null ) {
            sportPlan.setItems(sportItems); // 운동 계획에 항목 리스트 추가
            return ResponseEntity.ok(sportPlan);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/plan/range")
    private ResponseEntity<List<SportPlanDTO>> getSportPlanRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                 @RequestParam("userId") String userId) {
        List<SportPlanDTO> list = sportPlanService.getSportPlanByRange(startDate,endDate,userId);
        if(list !=null) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @GetMapping("/auth/userinfo")
//    public ResponseEntity<?> getUserInfo(HttpSession session) {
//        // 세션에서 userId 가져오기
//        String userId = (String) session.getAttribute("userId");
//
//        if (userId != null) {
//            return ResponseEntity.ok(userId);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
//        }
//    }


}
