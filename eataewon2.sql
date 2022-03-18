
create table eataewonMember(
	seq int,
    name varchar(20) not null,
    id varchar(30),
    pwd varchar(30) not null,
    email varchar(50) not null,
    nickName varchar(50) not null,
    profilPic varchar(500) not null,
    likePoint int,
    profilMsg varchar(500),
    constraint pk_eataewonMember primary key(seq)
);

create sequence seq_num
start with 1
increment by 1;

create table eataewonBbs(
    seq int,
    title varchar(500) not null,
    content varchar(4000) not null,
    hashtag varchar(500),
    wdate timestamp,
    address varchar(500),
    latitude number(12,8),
    longtitude number(12,8),
    readcnt int,
    likecnt int
);

ALTER TABLE eataewonBbs ADD CONSTRAINT FK_seq FOREIGN KEY(seq) REFERENCES eataewonMember(seq);

Insert into eataewonMember (seq, name, id, pwd, email, nickname,profilpic,likepoint,profilmsg)
values(seq_num.NEXTVAL, 'name','id','pwd','email','nickname','profilpic',1,'profilmsg');

