#create 문을 전체 실행 후 진행해주세요.

use wellness;

drop table Food_Category;

CREATE TABLE  Food_Category  (
     id 	int auto_increment primary key	NOT NULL,
     food_name 	varchar(255)	NOT NULL,
     kcal 	float NOT NULL,
     na 	float NOT NULL,
     protein float NOT NUll,
     fiber float NOT NUll,
     fat float NOT NUll,
     cholesterol float NOT NUll,
     carbohydrate float NOT NUll
);