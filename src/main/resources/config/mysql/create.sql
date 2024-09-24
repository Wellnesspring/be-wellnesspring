-- auto-generated definition
create user wellnessdb@localhost;

create database wellness;

use wellness;

DROP TABLE if exists social;
DROP TABLE if exists Food_Category;
DROP TABLE if exists Food_Like;
DROP TABLE if exists Meal;
DROP TABLE if exists Meal_detail;
DROP TABLE if exists Sport_Category;
DROP TABLE if exists Sport_Like;
DROP TABLE if exists Sport_Routine;
DROP TABLE if exists Sport_Routine_List;
DROP TABLE if exists Weight_Record;
DROP TABLE if exists Alert;
DROP TABLE if exists Alert_type;
DROP TABLE if exists users;
DROP TABLE if exists PLAN;
DROP TABLE if exists sport_plan;
DROP TABLE if exists sport_plan_item;
DROP TABLE if exists sport_record;
DROP TABLE if exists sport_record_item;

CREATE TABLE users(
        user_id 	VARCHAR(50) primary key	NOT NULL,
        role 	int	DEFAULT 0	COMMENT '0:회원 , 1:관리자',
        user_pw 	varchar(100) NOT NULL,
        name 	varchar(100) NOT NULL,
        serial_num_f 	varchar(100) NOT NULL	COMMENT '성별값까지 7자리 저장',
        serial_num_l 	varchar(100),
        phone 	varchar(100) NOT NULL,
        height 	varchar(100),
        weight 	varchar(100),
        join_at 	timestamp DEFAULT now() COMMENT '회원 가입일자', #now() : insert 시점의 날짜 데이터 삽입 (초까지)
        deleteAt	timestamp,
        alarm_agree 	varchar(10) COMMENT '동의,거부',
        profileImg 	varchar(100),
        point 	int DEFAULT 0,
        locker varchar(100) NOT NULL
);

CREATE TABLE Social(
    platform VARCHAR(20) NOT NULL COMMENT '소셜 플랫폼',
    user_id VARCHAR(50) NOT NULL,
    id_num varchar(20) NOT NULL COMMENT '해당플랫폼에서의 식별값',
    primary key (user_id, platform),
 	foreign key (user_id) references users(user_id)
);

CREATE TABLE  Food_Category  (
         id 	int auto_increment primary key	NOT NULL,
         food_name 	varchar(255),
         kcal 	float,
         na 	float,
         protein float,
         fiber float,
         fat float,
         cholesterol float,
         carbohydrate float
);

CREATE TABLE  Food_Like  (
     id 	int auto_increment primary key	NOT NULL,
     food_id 	int	COMMENT 'Food_Category 테이블의 id',
     liked_at 	timestamp DEFAULT now()
);

CREATE TABLE  Meal  (
        meal_id 	int auto_increment primary key	NOT NULL,
        meal_time 	timestamp	DEFAULT now()
);

CREATE TABLE  Meal_detail  (
       id 	int auto_increment primary key	NOT NULL,
       meal_id 	int	COMMENT 'Meal 테이블의 id',
       food_id 	int	COMMENT 'Food_Category 테이블의 id',
       meal 	varchar(50)	,
       amount 	float COMMENT '갯수,gram 등으로 표현'
);

CREATE TABLE  Sport_Category  (
      id int auto_increment primary key NOT NULL,
      sport_name varchar(255),
      kcal float
);

CREATE TABLE  Sport_Like  (
      id 	int auto_increment primary key	NOT NULL,
      sport_category_id 	int COMMENT 'Sport_Category 테이블의 id',
      liked_at 	timestamp	DEFAULT now()
);

CREATE TABLE  Weight_Record  (
         id int auto_increment primary key	NOT NULL,
         user_id VARCHAR(50) COMMENT 'Users 테이블의 id',
         weight_now int,
         record_at 	timestamp	DEFAULT now()
);

CREATE TABLE  Alert  (
         id int auto_increment primary key	NOT NULL,
         type_id 	int,
         user_id 	VARCHAR(50),
         read_or_not 	varchar(10) DEFAULT 'UNREAD',
         created_at 	timestamp DEFAULT now(),
         alertTime timestamp,
         scheduled int
);

CREATE TABLE  Alert_type  (
          type_id 	int auto_increment primary key	NOT NULL,
          altype 	varchar(20)	COMMENT '종목별 알림(운동시간,식사알림,목표달성 등)',
          message1 	varchar(255),
          message2 	varchar(255)

);

create table Plan (
    plan_id            int auto_increment primary key,
    user_id           varchar(50),
    sport_plan_time    float,
    sport_plan_type    int,
    kcal_plan_amount   float,
    plan_date          datetime,
    na_plan_amount           float,
    protein_plan_amount      float,
    fiber_plan_amount        float,
    fat_plan_amount          int,
    cholesterol_plan_amount  int,
    carbohydrate_plan_amount float
);

CREATE TABLE  Sport_Record  (
     id 	int auto_increment primary key	NOT NULL,
     user_id 	VARCHAR(50)	NULL,
     sport_plan_id 	int	NULL,
     total_sport_start 	timestamp	NULL,
     total_sport_end 	timestamp	NULL,
     total_duration 	int	NULL,
     total_burn_kcal 	float	NULL
);


CREATE TABLE  Sport_plan  (
   id 	int auto_increment primary key	NOT NULL,
   user_id 	VARCHAR(50)	NULL,
   total_sport_start 	timestamp	NULL,
   total_sport_end 	timestamp	NULL,
   total_duration 	int	NULL,
   total_burn_kcal 	float	NULL,
   `check` VARCHAR(50)	NULL	DEFAULT 'before'	COMMENT 'before,record 기록전,기록후'

);

CREATE TABLE  Sport_Record_Item  (
          id 	int auto_increment primary key	NOT NULL,
          sport_record_id 	int	NULL,
          sport_category_id 	int	NULL	COMMENT '한국건강증진개발원_보건소 모바일 헬스케어 운동 api',
          sport_start 	timestamp	NULL,
          sport_end 	timestamp	NULL,
          duration 	int	NULL,
          burn_kcal 	float	NULL
);

CREATE TABLE  Sport_Plan_Item  (
        id 	int auto_increment primary key	NOT NULL,
        sport_plan_id 	int	NULL,
        sport_category_id 	int	NULL	COMMENT '한국건강증진개발원_보건소 모바일 헬스케어 운동 api',
        sport_start 	timestamp	NULL,
        sport_end 	timestamp	NULL,
        duration 	int	NULL,
        burn_kcal 	float	NULL
);



