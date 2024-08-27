-- auto-generated definition
create user wellnessdb@localhost;

create database wellness;

use wellness;

DROP TABLE social CASCADE;
DROP TABLE Food_Category CASCADE;
DROP TABLE Food_Like CASCADE;
DROP TABLE Meal CASCADE;
DROP TABLE Meal_detail CASCADE;
DROP TABLE Sport_Category CASCADE;
DROP TABLE Sport_Like CASCADE;
DROP TABLE Sport_Routine CASCADE;
DROP TABLE Sport_Routine_List CASCADE;
DROP TABLE Weight_Record CASCADE;
DROP TABLE Alert CASCADE;
DROP TABLE Alert_type CASCADE;
DROP TABLE users CASCADE;

CREATE TABLE users(
        user_id 	VARCHAR(50) primary key	NOT NULL,
        role 	int	DEFAULT 0	COMMENT '0:회원 , 1:관리자',
        user_pw 	varchar(100) NOT NULL,
        name 	varchar(100) NOT NULL,
        serial_num_f 	varchar(100) NOT NULL	COMMENT '성별값까지 7자리 저장',
        serial_num_l 	varchar(100) NOT NULL	COMMENT '추후 암호화 생각중',
        phone 	varchar(100) NOT NULL,
        height 	varchar(100),
        weight 	varchar(100),
        join_at 	timestamp DEFAULT now() COMMENT '회원 가입일자', #now() : insert 시점의 날짜 데이터 삽입 (초까지)
        alarm_agree 	varchar(10) COMMENT '동의,거부',
        profileImg 	varchar(100),
        point 	int,
        locker varchar(100) NOT NULL
);

CREATE TABLE social(
    platform VARCHAR(20) primary key NOT NULL COMMENT '소셜 플랫폼',
    user_id VARCHAR(50) NOT NULL,
    id_num varchar(20) NOT NULL COMMENT '해당플랫폼에서의 식별값'
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

CREATE TABLE  Sport_Routine  (
     id 	int auto_increment primary key	NOT NULL,
     user_id 	VARCHAR(50)	COMMENT 'Users 테이블의 id',
     sport_routine_name 	varchar(255)
);

CREATE TABLE  Sport_Routine_List  (
      id 	int auto_increment primary key	NOT NULL,
      sport_routine_id 	int COMMENT 'Sport_Routine 테이블의 id',
      sport_category_id 	int	COMMENT 'Sport_Category 테이블의 id',
      set_count 	int,
      sport_count 	int,
      volume 	int,
      routine_start 	timestamp,
      routine_end 	timestamp
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
         message varchar(255),
         read_or_not 	varchar(10) DEFAULT 'UNREAD',
         created_at 	timestamp DEFAULT now()
);

CREATE TABLE  Alert_type  (
          type_id 	int auto_increment primary key	NOT NULL,
          message 	varchar(255),
          altype 	varchar(20)	COMMENT '종목별 알림(운동시간,식사알림,목표달성 등)'
);