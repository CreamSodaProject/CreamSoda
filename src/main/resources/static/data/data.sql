insert into USERS(email, password, name, birth_date, created_date) values ('David@naver.com', '1234', 'David', '19960807', now());
insert into USERS(email, password, name, birth_date, created_date) values ('Ho@naver.com', '1234', 'Ho', '19960807', now());
insert into USERS(email, password, name, birth_date, created_date) values ('Hee@naver.com', '1234', 'Hee', '19960807', now());

insert into SCHEDULE(title, memo, todo, label, user_id, start_time, end_time)
values ('프로젝트 세팅 마무리', 'JPA 테스트는 다 마무리 해야함!', '화이팅', 'RED', 1, '2023-09-21T20:00:00', '2023-09-22T20:00:00');
-- insert into SCHEDULE(title, memo, todo, label, user_id, start_time, end_time)
-- values ('프로젝트 진행 시작', '로그인 및 회원가입', 'JWT 설정', 'BLUE', 1, LocalDateTime.of(2023, 9, 23, 20, 0), LocalDateTime.of(2023, 9, 25, 18, 0));
-- insert into SCHEDULE(title, memo, todo, label, user_id, start_time, end_time)
-- values ('프로젝트 스케줄 등록', 'MOCK TEST 진행 하면서 진행', 'Security 설정', 'RED', 2, LocalDateTime.of(2023, 10, 03, 20, 0), LocalDateTime.of(2023, 10, 05, 18, 0));

commit ;