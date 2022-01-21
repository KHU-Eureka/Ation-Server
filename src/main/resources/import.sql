INSERT INTO sense(name) values("눈");
INSERT INTO sense(name) values("코");
INSERT INTO sense(name) values("입");
INSERT INTO sense(name) values("손");
INSERT INTO sense(name) values("귀");

INSERT INTO interest(name) values("개발");
INSERT INTO interest(name) values("디자인");
INSERT INTO interest(name) values("기획");
INSERT INTO interest(name) values("마케팅");
INSERT INTO interest(name) values("음악");
INSERT INTO interest(name) values("비즈니스");
INSERT INTO interest(name) values("순수예술");
INSERT INTO interest(name) values("응용예술");
INSERT INTO interest(name) values("기타");


INSERT INTO main_category(name) values("디자인");
INSERT INTO main_category(name) values("개발");
INSERT INTO main_category(name) values("기획");
INSERT INTO main_category(name) values("마케팅");
INSERT INTO main_category(name) values("뮤직 인사이트");
INSERT INTO main_category(name) values("비즈니스 경험담");
INSERT INTO main_category(name) values("Your View");
INSERT INTO main_category(name) values("기타");

INSERT INTO sub_category(name, main_category_id) values("일반디자인", 1);
INSERT INTO sub_category(name, main_category_id) values("UX/UI", 1);
INSERT INTO sub_category(name, main_category_id) values("UX", 1);
INSERT INTO sub_category(name, main_category_id) values("시각디자인", 1);
INSERT INTO sub_category(name, main_category_id) values("산업디자인", 1);
INSERT INTO sub_category(name, main_category_id) values("브랜딩", 1);
INSERT INTO sub_category(name, main_category_id) values("디자인 인사이트", 1);

INSERT INTO sub_category(name, main_category_id) values("웹개발", 2);
INSERT INTO sub_category(name, main_category_id) values("게임", 2);
INSERT INTO sub_category(name, main_category_id) values("모바일", 2);
INSERT INTO sub_category(name, main_category_id) values("AI", 2);
INSERT INTO sub_category(name, main_category_id) values("알고리즘", 2);
INSERT INTO sub_category(name, main_category_id) values("데이터분석", 2);
INSERT INTO sub_category(name, main_category_id) values("IoT", 2);

INSERT INTO sub_category(name, main_category_id) values("일반기획", 3);
INSERT INTO sub_category(name, main_category_id) values("서비스기획", 3);
INSERT INTO sub_category(name, main_category_id) values("프로젝트관리", 3);

INSERT INTO sub_category(name, main_category_id) values("일반 마케팅", 4);
INSERT INTO sub_category(name, main_category_id) values("콘텐츠 마케팅", 4);
INSERT INTO sub_category(name, main_category_id) values("브랜드 마케팅", 4);

INSERT INTO sub_category(name, main_category_id) values("힙합", 5);
INSERT INTO sub_category(name, main_category_id) values("발라드", 5);
INSERT INTO sub_category(name, main_category_id) values("POP", 5);
INSERT INTO sub_category(name, main_category_id) values("인디음악", 5);
INSERT INTO sub_category(name, main_category_id) values("R&B", 5);
INSERT INTO sub_category(name, main_category_id) values("클래식", 5);
INSERT INTO sub_category(name, main_category_id) values("락", 5);

INSERT INTO sub_category(name, main_category_id) values("스타트업", 6);
INSERT INTO sub_category(name, main_category_id) values("인턴", 6);
INSERT INTO sub_category(name, main_category_id) values("회사생활 꿀TIP", 6);