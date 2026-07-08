-- 테스트 유저 8명 (모두 일반 유저, role='user')
-- 비밀번호(평문): qwe123!@#  → BCryptPasswordEncoder(strength 10) 해시로 저장됨
-- user_id(auto_increment), created_at(default CURRENT_TIMESTAMP)은 자동 처리
-- admin 계정은 직접 추가했으므로 제외

INSERT INTO users (email, name, nickname, password, phone_number, role) VALUES
('hong@ohseat.com', '홍길동', '길동이', '$2a$10$xALpIxY8vqMpNsUpWHifVOD4Bpvaf6UC7lbiCHStRkQx0MxVwBPaS', '010-1111-0001', 'user'),
('kim@ohseat.com',  '김철수', '철수',   '$2a$10$f8TljRS1COWMSo6lFH.hTuJ3ssQdFuvFFF.g9jh9swDPQEqjSDgVi', '010-1111-0002', 'user'),
('lee@ohseat.com',  '이영희', '영희',   '$2a$10$7h7zPFXCHrF7IyY6AO1Un.Uc2xufx2fTsAd6eqTMtbD.M8KrdzE/m', '010-1111-0003', 'user'),
('park@ohseat.com', '박민수', '민수',   '$2a$10$lu5rcSPIdP4aPzpHDCHu/.unJDq6awygNstxMaYqwX4KOU52iPxUK', '010-1111-0004', 'user'),
('choi@ohseat.com', '최지우', '지우',   '$2a$10$B8vJu6Z5vj1Xs9CfLp3k4.ugzBrp/pDAbmDCqSDr8NlSLaiE0yBsa', '010-1111-0005', 'user'),
('jung@ohseat.com', '정수빈', '수빈',   '$2a$10$PRf..a14IzxGvWJn9ex2MeJ5dlm1rqcpqiqk4J.BA./32rXT.TqhS', '010-1111-0006', 'user'),
('kang@ohseat.com', '강하늘', '하늘',   '$2a$10$..gCCGUBG7XUx0YmW4THD.kmyez0xWZukjkqtE.VVIO3419NiyV0q', '010-1111-0007', 'user'),
('yoon@ohseat.com', '윤서연', '서연',   '$2a$10$ufP5hbL9l9TRq.0fMLL2o.m5yFFyRG/BccTN2fHcJLYS7YI.5QLcK', '010-1111-0008', 'user');
