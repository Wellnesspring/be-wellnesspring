-- auto-generated definition
create user wellnessdb@localhost;

create database wellness;

use wellness;

CREATE TABLE users(
          user_id 	VARCHAR(50) primary key	NOT NULL,
          role 	int	NOT NULL DEFAULT 0	COMMENT '0:회원 , 1:관리자',
          user_pw 	varchar(50)	NOT NULL,
          name 	varchar(20)	NOT NULL,
          serial_num_f 	varchar(7)	NOT NULL	COMMENT '성별값까지 7자리 저장',
          serial_num_l 	varchar(100) NOT NULL	COMMENT '추후 암호화 생각중',
          phone 	varchar(15)	NOT NULL,
          height 	int	NOT NULL,
          weight 	int	NOT NULL,
          join_at 	timestamp NOT NULL DEFAULT now() COMMENT '회원 가입일자', #now() : insert 시점의 날짜 데이터 삽입 (초까지)
          alarm_agree 	varchar(10)	NOT NULL COMMENT '동의,거부',
          profileImg 	varchar(250) NOT NULL,
          point 	int	NOT NULL
);

CREATE TABLE  Food_Category  (
     id 	int auto_increment primary key	NOT NULL,
     food_name 	varchar(255)	NOT NULL,
     kcal 	int	NOT NULL
);

CREATE TABLE  Food_Like  (
     id 	int auto_increment primary key	NOT NULL,
     food_id 	int	NOT NULL	COMMENT 'Food_Category 테이블의 id',
     liked_at 	timestamp	NOT NULL	DEFAULT now()
);

CREATE TABLE  Meal  (
        meal_id 	int auto_increment primary key	NOT NULL,
        meal_time 	timestamp	DEFAULT now() NOT NULL
);

CREATE TABLE  Meal_detail  (
       id 	int auto_increment primary key	NOT NULL,
       meal_id 	int	NOT NULL	COMMENT 'Meal 테이블의 id',
       food_id 	int	NOT NULL	COMMENT 'Food_Category 테이블의 id',
       meal 	varchar(50)	NOT NULL,
       amount 	float	NOT NULL	COMMENT '갯수,gram 등으로 표현'
);

CREATE TABLE  Sport_Category  (
      id int auto_increment primary key NOT NULL,
      sport_name varchar(255)	NOT NULL,
      kcal float	NOT NULL
);

CREATE TABLE  Sport_Like  (
      id 	int auto_increment primary key	NOT NULL,
      sport_category_id 	int	NOT NULL	COMMENT 'Sport_Category 테이블의 id',
      liked_at 	timestamp	NOT NULL	DEFAULT now()
);

CREATE TABLE  Sport_Routine  (
     id 	int auto_increment primary key	NOT NULL,
     user_id 	VARCHAR(50)	NOT NULL	COMMENT 'Users 테이블의 id',
     sport_routine_name 	varchar(255)	NOT NULL
);

CREATE TABLE  Sport_Routine_List  (
      id 	int auto_increment primary key	NOT NULL,
      sport_routine_id 	int	NOT NULL	COMMENT 'Sport_Routine 테이블의 id',
      sport_category_id 	int	NOT NULL	COMMENT 'Sport_Category 테이블의 id',
      set_count 	int	NOT NULL,
      sport_count 	int	NOT NULL,
      volume 	int	NOT NULL,
      routine_start 	timestamp	NULL,
      routine_end 	timestamp	NULL
);

CREATE TABLE  Weight_Record  (
         id int auto_increment primary key	NOT NULL,
         user_id VARCHAR(50) NOT NULL COMMENT 'Users 테이블의 id',
         weight_now int	NOT NULL,
         record_at 	timestamp	NOT NULL	DEFAULT now()
);

CREATE TABLE  Alert  (
         id int auto_increment primary key	NOT NULL,
         type_id 	int	NOT NULL,
         user_id 	VARCHAR(50)	NOT NULL,
         read_or_not 	varchar(10)	NOT NULL,
         created_at 	timestamp	NOT NULL DEFAULT now()
);

CREATE TABLE  Alert_type  (
          type_id 	int auto_increment primary key	NOT NULL,
          message 	varchar(255)	NOT NULL,
          altype 	varchar(20)	NOT NULL	COMMENT '종목별 알림(운동시간,식사알림,목표달성 등)'
);