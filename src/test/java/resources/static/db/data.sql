insert into USERS(email, password, name, profile, created_date) values ('David@naver.com', '1234', 'David', null, now());
insert into USERS(email, password, name, profile, created_date) values ('Ho@naver.com', '1234', 'Ho', null, now());
insert into USERS(email, password, name, profile, created_date) values ('Hee@naver.com', '1234', 'Hee', null, now());

insert into EMAIL(email, auth_num, type, created_date) values ('khh5762@naver.com', '12345', 'EMAIL', '2023-09-22T20:00:00');


insert into SCHEDULE(title, memo,  label, user_id, start_time, end_time)
values ('프로젝트 세팅 마무리', 'JPA 테스트는 다 마무리 해야함!',  'RED', 1, '2023-09-21T20:00:00', '2023-09-22T20:00:00');
-- insert into SCHEDULE(title, memo, todo, label, user_id, start_time, end_time)
-- values ('프로젝트 진행 시작', '로그인 및 회원가입', 'JWT 설정', 'BLUE', 1, LocalDateTime.of(2023, 9, 23, 20, 0), LocalDateTime.of(2023, 9, 25, 18, 0));
-- insert into SCHEDULE(title, memo, todo, label, user_id, start_time, end_time)
-- values ('프로젝트 스케줄 등록', 'MOCK TEST 진행 하면서 진행', 'Security 설정', 'RED', 2, LocalDateTime.of(2023, 10, 03, 20, 0), LocalDateTime.of(2023, 10, 05, 18, 0));

commit ;