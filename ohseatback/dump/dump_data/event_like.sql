-- 이벤트 좋아요(event_like) 더미 데이터 — 일반 유저 8명이 이벤트에 누른 좋아요
--   event_id → event.title 로 조회, user_id → users.email 로 조회 (FK 안전)
--   UNIQUE(event_id, user_id) : 같은 유저가 같은 이벤트를 중복 좋아요 하지 않도록 구성
--   인기 이벤트일수록 더 많은 유저가 좋아요 (차등)
-- event_like_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql, dump/dump_data/event.sql 이 먼저 적용되어 있어야 합니다.
-- ※ event.like_count 컬럼(표시용 집계값)과는 별개입니다. 아래 '집계 동기화' 참고.

INSERT INTO event_like (event_id, user_id) VALUES
-- 신작 <여름의 끝> 시사회 초대 이벤트 (5)
((SELECT event_id FROM event WHERE title='신작 <여름의 끝> 시사회 초대 이벤트'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='신작 <여름의 끝> 시사회 초대 이벤트'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='신작 <여름의 끝> 시사회 초대 이벤트'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='신작 <여름의 끝> 시사회 초대 이벤트'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='신작 <여름의 끝> 시사회 초대 이벤트'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
-- <미드나이트 서울> VIP 시사회 이벤트 (3)
((SELECT event_id FROM event WHERE title='<미드나이트 서울> VIP 시사회 이벤트'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='<미드나이트 서울> VIP 시사회 이벤트'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='<미드나이트 서울> VIP 시사회 이벤트'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 애니메이션 <별을 담은 아이> 가족 시사회 (2)
((SELECT event_id FROM event WHERE title='애니메이션 <별을 담은 아이> 가족 시사회'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='애니메이션 <별을 담은 아이> 가족 시사회'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- <라스트 씬> 배우 무대인사 시사회 (4)
((SELECT event_id FROM event WHERE title='<라스트 씬> 배우 무대인사 시사회'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='<라스트 씬> 배우 무대인사 시사회'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='<라스트 씬> 배우 무대인사 시사회'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT event_id FROM event WHERE title='<라스트 씬> 배우 무대인사 시사회'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 독립영화 특별 시사회 초대 (2)
((SELECT event_id FROM event WHERE title='독립영화 특별 시사회 초대'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='독립영화 특별 시사회 초대'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- <겨울 나그네> 리마스터 시사회 (1)
((SELECT event_id FROM event WHERE title='<겨울 나그네> 리마스터 시사회'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- <붉은 노을> 언론 시사회 이벤트 (3)
((SELECT event_id FROM event WHERE title='<붉은 노을> 언론 시사회 이벤트'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='<붉은 노을> 언론 시사회 이벤트'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='<붉은 노을> 언론 시사회 이벤트'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- <오늘의 온도> 관객 시사회 (3)
((SELECT event_id FROM event WHERE title='<오늘의 온도> 관객 시사회'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='<오늘의 온도> 관객 시사회'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT event_id FROM event WHERE title='<오늘의 온도> 관객 시사회'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 7월 예매권 증정 이벤트 (6)
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='7월 예매권 증정 이벤트'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- 커플 예매권 2매 증정 이벤트 (4)
((SELECT event_id FROM event WHERE title='커플 예매권 2매 증정 이벤트'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='커플 예매권 2매 증정 이벤트'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='커플 예매권 2매 증정 이벤트'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT event_id FROM event WHERE title='커플 예매권 2매 증정 이벤트'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- SNS 공유하고 예매권 받기 (5)
((SELECT event_id FROM event WHERE title='SNS 공유하고 예매권 받기'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='SNS 공유하고 예매권 받기'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='SNS 공유하고 예매권 받기'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='SNS 공유하고 예매권 받기'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT event_id FROM event WHERE title='SNS 공유하고 예매권 받기'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 리뷰 작성 이벤트 예매권 증정 (2)
((SELECT event_id FROM event WHERE title='리뷰 작성 이벤트 예매권 증정'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='리뷰 작성 이벤트 예매권 증정'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- 여름맞이 예매권 대방출 (4)
((SELECT event_id FROM event WHERE title='여름맞이 예매권 대방출'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='여름맞이 예매권 대방출'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT event_id FROM event WHERE title='여름맞이 예매권 대방출'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT event_id FROM event WHERE title='여름맞이 예매권 대방출'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 앱 첫 구매 예매권 이벤트 (3)
((SELECT event_id FROM event WHERE title='앱 첫 구매 예매권 이벤트'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='앱 첫 구매 예매권 이벤트'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='앱 첫 구매 예매권 이벤트'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- 주말 특가 예매권 이벤트 (2)
((SELECT event_id FROM event WHERE title='주말 특가 예매권 이벤트'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='주말 특가 예매권 이벤트'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- 회원가입 예매권 증정 (5)
((SELECT event_id FROM event WHERE title='회원가입 예매권 증정'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT event_id FROM event WHERE title='회원가입 예매권 증정'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT event_id FROM event WHERE title='회원가입 예매권 증정'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT event_id FROM event WHERE title='회원가입 예매권 증정'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT event_id FROM event WHERE title='회원가입 예매권 증정'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'));

-- ───────────────────────────────────────────────────────────
-- (선택) event.like_count 를 실제 event_like 행 수와 동기화하고 싶다면 아래 실행
-- UPDATE event e
-- SET e.like_count = (SELECT COUNT(*) FROM event_like el WHERE el.event_id = e.event_id);
