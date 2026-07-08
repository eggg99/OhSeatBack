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
