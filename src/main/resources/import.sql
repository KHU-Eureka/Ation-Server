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


INSERT INTO insight_main_category(name) values("디자인");
INSERT INTO insight_main_category(name) values("개발");
INSERT INTO insight_main_category(name) values("기획");
INSERT INTO insight_main_category(name) values("마케팅");
INSERT INTO insight_main_category(name) values("뮤직 인사이트");
INSERT INTO insight_main_category(name) values("비즈니스 경험담");
INSERT INTO insight_main_category(name) values("Your View");

INSERT INTO insight_sub_category(name, insightmaincategory_id) values("UI", 1);
INSERT INTO insight_sub_category(name, insightmaincategory_id) values("UX", 1);
INSERT INTO insight_sub_category(name, insightmaincategory_id) values("React", 2);
INSERT INTO insight_sub_category(name, insightmaincategory_id) values("Spring", 2);
INSERT INTO insight_sub_category(name, insightmaincategory_id) values("Django", 2);