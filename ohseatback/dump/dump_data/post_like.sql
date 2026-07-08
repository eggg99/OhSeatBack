-- 좌석추천(post) 좋아요(post_like) 더미 데이터 — 일반 유저 8명이 게시글에 누른 좋아요
--   post_id            → post.title 로 조회
--   post_like_user_id  → users.email 로 조회
--   ※ 이 테이블엔 FK/UNIQUE 제약이 없지만, 실존 게시글·유저만 사용하고
--     같은 유저가 같은 글에 중복 좋아요 하지 않도록 구성
--   ※ 컬럼명은 create_at (오타지만 DDL 그대로). 값은 default 사용
-- post_like_id(auto_increment), create_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql, dump/dump_data/post.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO post_like (post_id, post_like_user_id) VALUES
-- [CGV 강남 1관] 명당 좌석 추천드려요 (5)
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [CGV 영등포 골드클래스] 좌석 후기 (4)
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [CGV 동탄역 1관] 어디 앉아야 좋을까요 (3)
((SELECT post_id FROM post WHERE title='[CGV 동탄역 1관] 어디 앉아야 좋을까요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 동탄역 1관] 어디 앉아야 좋을까요'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 동탄역 1관] 어디 앉아야 좋을까요'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [CGV 판교 1관] 사운드 명당 공유 (4)
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [CGV 인천 10관] 좌석 추천 (2)
((SELECT post_id FROM post WHERE title='[CGV 인천 10관] 좌석 추천'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 인천 10관] 좌석 추천'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [CGV 천안펜타포트 10관] 명당 어디? (2)
((SELECT post_id FROM post WHERE title='[CGV 천안펜타포트 10관] 명당 어디?'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 천안펜타포트 10관] 명당 어디?'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [CGV 대구 10관] 좌석 후기 남겨요 (3)
((SELECT post_id FROM post WHERE title='[CGV 대구 10관] 좌석 후기 남겨요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 대구 10관] 좌석 후기 남겨요'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 대구 10관] 좌석 후기 남겨요'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- [CGV 서면삼정타워 1관] 명당 좌석 (3)
((SELECT post_id FROM post WHERE title='[CGV 서면삼정타워 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 서면삼정타워 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 서면삼정타워 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [CGV 전주고사 1관] 좌석 추천드려요 (2)
((SELECT post_id FROM post WHERE title='[CGV 전주고사 1관] 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[CGV 전주고사 1관] 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- [메가박스 강남 1관] 명당 자리 공유 (4)
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [메가박스 상봉 1관] 좌석 후기 (2)
((SELECT post_id FROM post WHERE title='[메가박스 상봉 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 상봉 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- [메가박스 백석벨라시타 101호] 명당 (3)
((SELECT post_id FROM post WHERE title='[메가박스 백석벨라시타 101호] 명당'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 백석벨라시타 101호] 명당'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 백석벨라시타 101호] 명당'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
-- [메가박스 수원인계 컴포트1관] 좌석 추천 (3)
((SELECT post_id FROM post WHERE title='[메가박스 수원인계 컴포트1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 수원인계 컴포트1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 수원인계 컴포트1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
-- [메가박스 속초점 1관] 좌석 후기 (2)
((SELECT post_id FROM post WHERE title='[메가박스 속초점 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 속초점 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [메가박스 대전유성 1관] 명당 좌석 (2)
((SELECT post_id FROM post WHERE title='[메가박스 대전유성 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 대전유성 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- [메가박스 덕천 1관] 좌석 추천 (2)
((SELECT post_id FROM post WHERE title='[메가박스 덕천 1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 덕천 1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
-- [메가박스 제주삼화 1관] 명당 공유 (2)
((SELECT post_id FROM post WHERE title='[메가박스 제주삼화 1관] 명당 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM post WHERE title='[메가박스 제주삼화 1관] 명당 공유'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- [롯데시네마 월드타워 10관] 명당 좌석 (5)
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
-- [롯데시네마 강동 10관] 좌석 후기 (3)
((SELECT post_id FROM post WHERE title='[롯데시네마 강동 10관] 좌석 후기'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 강동 10관] 좌석 후기'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 강동 10관] 좌석 후기'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [롯데시네마 평촌 1관] 명당 자리 (2)
((SELECT post_id FROM post WHERE title='[롯데시네마 평촌 1관] 명당 자리'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 평촌 1관] 명당 자리'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- [롯데시네마 강릉 1관] 좌석 추천 (2)
((SELECT post_id FROM post WHERE title='[롯데시네마 강릉 1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 강릉 1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [롯데시네마 대전 1관] 명당 좌석 (2)
((SELECT post_id FROM post WHERE title='[롯데시네마 대전 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 대전 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- [롯데시네마 동래관 1관] 좌석 후기 (2)
((SELECT post_id FROM post WHERE title='[롯데시네마 동래관 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 동래관 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [롯데시네마 광주 2관] 명당 좌석 공유 (2)
((SELECT post_id FROM post WHERE title='[롯데시네마 광주 2관] 명당 좌석 공유'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM post WHERE title='[롯데시네마 광주 2관] 명당 좌석 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com'));

-- ───────────────────────────────────────────────────────────
-- (선택) post.like_count 를 실제 post_like 행 수와 동기화하려면 아래 실행
-- UPDATE post p
-- SET p.like_count = (SELECT COUNT(*) FROM post_like pl WHERE pl.post_id = p.post_id);
