#create 문을 전체 실행 후 진행해주세요.

use wellness;

drop table food_category;

CREATE TABLE  food_category  (
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