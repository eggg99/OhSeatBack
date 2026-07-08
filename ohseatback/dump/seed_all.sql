-- ==============================================================
-- OhSeat 통합 시드 스크립트 (seed_all.sql)
-- 의존성 순서대로 실행: users -> 게시판 -> 댓글/좋아요
-- author/작성자/좋아요 유저는 email·제목 서브쿼리로 연결 (ID 무관)
-- 생성일: 2026-07-08
-- ==============================================================


-- ############################################################
-- # FILE: dump/dump_data/users.sql
-- ############################################################
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


-- ############################################################
-- # FILE: dump/dump_data/cinesquare.sql
-- ############################################################
-- 씨네광장(cinesquare) 더미 데이터 30건
--   category_id = 1 : 자유수다 (15건)
--   category_id = 2 : 구인구직 (15건)
-- author_id 는 insert-users.sql 로 넣은 유저를 email 로 조회해 FK 안전하게 매핑
-- post_id(auto_increment) 는 자동 처리
-- ※ 실행 전 insert-users.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO cinesquare (title, content, views, like_count, created_at, author_id, category_id, city, district) VALUES
-- ===== 자유수다 (category_id = 1) =====
('어제 본 영화 여운이 너무 남아요', '엔딩 보고 한동안 자리에서 못 일어났어요. 이런 영화 오랜만이네요. 다들 여운 남는 영화 있으신가요?', 152, 23, '2026-06-01 20:14:00', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), 1, '서울특별시', '강남구'),
('요즘 볼만한 영화 추천 좀!', '이번 주말에 영화관 가려는데 뭐 볼지 고민이에요. 장르 안 가리니까 인생영화급으로 추천 부탁드려요.', 341, 41, '2026-06-02 11:02:00', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), 1, '서울특별시', '마포구'),
('팝콘 없이 영화 보는 사람?', '저는 팝콘 냄새랑 소리가 신경 쓰여서 아무것도 안 사고 봐요. 저만 그런가요 ㅋㅋ', 98, 12, '2026-06-03 15:30:00', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), 1, '경기도', '성남시'),
('엔딩크레딧 끝까지 보시나요?', '쿠키영상 때문에 요즘은 무조건 끝까지 봐요. 마블 아니어도 습관이 됐네요.', 210, 30, '2026-06-04 18:45:00', (SELECT user_id FROM users WHERE email='park@ohseat.com'), 1, '부산광역시', '해운대구'),
('심야영화 매력에 빠졌어요', '사람 적고 조용해서 몰입이 잘 돼요. 끝나고 새벽 공기 마시는 것도 좋고. 심야파 계신가요?', 176, 27, '2026-06-05 23:10:00', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), 1, '서울특별시', '송파구'),
('OTT vs 영화관, 여러분의 선택은?', '집에서 편하게 보는 것도 좋지만 큰 화면 사운드는 못 이기더라고요. 다들 어떻게 보세요?', 288, 35, '2026-06-06 13:20:00', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), 1, '인천광역시', '연수구'),
('인생영화 하나씩 공유해요', '댓글로 각자 인생영화 하나씩 남겨봐요. 저는 잔잔한 드라마 장르가 최고입니다.', 402, 58, '2026-06-07 10:05:00', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), 1, '대구광역시', '수성구'),
('영화관 명당 자리 어디인가요?', '보통 정중앙 뒤쪽을 선호하는데 여러분 명당 좌석 공유해주세요. 상영관마다 다르려나요?', 134, 19, '2026-06-08 16:40:00', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), 1, '서울특별시', '영등포구'),
('스포일러 당했을 때 대처법', '개봉 첫 주에 커뮤니티 잘못 들어갔다가 결말 알아버렸네요... 다들 어떻게 피하시나요?', 167, 21, '2026-06-09 21:15:00', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), 1, '경기도', '수원시'),
('재개봉 영화 보러 가실 분?', '예전에 놓쳤던 명작이 재개봉한대요. 큰 화면으로 다시 보고 싶은 영화 있으세요?', 145, 18, '2026-06-10 14:50:00', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), 1, '서울특별시', '종로구'),
('영화 보고 나서 뭐 드세요?', '저는 무조건 근처에서 국밥 한 그릇 해요. 영화 후 코스 있으신 분 공유해요.', 121, 15, '2026-06-11 19:30:00', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), 1, '부산광역시', '부산진구'),
('사운드 좋은 상영관 추천', '음향이 진짜 중요하더라고요. 특별관 위주로 다니는데 추천 상영관 있으면 알려주세요.', 198, 26, '2026-06-12 12:00:00', (SELECT user_id FROM users WHERE email='park@ohseat.com'), 1, '서울특별시', '용산구'),
('혼자 영화 보는 거 어때요?', '처음엔 어색했는데 이제 혼영이 제일 편해요. 내 페이스대로 볼 수 있어서 좋아요.', 233, 33, '2026-06-13 17:25:00', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), 1, '광주광역시', '서구'),
('예고편만 보고 실망한 적 있나요?', '예고편이 제일 재밌었던 영화... 다들 하나쯤 있죠? ㅋㅋ 낚였던 경험 공유해요.', 189, 24, '2026-06-14 22:40:00', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), 1, '경기도', '고양시'),
('굿즈 모으는 재미', '오리지널 티켓이랑 포스터 모으는 취미가 생겼어요. 굿즈 수집하시는 분 계세요?', 156, 20, '2026-06-15 09:35:00', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), 1, '대전광역시', '유성구'),
-- ===== 구인구직 (category_id = 2) =====
('[구인] 주말 영화관 스태프 모집합니다', '주말 오후 근무 가능한 영화관 스태프 구합니다. 매표/안내 업무이며 초보 환영, 자세한 조건은 쪽지 주세요.', 312, 8, '2026-06-16 10:00:00', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), 2, '서울특별시', '강남구'),
('[구직] 영화관 아르바이트 구해요', '평일 오전~오후 근무 가능한 대학생입니다. 매점/티켓 경험 있고 성실하게 일하겠습니다. 연락 기다립니다.', 205, 5, '2026-06-17 09:20:00', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), 2, '서울특별시', '마포구'),
('[구인] 단편영화 촬영 스태프 급구', '이번 주말 단편영화 촬영 도와주실 스태프 급구합니다. 촬영/조명 보조, 식사 및 소정의 페이 제공합니다.', 278, 6, '2026-06-18 14:10:00', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), 2, '경기도', '성남시'),
('[구직] 영상편집 도와드립니다', '프리미어/애프터이펙트 다룹니다. 유튜브 영상, 브이로그 편집 경험 많아요. 포트폴리오 있습니다.', 241, 11, '2026-06-19 16:30:00', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), 2, '부산광역시', '해운대구'),
('[구인] 영화 리뷰 작성 알바 모집', '영화 리뷰 콘텐츠 작성해주실 분 모집합니다. 재택 가능, 건당 지급이며 영화 좋아하는 분이면 좋겠어요.', 356, 9, '2026-06-20 11:45:00', (SELECT user_id FROM users WHERE email='park@ohseat.com'), 2, '서울특별시', '송파구'),
('[구직] 주중 오전 근무 가능합니다', '주중 오전 시간대에 일할 곳 찾고 있습니다. 서비스직 경험 있고 근처 거주라 출근 빠릅니다.', 187, 4, '2026-06-21 08:50:00', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), 2, '인천광역시', '연수구'),
('[구인] 시사회 안내요원 모집', '주말 시사회 현장 안내요원 모집합니다. 관객 안내 및 좌석 정리, 밝은 성격이면 누구나 지원 가능합니다.', 299, 7, '2026-06-22 13:15:00', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), 2, '대구광역시', '수성구'),
('[구직] 포스터 디자인 가능한 사람 찾으시나요', '포토샵/일러스트 능숙합니다. 영화 포스터, 홍보물 디자인 작업 구합니다. 단가 협의 가능해요.', 223, 10, '2026-06-23 15:40:00', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), 2, '서울특별시', '영등포구'),
('[구인] 영화제 자원봉사자 모집', '지역 영화제 운영을 도와줄 자원봉사자를 모집합니다. 봉사시간 인정, 굿즈 및 식사 제공됩니다.', 401, 14, '2026-06-24 10:30:00', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), 2, '경기도', '수원시'),
('[구직] 매점 경력 있습니다 구직해요', '멀티플렉스 매점에서 1년 근무한 경력자입니다. 주말 포함 근무 가능하며 성실히 일하겠습니다.', 168, 3, '2026-06-25 09:00:00', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), 2, '서울특별시', '종로구'),
('[구인] 상영관 청소 인력 구합니다', '심야 시간대 상영관 청소 인력 구합니다. 상영 종료 후 2~3시간 근무, 시급 협의 가능합니다.', 190, 2, '2026-06-26 23:30:00', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), 2, '부산광역시', '부산진구'),
('[구직] 티켓팅 도우미 지원합니다', '컴퓨터 활용 능숙하고 응대 경험 많습니다. 주말 티켓팅/키오스크 안내 업무 지원합니다.', 152, 4, '2026-06-27 17:20:00', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), 2, '서울특별시', '용산구'),
('[구인] 홍보 SNS 운영자 모집', '영화관 인스타/블로그 운영해주실 분 모집합니다. 재택 가능하며 콘텐츠 기획 경험 있으면 우대합니다.', 267, 6, '2026-06-28 12:10:00', (SELECT user_id FROM users WHERE email='park@ohseat.com'), 2, '광주광역시', '서구'),
('[구직] 영화관 인근 거주, 야간 가능', '영화관 도보 5분 거리에 살아서 야간/마감 근무 가능합니다. 책임감 있게 일하겠습니다.', 143, 3, '2026-06-29 21:00:00', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), 2, '경기도', '고양시'),
('[구인] 팝업스토어 판매 알바 구인', '영화 팝업스토어 굿즈 판매 알바 구합니다. 단기 근무, 판매 경험 있는 분 우대하며 친절하신 분 환영해요.', 231, 5, '2026-06-30 11:30:00', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), 2, '대전광역시', '유성구');


-- ############################################################
-- # FILE: dump/dump_data/comments_cinesquare.sql
-- ############################################################
-- 씨네광장(cinesquare) 댓글 더미 데이터 36건
-- commenter_id → users.email 로 조회 (FK 안전)
-- post_id      → cinesquare.title 로 조회 (내가 넣은 제목은 유일하므로 1건 매핑)
-- comment_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql, dump/dump_data/cinesquare.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO comments_cinesquare (content, created_at, updated_at, commenter_id, post_id) VALUES
-- '어제 본 영화 여운이 너무 남아요'
('저도 그 영화 보고 며칠 동안 생각났어요. 진짜 여운 오래가더라고요.', '2026-06-01 21:00:00', NULL, (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  (SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요')),
('제목이 궁금하네요! 어떤 영화인지 살짝 힌트라도 주실 수 있나요?', '2026-06-01 22:10:00', NULL, (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  (SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요')),
('저는 요즘 잔잔한 영화가 더 오래 남더라고요. 공감합니다.', '2026-06-02 09:30:00', NULL, (SELECT user_id FROM users WHERE email='park@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요')),
-- '요즘 볼만한 영화 추천 좀!'
('최근 개봉작 중에 평 좋은 거 하나 있어요. 쪽지로 알려드릴게요!', '2026-06-02 12:00:00', NULL, (SELECT user_id FROM users WHERE email='choi@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!')),
('장르 안 가리시면 액션 하나 강추합니다. 사운드로 봐야 제맛이에요.', '2026-06-02 13:25:00', NULL, (SELECT user_id FROM users WHERE email='jung@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!')),
('저도 추천 받으러 왔어요 ㅋㅋ 댓글 정독 중입니다.', '2026-06-02 15:40:00', NULL, (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!')),
-- '팝콘 없이 영화 보는 사람?'
('저요! 냄새 신경 쓰여서 물만 들고 들어가요.', '2026-06-03 16:00:00', NULL, (SELECT user_id FROM users WHERE email='hong@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='팝콘 없이 영화 보는 사람?')),
('전 팝콘 없으면 허전해서 꼭 사요 ㅋㅋ 사람마다 다르네요.', '2026-06-03 17:20:00', NULL, (SELECT user_id FROM users WHERE email='kang@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='팝콘 없이 영화 보는 사람?')),
-- '엔딩크레딧 끝까지 보시나요?'
('무조건 끝까지 봐요. 쿠키 놓치면 아쉽잖아요.', '2026-06-04 19:30:00', NULL, (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  (SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?')),
('예전엔 바로 나갔는데 쿠키 문화 생기고 나서 습관됐어요.', '2026-06-04 20:15:00', '2026-06-04 20:20:00', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?')),
-- '심야영화 매력에 빠졌어요'
('심야 상영 진짜 조용하고 좋죠. 몰입 최고예요.', '2026-06-06 00:10:00', NULL, (SELECT user_id FROM users WHERE email='park@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요')),
('끝나고 새벽 공기 마시는 그 느낌 아는 사람만 알죠 ㅋㅋ', '2026-06-06 08:00:00', NULL, (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요')),
-- 'OTT vs 영화관, 여러분의 선택은?'
('편한 건 OTT지만 큰 작품은 무조건 영화관이요.', '2026-06-06 14:00:00', NULL, (SELECT user_id FROM users WHERE email='hong@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?')),
('요즘 티켓값 올라서 고민되긴 하더라고요...', '2026-06-06 16:30:00', NULL, (SELECT user_id FROM users WHERE email='choi@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?')),
('저는 둘 다 써요. 상황 따라 골라 봅니다.', '2026-06-07 10:00:00', NULL, (SELECT user_id FROM users WHERE email='kang@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?')),
-- '인생영화 하나씩 공유해요'
('저는 잔잔한 성장 드라마 한 편이 인생영화예요.', '2026-06-07 11:00:00', NULL, (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  (SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요')),
('이런 글 너무 좋아요. 댓글 보면서 위시리스트 채우는 중 ㅋㅋ', '2026-06-07 13:20:00', NULL, (SELECT user_id FROM users WHERE email='lee@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요')),
('한 편만 고르기 너무 어렵네요 ㅠㅠ', '2026-06-07 15:45:00', NULL, (SELECT user_id FROM users WHERE email='park@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요')),
-- '영화관 명당 자리 어디인가요?'
('저는 화면 대각선 기준 정중앙 뒤쪽 선호해요.', '2026-06-08 17:00:00', NULL, (SELECT user_id FROM users WHERE email='jung@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='영화관 명당 자리 어디인가요?')),
('상영관 크기마다 달라서 예매 전에 좌석 배치도 꼭 확인해요.', '2026-06-08 18:30:00', NULL, (SELECT user_id FROM users WHERE email='hong@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='영화관 명당 자리 어디인가요?')),
-- '스포일러 당했을 때 대처법'
('개봉 첫 주엔 커뮤니티 자체를 안 들어가요 ㅋㅋ 자기방어',  '2026-06-09 22:00:00', NULL, (SELECT user_id FROM users WHERE email='choi@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='스포일러 당했을 때 대처법')),
('스포 방지 확장앱 쓰는 것도 방법이에요.', '2026-06-10 09:10:00', NULL, (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='스포일러 당했을 때 대처법')),
-- '재개봉 영화 보러 가실 분?'
('저 갈래요! 큰 화면으로 다시 보고 싶었어요.', '2026-06-10 15:30:00', NULL, (SELECT user_id FROM users WHERE email='kang@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='재개봉 영화 보러 가실 분?')),
('재개봉은 놓치면 또 언제 볼지 모르니 꼭 가야죠.', '2026-06-10 17:00:00', NULL, (SELECT user_id FROM users WHERE email='kim@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='재개봉 영화 보러 가실 분?')),
-- '영화 보고 나서 뭐 드세요?'
('저도 국밥파입니다 ㅋㅋ 영화 후엔 뜨끈한 국물이죠.', '2026-06-11 20:00:00', NULL, (SELECT user_id FROM users WHERE email='park@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='영화 보고 나서 뭐 드세요?')),
('전 무조건 카페 가서 감상 정리해요.', '2026-06-11 21:15:00', NULL, (SELECT user_id FROM users WHERE email='lee@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='영화 보고 나서 뭐 드세요?')),
-- '사운드 좋은 상영관 추천'
('특별관 한번 가보면 일반관 못 가요... 사운드 차원이 달라요.', '2026-06-12 13:00:00', NULL, (SELECT user_id FROM users WHERE email='choi@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천')),
('음향 중요하다는 거 완전 공감합니다.', '2026-06-12 14:30:00', NULL, (SELECT user_id FROM users WHERE email='jung@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천')),
-- '혼자 영화 보는 거 어때요?'
('혼영 최고예요. 내 페이스대로 볼 수 있어서 편해요.', '2026-06-13 18:00:00', NULL, (SELECT user_id FROM users WHERE email='hong@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?')),
('처음엔 어색했는데 지금은 오히려 혼자가 좋아요.', '2026-06-13 19:20:00', '2026-06-13 19:25:00', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?')),
-- '예고편만 보고 실망한 적 있나요?'
('예고편이 하이라이트였던 영화 진짜 많죠 ㅋㅋㅋ', '2026-06-14 23:00:00', NULL, (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='예고편만 보고 실망한 적 있나요?')),
('그래서 요즘은 예고편 잘 안 봐요. 스포도 되고요.', '2026-06-15 08:30:00', NULL, (SELECT user_id FROM users WHERE email='kim@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='예고편만 보고 실망한 적 있나요?')),
-- '굿즈 모으는 재미'
('오리지널 티켓 모으는 거 저도 해요! 소소한 행복이죠.', '2026-06-15 10:00:00', NULL, (SELECT user_id FROM users WHERE email='park@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='굿즈 모으는 재미')),
('포스터 보관이 은근 어렵더라고요. 팁 있으면 공유해요!', '2026-06-15 11:30:00', NULL, (SELECT user_id FROM users WHERE email='lee@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='굿즈 모으는 재미')),
-- 구인구직 게시글 댓글
('혹시 근무 요일 협의 가능한가요? 관심 있습니다!', '2026-06-16 12:00:00', NULL, (SELECT user_id FROM users WHERE email='choi@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='[구인] 주말 영화관 스태프 모집합니다')),
('경력 없어도 지원 가능할까요? 성실하게 배우겠습니다.', '2026-06-18 15:00:00', NULL, (SELECT user_id FROM users WHERE email='hong@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='[구인] 단편영화 촬영 스태프 급구')),
('재택 리뷰 알바 관심 많아요. 지원 방법 알려주세요!', '2026-06-20 13:00:00', NULL, (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), (SELECT post_id FROM cinesquare WHERE title='[구인] 영화 리뷰 작성 알바 모집'));


-- ############################################################
-- # FILE: dump/dump_data/post_like_cinesquare.sql
-- ############################################################
-- 씨네광장(cinesquare) 좋아요(post_like_cinesquare) 더미 데이터 — 일반 유저 8명
--   post_id            → cinesquare.title 로 조회 (제목 유일 → 1건 매핑)
--   post_like_user_id  → users.email 로 조회 (FK 안전)
--   UNIQUE(post_id, post_like_user_id) : 같은 유저가 같은 글에 중복 좋아요 하지 않도록 구성
--   자유수다 글에 좋아요 많이, 구인구직 글은 적게 차등
-- post_like_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql, dump/dump_data/cinesquare.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO post_like_cinesquare (post_id, post_like_user_id) VALUES
-- ===== 자유수다 =====
-- 어제 본 영화 여운이 너무 남아요 (4)
((SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='어제 본 영화 여운이 너무 남아요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- 요즘 볼만한 영화 추천 좀! (6)
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='요즘 볼만한 영화 추천 좀!'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- 팝콘 없이 영화 보는 사람? (2)
((SELECT post_id FROM cinesquare WHERE title='팝콘 없이 영화 보는 사람?'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='팝콘 없이 영화 보는 사람?'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- 엔딩크레딧 끝까지 보시나요? (4)
((SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='엔딩크레딧 끝까지 보시나요?'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 심야영화 매력에 빠졌어요 (4)
((SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='심야영화 매력에 빠졌어요'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- OTT vs 영화관, 여러분의 선택은? (5)
((SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='OTT vs 영화관, 여러분의 선택은?'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 인생영화 하나씩 공유해요 (7)
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='인생영화 하나씩 공유해요'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- 영화관 명당 자리 어디인가요? (3)
((SELECT post_id FROM cinesquare WHERE title='영화관 명당 자리 어디인가요?'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='영화관 명당 자리 어디인가요?'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='영화관 명당 자리 어디인가요?'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 스포일러 당했을 때 대처법 (3)
((SELECT post_id FROM cinesquare WHERE title='스포일러 당했을 때 대처법'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='스포일러 당했을 때 대처법'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='스포일러 당했을 때 대처법'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 재개봉 영화 보러 가실 분? (2)
((SELECT post_id FROM cinesquare WHERE title='재개봉 영화 보러 가실 분?'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='재개봉 영화 보러 가실 분?'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
-- 영화 보고 나서 뭐 드세요? (2)
((SELECT post_id FROM cinesquare WHERE title='영화 보고 나서 뭐 드세요?'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='영화 보고 나서 뭐 드세요?'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
-- 사운드 좋은 상영관 추천 (4)
((SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='사운드 좋은 상영관 추천'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 혼자 영화 보는 거 어때요? (5)
((SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='혼자 영화 보는 거 어때요?'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 예고편만 보고 실망한 적 있나요? (3)
((SELECT post_id FROM cinesquare WHERE title='예고편만 보고 실망한 적 있나요?'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='예고편만 보고 실망한 적 있나요?'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='예고편만 보고 실망한 적 있나요?'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- 굿즈 모으는 재미 (3)
((SELECT post_id FROM cinesquare WHERE title='굿즈 모으는 재미'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='굿즈 모으는 재미'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='굿즈 모으는 재미'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- ===== 구인구직 =====
-- [구인] 주말 영화관 스태프 모집합니다 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 주말 영화관 스태프 모집합니다'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 주말 영화관 스태프 모집합니다'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [구직] 영화관 아르바이트 구해요 (1)
((SELECT post_id FROM cinesquare WHERE title='[구직] 영화관 아르바이트 구해요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- [구인] 단편영화 촬영 스태프 급구 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 단편영화 촬영 스태프 급구'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 단편영화 촬영 스태프 급구'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- [구직] 영상편집 도와드립니다 (3)
((SELECT post_id FROM cinesquare WHERE title='[구직] 영상편집 도와드립니다'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구직] 영상편집 도와드립니다'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구직] 영상편집 도와드립니다'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- [구인] 영화 리뷰 작성 알바 모집 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화 리뷰 작성 알바 모집'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화 리뷰 작성 알바 모집'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [구직] 주중 오전 근무 가능합니다 (1)
((SELECT post_id FROM cinesquare WHERE title='[구직] 주중 오전 근무 가능합니다'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
-- [구인] 시사회 안내요원 모집 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 시사회 안내요원 모집'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 시사회 안내요원 모집'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- [구직] 포스터 디자인 가능한 사람 찾으시나요 (3)
((SELECT post_id FROM cinesquare WHERE title='[구직] 포스터 디자인 가능한 사람 찾으시나요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구직] 포스터 디자인 가능한 사람 찾으시나요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구직] 포스터 디자인 가능한 사람 찾으시나요'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [구인] 영화제 자원봉사자 모집 (4)
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화제 자원봉사자 모집'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화제 자원봉사자 모집'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화제 자원봉사자 모집'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 영화제 자원봉사자 모집'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com')),
-- [구직] 매점 경력 있습니다 구직해요 (1)
((SELECT post_id FROM cinesquare WHERE title='[구직] 매점 경력 있습니다 구직해요'), (SELECT user_id FROM users WHERE email='park@ohseat.com')),
-- [구인] 상영관 청소 인력 구합니다 (1)
((SELECT post_id FROM cinesquare WHERE title='[구인] 상영관 청소 인력 구합니다'), (SELECT user_id FROM users WHERE email='kim@ohseat.com')),
-- [구직] 티켓팅 도우미 지원합니다 (1)
((SELECT post_id FROM cinesquare WHERE title='[구직] 티켓팅 도우미 지원합니다'), (SELECT user_id FROM users WHERE email='lee@ohseat.com')),
-- [구인] 홍보 SNS 운영자 모집 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 홍보 SNS 운영자 모집'), (SELECT user_id FROM users WHERE email='choi@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 홍보 SNS 운영자 모집'), (SELECT user_id FROM users WHERE email='jung@ohseat.com')),
-- [구직] 영화관 인근 거주, 야간 가능 (1)
((SELECT post_id FROM cinesquare WHERE title='[구직] 영화관 인근 거주, 야간 가능'), (SELECT user_id FROM users WHERE email='kang@ohseat.com')),
-- [구인] 팝업스토어 판매 알바 구인 (2)
((SELECT post_id FROM cinesquare WHERE title='[구인] 팝업스토어 판매 알바 구인'), (SELECT user_id FROM users WHERE email='hong@ohseat.com')),
((SELECT post_id FROM cinesquare WHERE title='[구인] 팝업스토어 판매 알바 구인'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'));

-- ───────────────────────────────────────────────────────────
-- (선택) cinesquare.like_count 를 실제 좋아요 행 수와 동기화하려면 아래 실행
-- UPDATE cinesquare c
-- SET c.like_count = (SELECT COUNT(*) FROM post_like_cinesquare pl WHERE pl.post_id = c.post_id);


-- ############################################################
-- # FILE: dump/dump_data/event.sql
-- ############################################################
-- 이벤트(event) 더미 데이터 16건 — event_se = 'EVT'
--   category_id : 1 = 시사회, 2 = 예매권  (0 = 전체는 필터용)
--   author_id   : 관리자(admin@naver.com) 를 email 로 조회 (FK 안전)
--   진행 상태는 오늘(2026-07-08) 기준 start_dt <= 오늘 <= end_dt 면 '진행중'
--     - 진행중 : 2026-07-01 ~ 2026-07-31
--     - 예정   : 2026-07-20 ~ 2026-08-15 (start_dt 가 미래)
--     - 종료   : 2026-06-01 ~ 2026-06-30 (end_dt 가 과거)
-- event_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 관리자 유저(admin@naver.com)가 users 테이블에 있어야 합니다.

INSERT INTO event (event_se, category_id, author_id, title, content, ann_count, start_dt, end_dt, views, like_count, created_at) VALUES
-- ===== 시사회 (category_id = 1) =====
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '신작 <여름의 끝> 시사회 초대 이벤트', '개봉 전 <여름의 끝>을 가장 먼저 만나보세요! 추첨을 통해 50분을 시사회에 초대합니다. 응모 기간 내 참여해 주세요.', 1, '2026-07-01', '2026-07-31', 1240, 86, '2026-06-28 10:00:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '<미드나이트 서울> VIP 시사회 이벤트', '주연 배우 무대인사가 함께하는 VIP 시사회! 커플 관람권으로 응모하시면 두 분 함께 초대됩니다.', 0, '2026-07-05', '2026-07-25', 980, 61, '2026-07-02 11:30:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '애니메이션 <별을 담은 아이> 가족 시사회', '온 가족이 함께 즐기는 애니메이션 가족 시사회입니다. 자녀 동반 관람 가능하며 소정의 굿즈도 증정합니다.', 0, '2026-07-20', '2026-08-15', 320, 24, '2026-07-06 09:15:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '<라스트 씬> 배우 무대인사 시사회', '<라스트 씬> 개봉 기념 배우 무대인사 시사회가 성황리에 마감되었습니다. 당첨자 발표를 확인해 주세요.', 2, '2026-06-01', '2026-06-20', 2110, 143, '2026-05-28 14:00:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '독립영화 특별 시사회 초대', '주목받는 독립영화들을 한자리에서! 영화 애호가를 위한 특별 시사회에 여러분을 초대합니다.', 0, '2026-07-03', '2026-07-28', 640, 48, '2026-06-30 16:20:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '<겨울 나그네> 리마스터 시사회', '명작 <겨울 나그네>가 4K 리마스터로 돌아옵니다. 큰 화면으로 다시 만나는 감동, 시사회에서 먼저 경험하세요.', 0, '2026-07-25', '2026-08-10', 210, 17, '2026-07-07 10:45:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '<붉은 노을> 언론 시사회 이벤트', '언론 시사회 연계 관객 초청 이벤트가 종료되었습니다. 많은 참여 감사드립니다.', 1, '2026-06-05', '2026-06-30', 1560, 92, '2026-06-01 13:10:00'),
('EVT', 1, (SELECT user_id FROM users WHERE email='admin@naver.com'), '<오늘의 온도> 관객 시사회', '올여름 화제작 <오늘의 온도> 관객 시사회! 응모 후 당첨되시면 개봉 전 특별 상영에 초대됩니다.', 0, '2026-07-08', '2026-07-30', 870, 55, '2026-07-04 15:00:00'),
-- ===== 예매권 (category_id = 2) =====
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '7월 예매권 증정 이벤트', '7월 한 달간 진행되는 예매권 증정 이벤트! 출석 체크만 해도 추첨을 통해 예매권을 드립니다.', 0, '2026-07-01', '2026-07-31', 1830, 120, '2026-06-29 09:00:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '커플 예매권 2매 증정 이벤트', '연인과 함께하는 영화 데이트! 응모하신 분 중 추첨을 통해 커플 예매권 2매를 증정합니다.', 0, '2026-07-04', '2026-07-27', 1420, 98, '2026-07-01 10:30:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), 'SNS 공유하고 예매권 받기', '이벤트 게시글을 SNS에 공유하고 인증하시면 예매권 응모 완료! 간단한 참여로 영화 한 편 무료 관람 기회를 잡으세요.', 0, '2026-07-02', '2026-07-31', 2050, 134, '2026-06-30 11:00:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '리뷰 작성 이벤트 예매권 증정', '관람 후 리뷰를 남겨주신 분들 중 추첨을 통해 예매권을 드립니다. 여러분의 생생한 후기를 기다립니다.', 0, '2026-07-22', '2026-08-12', 290, 21, '2026-07-06 14:40:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '여름맞이 예매권 대방출', '무더운 여름, 시원한 영화관에서! 여름맞이 예매권 대방출 이벤트에 지금 참여하세요.', 0, '2026-07-06', '2026-07-29', 1670, 110, '2026-07-03 12:15:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '앱 첫 구매 예매권 이벤트', '앱에서 첫 예매를 완료하신 분께 다음 관람에 사용 가능한 예매권을 드립니다. 신규 회원 필수 혜택!', 0, '2026-07-01', '2026-07-31', 1180, 77, '2026-06-28 17:00:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '주말 특가 예매권 이벤트', '주말 한정 특가 예매권 이벤트가 종료되었습니다. 다음 이벤트도 많은 관심 부탁드립니다.', 0, '2026-06-07', '2026-06-28', 1390, 84, '2026-06-04 10:00:00'),
('EVT', 2, (SELECT user_id FROM users WHERE email='admin@naver.com'), '회원가입 예매권 증정', '신규 회원가입 시 즉시 사용 가능한 예매권을 드립니다. 지금 가입하고 첫 영화를 무료로 즐겨보세요!', 0, '2026-07-01', '2026-07-31', 2240, 156, '2026-06-27 09:30:00');


-- ############################################################
-- # FILE: dump/dump_data/event_like.sql
-- ############################################################
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


-- ############################################################
-- # FILE: dump/dump_data/notice.sql
-- ############################################################
-- 공지사항(notice) 더미 데이터 12건 — 관리자(admin@naver.com) 작성
--   target_board : CINESQUARE(씨네광장) 6건 / RECOMMEND(추천게시판) 6건
--   is_pinned    : 각 게시판 대표 공지 1건씩 상단 고정
--   is_active    : 대부분 노출(1), 지난 공지 1건은 미노출(0) 예시 포함
--   author_id    : 관리자 email 로 조회 (FK 안전)
-- notice_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 관리자 유저(admin@naver.com)가 users 테이블에 있어야 합니다.

INSERT INTO notice (target_board, author_id, title, content, views, is_pinned, is_active, created_at, updated_at) VALUES
-- ===== 씨네광장 (CINESQUARE) =====
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '[필독] 씨네광장 커뮤니티 이용 규칙 안내', '씨네광장은 영화를 사랑하는 모두를 위한 공간입니다. 상호 존중을 바탕으로 건전한 대화 문화를 만들어 주세요. 규칙 위반 시 게시글이 제한될 수 있습니다.', 1820, 1, 1, '2026-05-01 09:00:00', NULL),
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '커뮤니티 매너를 지켜주세요', '욕설, 비방, 도배성 게시글은 삼가주시기 바랍니다. 서로 배려하는 댓글 문화에 동참해 주세요.', 640, 0, 1, '2026-05-10 10:30:00', NULL),
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '구인구직 게시글 작성 시 주의사항', '구인구직 카테고리에서는 허위 채용, 개인정보 과다 요구 게시글을 금지합니다. 안전한 거래를 위해 신중히 이용해 주세요.', 512, 0, 1, '2026-05-18 14:00:00', NULL),
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '스포일러 방지 캠페인 안내', '개봉 초기 작품의 결말이 포함된 게시글은 제목에 [스포] 표기를 부탁드립니다. 함께 즐거운 관람 문화를 만들어요.', 733, 0, 1, '2026-06-02 11:15:00', NULL),
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '불건전 게시글 신고 방법 안내', '부적절한 게시글은 우측 상단 신고 버튼으로 접수해 주세요. 접수된 내용은 검토 후 신속히 처리됩니다.', 298, 0, 1, '2026-06-20 16:40:00', '2026-06-21 09:10:00'),
('CINESQUARE', (SELECT user_id FROM users WHERE email='admin@naver.com'), '씨네광장 서비스 점검 안내 (완료)', '5월 정기 점검이 완료되었습니다. 이용에 불편을 드려 죄송합니다. (지난 공지)', 210, 0, 0, '2026-05-05 08:00:00', NULL),
-- ===== 추천게시판 (RECOMMEND) =====
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '[필독] 영화 추천 게시판 이용 안내', '추천 게시판은 인생영화와 숨은 명작을 공유하는 공간입니다. 간단한 추천 이유를 함께 적어주시면 다른 분들께 큰 도움이 됩니다.', 1540, 1, 1, '2026-05-02 09:30:00', NULL),
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '추천 글 작성 가이드', '영화 제목, 장르, 추천 포인트를 포함해 작성해 주세요. 스포일러가 있는 경우 반드시 별도 표기 부탁드립니다.', 468, 0, 1, '2026-05-12 13:20:00', NULL),
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '이달의 추천 영화 선정 안내', '매월 회원 추천글을 집계해 이달의 추천 영화를 선정합니다. 많은 추천과 참여 부탁드립니다.', 892, 0, 1, '2026-06-01 10:00:00', NULL),
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '추천 게시판 UI 개편 안내', '더 편리한 이용을 위해 추천 게시판 화면이 개편되었습니다. 정렬/검색 기능이 향상되었으니 많은 이용 바랍니다.', 355, 0, 1, '2026-06-15 15:00:00', '2026-06-16 10:00:00'),
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '이미지 저작권 사용 주의 안내', '포스터, 스틸컷 등 이미지 사용 시 저작권에 유의해 주세요. 권리 침해 게시물은 사전 통보 없이 삭제될 수 있습니다.', 274, 0, 1, '2026-06-25 11:45:00', NULL),
('RECOMMEND', (SELECT user_id FROM users WHERE email='admin@naver.com'), '추천왕 이벤트 안내', '한 달 동안 가장 많은 공감을 받은 추천글 작성자에게 예매권을 드립니다. 자세한 내용은 이벤트 페이지를 확인해 주세요.', 611, 0, 1, '2026-07-01 09:00:00', NULL);


-- ############################################################
-- # FILE: dump/dump_data/post.sql
-- ############################################################
-- 좌석추천(post) 더미 데이터 24건 — 일반 유저가 특정 상영관의 명당 좌석을 추천
--   multiplex_id / area_id / cinema_id / screen_id : 운영 DB의 실제 참조 데이터(cinema, screen)에서
--     일관된 조합으로 채움 (multiplex 1=CGV 2=메가박스 3=롯데시네마, area 11서울~19)
--     ※ post 테이블엔 이 4개 컬럼에 FK가 없지만, 실제 존재하는 조합만 사용
--   author_id : 일반 유저 email 로 조회 (FK 안전)
-- post_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO post (multiplex_id, area_id, cinema_id, screen_id, author_id, title, content, views, created_at, like_count) VALUES
-- ===== CGV =====
(1, 11, 'CG11001', 'CG11001001', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '[CGV 강남 1관] 명당 좌석 추천드려요', '강남 CGV 1관은 스크린이 큰 편이라 너무 앞자리는 목 아파요. H~J열 중앙(가운데 4~5칸)이 화면 꽉 차게 보이면서 편해요. 개인적으로 I열 중앙 강추합니다.', 421, '2026-06-03 14:10:00', 37),
(1, 11, 'CG11022', 'CG11022001', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), '[CGV 영등포 골드클래스] 좌석 후기', '영등포 골드클래스관은 좌석 간격이 넓어서 어디 앉아도 편한데, 그래도 중앙 뒤쪽이 사운드 밸런스가 제일 좋았어요. 리클라이너라 목받침 각도만 맞추면 최고입니다.', 356, '2026-06-05 20:30:00', 29),
(1, 12, 'CG12020', 'CG12020001', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), '[CGV 동탄역 1관] 어디 앉아야 좋을까요', '동탄역 1관 가시는 분들, 관이 아담해서 중간열이면 다 괜찮아요. 다만 맨 앞 3열은 스크린 올려다봐야 해서 비추천이에요. E~F열 중앙 추천!', 288, '2026-06-08 16:45:00', 21),
(1, 12, 'CG12048', 'CG12048001', (SELECT user_id FROM users WHERE email='park@ohseat.com'), '[CGV 판교 1관] 사운드 명당 공유', '판교 1관은 음향이 좋아서 액션 영화 보기 딱이에요. 스피커 위치상 정중앙보다 살짝 뒤(G열 정도) 중앙이 소리가 가장 균형 잡혀 들립니다.', 312, '2026-06-10 19:00:00', 26),
(1, 13, 'CG13006', 'CG13006001', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '[CGV 인천 10관] 좌석 추천', '인천 10관은 경사가 완만해서 앞사람 머리 걸릴 수 있어요. 되도록 뒤쪽 열(K열 이후) 중앙 잡으시면 시야 방해 없이 편하게 보실 수 있습니다.', 240, '2026-06-12 13:20:00', 18),
(1, 15, 'CG15016', 'CG15016001', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '[CGV 천안펜타포트 10관] 명당 어디?', '천안펜타포트 10관 넓은 편이에요. 스크린 대비 정중앙 뒤쪽이 몰입감 최고입니다. 커플이면 뒷줄 커플석 노려보세요.', 199, '2026-06-14 15:40:00', 15),
(1, 16, 'CG16001', 'CG16001001', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '[CGV 대구 10관] 좌석 후기 남겨요', '대구 CGV 10관 다녀왔어요. 화면이 커서 중앙보다 약간 뒤가 편하고, 통로 쪽 좌석은 오가는 사람 때문에 살짝 신경 쓰이더라고요. 중앙 블록 추천!', 267, '2026-06-16 18:10:00', 22),
(1, 17, 'CG17004', 'CG17004001', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '[CGV 서면삼정타워 1관] 명당 좌석', '서면삼정타워 1관은 관이 세로로 길어서 앞쪽은 정말 비추예요. 중앙에서 뒤로 2/3 지점 중앙이 화면·사운드 둘 다 좋습니다.', 233, '2026-06-18 21:05:00', 19),
(1, 19, 'CG19016', 'CG19016001', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '[CGV 전주고사 1관] 좌석 추천드려요', '전주고사 1관 자주 가는데, 정중앙 G~H열이 제일 명당이에요. 주말엔 빨리 매진되니 예매 서두르세요!', 178, '2026-06-20 14:30:00', 14),
-- ===== 메가박스 =====
(2, 11, 'MB11001', 'MB11001001', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), '[메가박스 강남 1관] 명당 자리 공유', '메가박스 강남 1관은 좌석 경사가 좋아서 앞사람 머리 안 걸려요. 그래도 중앙 F~H열이 가장 편안하게 몰입됩니다. 강추!', 334, '2026-06-04 17:20:00', 28),
(2, 11, 'MB11008', 'MB11008001', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), '[메가박스 상봉 1관] 좌석 후기', '상봉 1관 다녀왔어요. 스크린이 적당한 크기라 중간열이면 충분히 몰입돼요. 맨 뒤는 살짝 멀게 느껴지니 중앙 추천합니다.', 201, '2026-06-06 19:50:00', 16),
(2, 12, 'MB12009', 'MB12009001', (SELECT user_id FROM users WHERE email='park@ohseat.com'), '[메가박스 백석벨라시타 101호] 명당', '백석벨라시타 101호는 프리미엄관 느낌이라 좌석이 넓어요. 중앙 어디 앉아도 좋은데, 사운드는 뒤쪽 중앙이 제일 감쌌습니다.', 245, '2026-06-09 13:15:00', 20),
(2, 12, 'MB12016', 'MB12016001', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '[메가박스 수원인계 컴포트1관] 좌석 추천', '수원인계 컴포트관은 리클라이너라 진짜 편해요. 좌석 간격 넓어서 통로석도 부담 없고, 중앙 뒤쪽이 화면 밸런스 좋습니다.', 289, '2026-06-11 20:10:00', 24),
(2, 14, 'MB14002', 'MB14002001', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '[메가박스 속초점 1관] 좌석 후기', '여행 중 속초점 들렀는데 관이 크지 않아 어디든 무난해요. 정중앙 잡으면 화면 꽉 차게 잘 보입니다. 여행객에게 추천!', 156, '2026-06-13 16:00:00', 12),
(2, 15, 'MB15004', 'MB15004001', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '[메가박스 대전유성 1관] 명당 좌석', '대전유성 1관은 경사가 괜찮아서 중간열부터 시야 트여요. 개인적으로 G열 중앙이 스크린 정면으로 딱 맞아 제일 좋았습니다.', 187, '2026-06-15 18:40:00', 15),
(2, 17, 'MB17001', 'MB17001001', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '[메가박스 덕천 1관] 좌석 추천', '부산 덕천 1관 자주 이용해요. 앞쪽은 스크린 올려다봐야 해서 중앙 뒤쪽 추천드립니다. 사운드도 그쪽이 제일 좋아요.', 172, '2026-06-17 21:30:00', 13),
(2, 19, 'MB19008', 'MB19008001', (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '[메가박스 제주삼화 1관] 명당 공유', '제주삼화 1관 다녀왔어요. 관이 넓지 않아 중간만 잡아도 충분히 몰입됩니다. 제주 여행 중 영화 보실 분들 참고하세요!', 143, '2026-06-19 15:20:00', 11),
-- ===== 롯데시네마 =====
(3, 11, 'LC11017', 'LC11017001', (SELECT user_id FROM users WHERE email='kim@ohseat.com'), '[롯데시네마 월드타워 10관] 명당 좌석', '월드타워 10관은 스크린이 커서 앞자리는 부담돼요. 중앙에서 뒤로 약간(H~J열 중앙)이 화면 꽉 차면서 목도 안 아픕니다. 강추!', 398, '2026-06-05 20:00:00', 33),
(3, 11, 'LC11003', 'LC11003001', (SELECT user_id FROM users WHERE email='lee@ohseat.com'), '[롯데시네마 강동 10관] 좌석 후기', '강동 10관 다녀왔어요. 경사가 좋아서 중간열부터 시야 편하고, 사운드는 정중앙이 제일 밸런스 좋았습니다. 커플석도 뒤쪽에 있어요.', 276, '2026-06-07 19:15:00', 23),
(3, 12, 'LC12038', 'LC12038001', (SELECT user_id FROM users WHERE email='park@ohseat.com'), '[롯데시네마 평촌 1관] 명당 자리', '평촌 1관은 아담해서 중앙이면 다 좋아요. 다만 앞 2~3열은 화면이 너무 가까우니 피하세요. E~F열 중앙 추천합니다.', 214, '2026-06-10 17:50:00', 17),
(3, 14, 'LC14001', 'LC14001001', (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '[롯데시네마 강릉 1관] 좌석 추천', '강릉 1관 여행 중 들렀는데 깔끔하고 좋아요. 관 크기 적당해서 정중앙이면 화면·사운드 다 만족스럽습니다. 여행객 추천!', 168, '2026-06-13 16:30:00', 13),
(3, 15, 'LC15002', 'LC15002001', (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '[롯데시네마 대전 1관] 명당 좌석', '대전 1관은 스크린 대비 중앙 뒤쪽이 몰입감 좋아요. 통로석은 사람 오가서 살짝 신경 쓰이니 중앙 블록으로 잡으세요.', 191, '2026-06-15 18:20:00', 15),
(3, 17, 'LC17002', 'LC17002001', (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '[롯데시네마 동래관 1관] 좌석 후기', '부산 동래관 1관 다녀왔습니다. 경사 완만한 편이라 뒤쪽 중앙(J열 이후)이 앞사람 머리 안 걸리고 편해요. 참고하세요!', 179, '2026-06-17 20:40:00', 14),
(3, 19, 'LC19002', 'LC19002001', (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '[롯데시네마 광주 2관] 명당 좌석 공유', '광주 2관은 중앙 F~H열이 화면 정면으로 딱 맞아 제일 좋아요. 주말 저녁은 금방 차니 예매 서두르시길 추천드립니다.', 203, '2026-06-19 21:10:00', 16);


-- ############################################################
-- # FILE: dump/dump_data/post_comment.sql
-- ############################################################
-- 좌석추천(post) 댓글 더미 데이터 34건
--   post_id      → post.title 로 조회 (내가 넣은 제목은 유일하므로 1건 매핑)
--   commenter_id → users.email 로 조회 (FK 안전)
--   comment_id(auto_increment), created_at(default) 자동 처리
-- ※ 실행 전 insert-users.sql, dump/dump_data/post.sql 이 먼저 적용되어 있어야 합니다.

INSERT INTO post_comment (post_id, commenter_id, content, created_at) VALUES
-- [CGV 강남 1관] 명당 좌석 추천드려요
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  '오 마침 이번 주말에 강남 CGV 가는데 I열 노려볼게요! 감사합니다.', '2026-06-03 15:00:00'),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  '맞아요 앞자리 앉았다가 목 아파서 혼난 적 있어요 ㅠㅠ 꿀팁 감사해요.', '2026-06-03 18:20:00'),
((SELECT post_id FROM post WHERE title='[CGV 강남 1관] 명당 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='park@ohseat.com'), '중앙 4~5칸 기준 잡는 거 좋네요. 저장해둡니다!', '2026-06-04 09:10:00'),
-- [CGV 영등포 골드클래스] 좌석 후기
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '골드클래스 한 번 가보고 싶었는데 후기 도움 많이 됐어요.', '2026-06-06 10:30:00'),
((SELECT post_id FROM post WHERE title='[CGV 영등포 골드클래스] 좌석 후기'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '리클라이너 목받침 각도 팁 좋네요 ㅋㅋ 참고할게요.', '2026-06-06 21:00:00'),
-- [CGV 동탄역 1관] 어디 앉아야 좋을까요
((SELECT post_id FROM post WHERE title='[CGV 동탄역 1관] 어디 앉아야 좋을까요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '동탄역 1관 저도 자주 가요. E~F열 중앙 공감합니다!', '2026-06-08 17:30:00'),
((SELECT post_id FROM post WHERE title='[CGV 동탄역 1관] 어디 앉아야 좋을까요'), (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '앞 3열은 진짜 비추죠 ㅋㅋ 좋은 정보 고마워요.', '2026-06-09 11:15:00'),
-- [CGV 판교 1관] 사운드 명당 공유
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '액션 영화 볼 때 사운드 진짜 중요하죠. G열 참고할게요!', '2026-06-10 20:00:00'),
((SELECT post_id FROM post WHERE title='[CGV 판교 1관] 사운드 명당 공유'), (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  '판교 1관 음향 좋다는 거 완전 동의해요.', '2026-06-11 13:40:00'),
-- [CGV 인천 10관] 좌석 추천
((SELECT post_id FROM post WHERE title='[CGV 인천 10관] 좌석 추천'), (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  '경사 완만한 관은 앞사람 머리 신경 쓰이더라고요. K열 이후 좋네요.', '2026-06-12 14:00:00'),
((SELECT post_id FROM post WHERE title='[CGV 인천 10관] 좌석 추천'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '인천 10관 정보 감사합니다. 뒤쪽으로 예매할게요!', '2026-06-13 09:20:00'),
-- [CGV 천안펜타포트 10관] 명당 어디?
((SELECT post_id FROM post WHERE title='[CGV 천안펜타포트 10관] 명당 어디?'), (SELECT user_id FROM users WHERE email='park@ohseat.com'), '커플석 정보까지 ㅋㅋ 알찬 후기네요. 감사해요.', '2026-06-14 16:30:00'),
-- [CGV 대구 10관] 좌석 후기 남겨요
((SELECT post_id FROM post WHERE title='[CGV 대구 10관] 좌석 후기 남겨요'), (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '통로석은 확실히 사람 오가서 좀 그렇죠. 중앙 블록 참고할게요.', '2026-06-16 19:00:00'),
((SELECT post_id FROM post WHERE title='[CGV 대구 10관] 좌석 후기 남겨요'), (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '대구 CGV 갈 일 있었는데 딱 좋은 정보네요!', '2026-06-17 10:45:00'),
-- [CGV 서면삼정타워 1관] 명당 좌석
((SELECT post_id FROM post WHERE title='[CGV 서면삼정타워 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '세로로 긴 관이라 앞쪽 비추 공감해요. 2/3 지점 노려볼게요.', '2026-06-19 08:30:00'),
((SELECT post_id FROM post WHERE title='[CGV 서면삼정타워 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '서면 자주 가는데 유용하네요 ㅎㅎ 감사합니다.', '2026-06-19 21:50:00'),
-- [CGV 전주고사 1관] 좌석 추천드려요
((SELECT post_id FROM post WHERE title='[CGV 전주고사 1관] 좌석 추천드려요'), (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  '주말 매진 빠른 거 실화... 예매 서둘러야겠어요.', '2026-06-20 15:10:00'),
-- [메가박스 강남 1관] 명당 자리 공유
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  '메가박스 강남 경사 좋다는 거 동의해요. F~H열 좋죠!', '2026-06-04 18:00:00'),
((SELECT post_id FROM post WHERE title='[메가박스 강남 1관] 명당 자리 공유'), (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '앞사람 머리 안 걸리는 관이 최고예요 ㅋㅋ', '2026-06-05 12:30:00'),
-- [메가박스 상봉 1관] 좌석 후기
((SELECT post_id FROM post WHERE title='[메가박스 상봉 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='park@ohseat.com'), '상봉 자주 가는데 중간열이면 확실히 무난하더라고요.', '2026-06-07 10:00:00'),
-- [메가박스 백석벨라시타 101호] 명당
((SELECT post_id FROM post WHERE title='[메가박스 백석벨라시타 101호] 명당'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '벨라시타 프리미엄관 좋죠. 뒤쪽 중앙 사운드 팁 감사해요!', '2026-06-09 14:20:00'),
((SELECT post_id FROM post WHERE title='[메가박스 백석벨라시타 101호] 명당'), (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '좌석 넓은 관은 어디 앉아도 편해서 좋아요 ㅎㅎ', '2026-06-10 09:40:00'),
-- [메가박스 수원인계 컴포트1관] 좌석 추천
((SELECT post_id FROM post WHERE title='[메가박스 수원인계 컴포트1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='kim@ohseat.com'), '컴포트관 리클라이너 진짜 편하죠. 통로석 부담 없다는 거 공감!', '2026-06-11 21:00:00'),
((SELECT post_id FROM post WHERE title='[메가박스 수원인계 컴포트1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '수원인계 컴포트관 가보고 싶네요. 후기 감사합니다.', '2026-06-12 13:10:00'),
-- [메가박스 속초점 1관] 좌석 후기
((SELECT post_id FROM post WHERE title='[메가박스 속초점 1관] 좌석 후기'), (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '속초 여행 가면 들러야겠어요. 정중앙 참고할게요!', '2026-06-13 17:00:00'),
-- [메가박스 대전유성 1관] 명당 좌석
((SELECT post_id FROM post WHERE title='[메가박스 대전유성 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='jung@ohseat.com'), 'G열 중앙 스크린 정면이라는 거 유용하네요. 감사해요.', '2026-06-15 19:30:00'),
-- [메가박스 덕천 1관] 좌석 추천
((SELECT post_id FROM post WHERE title='[메가박스 덕천 1관] 좌석 추천'), (SELECT user_id FROM users WHERE email='yoon@ohseat.com'), '덕천 자주 가는데 중앙 뒤쪽 확실히 좋더라고요. 공감!', '2026-06-18 09:00:00'),
-- [메가박스 제주삼화 1관] 명당 공유
((SELECT post_id FROM post WHERE title='[메가박스 제주삼화 1관] 명당 공유'), (SELECT user_id FROM users WHERE email='lee@ohseat.com'),  '제주 여행 중에 영화 볼 일 있었는데 딱이네요. 감사합니다!', '2026-06-19 16:00:00'),
-- [롯데시네마 월드타워 10관] 명당 좌석
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='park@ohseat.com'), '월드타워 10관 스크린 크죠. H~J열 중앙 저도 강추해요!', '2026-06-05 21:00:00'),
((SELECT post_id FROM post WHERE title='[롯데시네마 월드타워 10관] 명당 좌석'), (SELECT user_id FROM users WHERE email='choi@ohseat.com'), '앞자리 앉으면 화면 다 못 담아요 ㅠㅠ 꿀팁 감사!', '2026-06-06 11:30:00'),
-- [롯데시네마 강동 10관] 좌석 후기
((SELECT post_id FROM post WHERE title='[롯데시네마 강동 10관] 좌석 후기'), (SELECT user_id FROM users WHERE email='hong@ohseat.com'), '강동 10관 커플석 뒤쪽에 있는 거 좋은 정보네요!', '2026-06-07 20:00:00'),
-- [롯데시네마 평촌 1관] 명당 자리
((SELECT post_id FROM post WHERE title='[롯데시네마 평촌 1관] 명당 자리'), (SELECT user_id FROM users WHERE email='kim@ohseat.com'),  '평촌 1관 아담하죠 ㅋㅋ E~F열 중앙 참고할게요.', '2026-06-10 18:30:00'),
-- [롯데시네마 대전 1관] 명당 좌석
((SELECT post_id FROM post WHERE title='[롯데시네마 대전 1관] 명당 좌석'), (SELECT user_id FROM users WHERE email='kang@ohseat.com'), '통로석 사람 오가는 거 신경 쓰이죠. 중앙 블록 노려볼게요!', '2026-06-16 10:00:00'),
-- [롯데시네마 광주 2관] 명당 좌석 공유
((SELECT post_id FROM post WHERE title='[롯데시네마 광주 2관] 명당 좌석 공유'), (SELECT user_id FROM users WHERE email='jung@ohseat.com'), '광주 2관 F~H열 중앙 참고할게요. 주말 예매 서둘러야겠네요!', '2026-06-20 09:30:00');


-- ############################################################
-- # FILE: dump/dump_data/post_like.sql
-- ############################################################
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

