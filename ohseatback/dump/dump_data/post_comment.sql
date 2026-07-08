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
