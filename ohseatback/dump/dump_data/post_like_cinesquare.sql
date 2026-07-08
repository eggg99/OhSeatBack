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
