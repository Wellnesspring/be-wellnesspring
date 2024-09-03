
INSERT INTO users(
    user_id,ROLE, user_pw, name, serial_num_f, serial_num_l, phone, height, weight, join_at, alarm_agree, profileImg, point,email)
VALUES ('user01', 0, 'pw01', '홍길동', '960606','1122334','010-1234-1234',175,70,now(),'동의','img',1,'yoohwanjoo@nate.com');

INSERT INTO users(
    user_id,ROLE, user_pw, name, serial_num_f, serial_num_l, phone, height, weight, join_at, alarm_agree, profileImg, point,email)
VALUES ('user02', 0, 'pw02', '김민지', '940202','2122334','010-5678-5678',182,78,now(),'거부','img',1, 'yoohwanjoo@nate.com');
INSERT INTO social(
    platform, user_id, id_num)
VALUES ('kakao','user03','1');

INSERT INTO Food_Category
(food_name, kcal)
VALUES ('바나나', 100);

INSERT INTO Food_Category
(food_name, kcal)
VALUES ('사과', 50);

INSERT INTO Food_Like
(food_id, liked_at)
VALUES (1, now());

INSERT INTO meal
(meal_time)
VALUES (now());

INSERT INTO Meal_detail
(meal_id, food_id, meal, amount)
VALUES (1, 1, '바나나', 2);


INSERT INTO Sport_category
(sport_name, kcal)
VALUES ('축구', 2.5);

INSERT INTO Sport_category
(sport_name, kcal)
VALUES ('수영', 1.5);

INSERT INTO Sport_Like
(sport_category_id, liked_at)
VALUES (1, now());

INSERT INTO Sport_Routine
(user_id, sport_routine_name)
VALUES ('user01', '수영');

INSERT INTO Sport_Routine_List
(sport_routine_id, sport_category_id, set_count, sport_count, volume, routine_start, routine_end)
VALUES (1, 1, 2, 3, 30, now(), '2024-08-30 15:00:00');

INSERT INTO Weight_record
(user_id, weight_now, record_at)
VALUES ('user01', 70, now());

INSERT INTO Alert
(type_id, user_id, read_or_not, created_at, alertTime, scheduled)
VALUES (1, 'user01', 'UNREAD', now(),2024-08-27T12:47:00 ,5);

INSERT INTO Alert
(type_id, user_id, read_or_not, created_at, alertTime, scheduled)
VALUES (2, 'user01', 'UNREAD', now(),2024-08-27T12:47:00 ,10);

INSERT INTO Alert_type
(altype, messge1, message2)
VALUES ('운동','운동하기 ','분 전입니다!');

INSERT INTO Alert_type
(altype, messge1, message2)
VALUES ('식사','식사하기 ','분 전입니다!');

INSERT INTO Plan
(user_id, sport_plan_time, sport_plan_type, kcal_plan_amount, plan_date, protein_plan_amount,
 fiber_plan_amount, fat_plan_amount, cholesterol_plan_amount, carbohydrate_plan_amount )
VALUES ('user01',20,1,1000,2024-08-27T12:47:00,10,20,10,10,10,10);

INSERT INTO Plan
(user_id, sport_plan_time, sport_plan_type, kcal_plan_amount, plan_date, protein_plan_amount,
 fiber_plan_amount, fat_plan_amount, cholesterol_plan_amount, carbohydrate_plan_amount )
VALUES ('user01',10,1,1500,2024-08-27T12:47:00,20,10,15,15,15,15);