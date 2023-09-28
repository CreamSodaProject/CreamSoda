insert into USERS(email, password, name, created_date) values ('David@naver.com', '1234', 'David', now());

insert into SECHEDULE(title, memo, todo, label, start_time, end_time)
values ('프로젝트 세팅 마무리', 'JPA 테스트는 다 마무리 해야함!', '화이팅', LocalDateTime.of(2023, 9, 21, 20, 0), LocalDateTime.of(2023, 9, 22, 18, 0))

commit ;
``