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
