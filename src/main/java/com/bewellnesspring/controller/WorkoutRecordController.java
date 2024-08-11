package com.bewellnesspring.controller;



import com.bewellnesspring.domain.WorkoutRecord;
import com.bewellnesspring.service.WorkoutRecordService;  // Service 인터페이스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workout")
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    // 운동 기록 생성
    @PostMapping("/write")
    public ResponseEntity<String> createWorkoutRecord(@RequestBody WorkoutRecord workoutRecord) {
        workoutRecordService.createWorkoutRecord(workoutRecord);
        return ResponseEntity.ok("Workout record created successfully");
    }

    // ID로 운동 기록 조회
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRecord> getWorkoutRecord(@PathVariable Long id) {
        WorkoutRecord record = workoutRecordService.getWorkoutRecord(id);
        return ResponseEntity.ok(record);
    }

    // 모든 운동 기록 조회
    @GetMapping("/list")
    public ResponseEntity<List<WorkoutRecord>> getAllWorkoutRecords() {
        List<WorkoutRecord> records = workoutRecordService.getAllWorkoutRecords();
        return ResponseEntity.ok(records);
    }

    // 운동 기록 수정
    @PostMapping("/modify/{id}")
    public ResponseEntity<String> updateWorkoutRecord(@RequestBody WorkoutRecord workoutRecord) {
        workoutRecordService.updateWorkoutRecord(workoutRecord);
        return ResponseEntity.ok("Workout record updated successfully");
    }

    // ID로 운동 기록 삭제
     @PostMapping("/remove/{id}")
    public ResponseEntity<String> deleteWorkoutRecord(@PathVariable Long id) {
        workoutRecordService.deleteWorkoutRecord(id);
        return ResponseEntity.ok("Workout record deleted successfully");
    }
}
