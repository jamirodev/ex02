-- 게시판 테이블
create table tbl_board(
    bno number primary key,
    title varchar2(200) not null,
    content varchar2(4000),
    writer varchar2(50) not null
        references tbl_user(u_id),
    regdate timestamp default sysdate,
    viewcnt number default 0,
    replycnt number default 0
);

alter table tbl_board
add replycnt number default 0;


-- 게시판 시퀀스
drop sequence seq_board_bno;
create sequence seq_board_bno;

select * from tbl_board
;
select count(*) from tbl_board;

truncate table tbl_board; -- tbl_board 데이터를 모두 삭제
drop sequence seq_board_bno; -- 시퀀스 삭제
create sequence seq_board_bno; -- 시퀀스 새로 생성

select rownum, bno, title from tbl_board
order by bno desc;

-- 제목 : 제목4 - where title like '%제목1%'
select * 
from (select rownum rnum, a.*
      from (select * from tbl_board
            order by bno desc) a)
where rnum between 1 and 10;

-- 댓글 테이블
-- 댓글이 어느글에 대한 댓글인지(bno - 외래키)
create table tbl_reply(
    rno number primary key,
    bno number references tbl_board(bno),
    replytext varchar2(1000) not null,
    replyer varchar2(50) not null,
    regdate timestamp default sysdate,
    updatedate timestamp
);

-- 댓글 시퀀스
create sequence seq_reply_rno;

select * from tbl_reply;


-- 첨부파일 테이블
create table tbl_attach(
    fullname varchar2(150) primary key,
    bno number not null references tbl_board(bno),
    regdate timestamp default sysdate
);

select * from  tbl_attach;

select b.bno, b.title, b.content, b.writer, a.fullname
from tbl_board b, tbl_attach a
where b.bno = a.bno;


-- 글번호(bno)
select * from tbl_attach
where bno = 521;

delete from tbl_attach
where fullname = ?

-- 사용자 테이블
create table tbl_user(
    u_id varchar2(50) primary key,
    u_pw varchar2(50) not null,
    u_name varchar2(100) not null,
    u_point number default 0 not null
);

-- 사용자 테스트 데이터 추가
insert into tbl_user(u_id, u_pw, u_name)
values ('user00', 'user00', 'IRON MAN');
insert into tbl_user(u_id, u_pw, u_name)
values ('user01', 'user01', 'CAPTAIN');
insert into tbl_user(u_id, u_pw, u_name)
values ('user02', 'user02', 'HULK');
insert into tbl_user(u_id, u_pw, u_name)
values ('user03', 'user03', 'Thor');
insert into tbl_user(u_id, u_pw, u_name)
values ('user10', 'user10', 'Quick Silver');

commit;

select * from tbl_user;

-- 메시지(쪽지) 테이블
create table tbl_message(
    m_id number primary key,
    targetid varchar2(50)
        references tbl_user(u_id),
    sender varchar2(50)
        references tbl_user(u_id),
    message varchar2(500) not null,
    opendate timestamp,
    senddate timestamp default sysdate
);


-- 메시지 시퀀스
create sequence seq_message_id;

-- 게시판 테이블에 댓글갯수 컬럼 추가
ALTER TABLE TBL_BOARD 
ADD (REPLYCNT NUMBER DEFAULT 0 NOT NULL);

-- 댓글 테이블 데이터 삭제
truncate table tbl_reply; -- rollback 안됨
-- delete from tbl_reply

-- 첨부파일 테이블 데이터 삭제
truncate table tbl_attach;

-- 게시판 테이블 삭제
drop table tbl_board;


-- 좋아요 테이블
create table tbl_like(
    u_id varchar2(50) not null references tbl_user(u_id),
    bno number not null references tbl_board(bno)
);

select * from tbl_like;

delete from tbl_like;
commit;

select count(*) from tbl_like
where u_id = 'user01'
and bno = 2;


