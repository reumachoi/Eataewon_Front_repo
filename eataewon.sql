drop table eataewonLike;
drop table eataewonScrap;
drop table eataewonBbs;
drop table eataewonMember;

create table eataewonMember(
    id varchar(50) not null,
    name varchar(50) not null,
    pwd varchar(50) not null,
    email varchar(100) not null,
    nickname varchar(50) not null,
    profilpic varchar(1000),
    likepoint int,
    profilmsg varchar(500),
    del int,
    constraint pk_eataewonMember primary key(id)
);

create sequence bbsseq
start with 1
increment by 1;



create table eataewonBbs(
    id varchar(50) not null,
    nickname varchar(50),
    seq int ,
    title varchar(500) not null,
    content varchar(4000) not null,
    picture varchar(4000),
    hashtag varchar(500),
    wdate timestamp,
    shopname varchar(100),
    address varchar(500),
    shopphnum varchar(50),
    shopurl varchar(100),
    latitude number(12,8),
    longitude number(12,8),
    readcnt int,
    likecnt int,    
    constraint pk_eataewonBbs primary key(seq)
);

ALTER TABLE eataewonBbs ADD CONSTRAINT FK_id FOREIGN KEY(id) REFERENCES eataewonMember(id);



create table eataewonScrap(
    id varchar(50) not null,
    bbsseq int not null,
    seq int,
    constraint pk_scrap_seq primary key(seq)
);

create sequence scrapseq
start with 1
increment by 1;

ALTER TABLE eataewonScrap ADD CONSTRAINT FK_scrapId FOREIGN KEY(id) REFERENCES eataewonMember(id);
ALTER TABLE eataewonScrap ADD CONSTRAINT FK_scrapSeq FOREIGN KEY(bbsseq) REFERENCES eataewonBbs(seq);



create table eataewonLike(
    id varchar(50) not null,
    bbsseq int not null,
    seq int,
    constraint pk_like_seq primary key(seq)
);

create sequence likeseq
start with 1
increment by 1;

ALTER TABLE eataewonLike ADD CONSTRAINT FK_likeId FOREIGN KEY(id) REFERENCES eataewonMember(id);
ALTER TABLE eataewonLike ADD CONSTRAINT FK_likeSeq FOREIGN KEY(bbsseq) REFERENCES eataewonBbs(seq);

Insert into eataewonMember (name, id, pwd, email, nickname,profilpic,likepoint,profilmsg)
values('name','id','pwd','email','nickname','/storage/emulated/0/Download/다운로드.jpeg-1.jpg',1,'profilmsg');

Insert into eataewonMember (name, id, pwd, email, nickname,profilpic,likepoint,profilmsg)
values('aaa','aaa','111','aa@aa.com','misik','/storage/emulated/0/Download/다운로드.jpeg-1.jpg',1,'profilmsg');

Insert into eataewonBbs (seq, id, title, content, picture, hashtag, wdate, shopname, address, latitude, longitude, readcnt, likecnt)
values(bbsseq.NEXTVAL, 'id', 'title', 'content', '/storage/emulated/0/Download/다운로드.jpeg-4.jpg /storage/emulated/0/Download/다운로드.jpeg-1.jpg /storage/emulated/0/Pictures/4bcbc84e-5095-45c4-a255-b6a4dede152b.jpeg /storage/emulated/0/Pictures/image_4938315241509144191037.jpg /storage/emulated/0/Download/다운로드.jpeg-4.jpg /storage/emulated/0/Download/다운로드.jpeg-1.jpg /storage/emulated/0/Pictures/4bcbc84e-5095-45c4-a255-b6a4dede152b.jpeg /storage/emulated/0/Pictures/image_4938315241509144191037.jpg', 'hashtag', SYSDATE, 'mulcam', 'seoul', 37.503624 , 127.042391 , 1, 1);



SELECT SEQ, nickname, ID, TITLE, CONTENT, HASHTAG, WDATE, SHOPNAME, ADDRESS, READCNT, LIKECNT, TESTURL
        FROM EATAEWONBBS;
        
SELECT a.SEQ, a.SHOPNAME, a.ADDRESS, a.TESTURL, a.NICKNAME, a.CONTENT, a.HASHTAG, a.WDATE, b.PROFILPIC
        FROM EATAEWONBBS a, EATAEWONMEMBER b
        WHERE a.ID = b.ID
        ORDER BY WDATE DESC;

SELECT SEQ, NICKNAME, PICTURE, TITLE, READCNT, LIKECNT
        FROM EATAEWONBBS
        ORDER BY READCNT DESC;

COMMIT;