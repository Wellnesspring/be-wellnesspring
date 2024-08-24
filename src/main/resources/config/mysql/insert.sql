
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
VALUES (1, '수영');

INSERT INTO Sport_Routine_List
(sport_routine_id, sport_category_id, set_count, sport_count, volume, routine_start, routine_end)
VALUES (1, 1, 2, 3, 30, now(), '2024-08-30 15:00:00');

INSERT INTO Weight_record
(user_id, weight_now, record_at)
VALUES ('user01', 70, now());

INSERT INTO Alert
(type_id, user_id, read_or_not, created_at)
VALUES (1, 1, '읽음', now());

INSERT INTO Alert_type
(message, altype)
VALUES ('목표를 달성했습니다.', '목표달성알림');