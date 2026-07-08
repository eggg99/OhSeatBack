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
