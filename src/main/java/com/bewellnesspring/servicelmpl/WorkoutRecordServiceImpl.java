package com.bewellnesspring.servicelmpl;

import com.bewellnesspring.dbapi.SportCategory;
import com.bewellnesspring.dbapi.SportCategoryMapper;
import com.bewellnesspring.domain.WorkoutRecord;
import com.bewellnesspring.mappers.WorkoutRecordMapper;
import com.bewellnesspring.service.WorkoutRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutRecordServiceImpl implements WorkoutRecordService {

    private final WorkoutRecordMapper workoutRecordMapper;  // WorkoutRecordMapper 주입
    private final SportCategoryMapper sportCategoryMapper;  // SportCategoryMapper 주입 (운동 종목 정보를 가져오기 위함)

    // 운동 기록 생성
    @Override
    public void createWorkoutRecord(WorkoutRecord workoutRecord) {
        // 운동 종목의 MET 계수를 사용하여 소모 칼로리를 계산할 수 있음
        SportCategory sportCategory = sportCategoryMapper.selectSportCategoryById(Long.valueOf(workoutRecord.getSportCategoryId()));
        double caloriesBurned = workoutRecord.getDuration() * sportCategory.getKcal(); // MET 계수 * 운동 시간 = 소모 칼로리
        workoutRecord.setCaloriesBurned(caloriesBurned);

        // DB에 운동 기록 삽입
        workoutRecordMapper.insertWorkoutRecord(workoutRecord);
    }

    // ID로 운동 기록 조회
    @Override
    public WorkoutRecord getWorkoutRecord(Long id) {
        return workoutRecordMapper.selectWorkoutRecordById(id);
    }

    // 모든 운동 기록 조회
    @Override
    public List<WorkoutRecord> getAllWorkoutRecords() {
        return workoutRecordMapper.selectAllWorkoutRecords();
    }

    // 운동 기록 업데이트
    @Override
    public void updateWorkoutRecord(WorkoutRecord workoutRecord) {
        // 운동 기록 업데이트 시에도 소모 칼로리 재계산 가능
        SportCategory sportCategory = sportCategoryMapper.selectSportCategoryById(Long.valueOf(workoutRecord.getSportCategoryId()));
        double caloriesBurned = workoutRecord.getDuration() * sportCategory.getKcal();
        workoutRecord.setCaloriesBurned(caloriesBurned);

        // DB에 운동 기록 업데이트
        workoutRecordMapper.updateWorkoutRecord(workoutRecord);
    }

    // ID로 운동 기록 삭제
    @Override
    public void deleteWorkoutRecord(Long id) {
        workoutRecordMapper.deleteWorkoutRecord(id);
    }

    // 모든 운동 종목 조회
    @Override
    public List<SportCategory> getAllSportCategories() {
        return sportCategoryMapper.selectAllSportCategories();
    }
}
