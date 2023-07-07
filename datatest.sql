insert into chuas 
( tenchua, ngaythanhlap, diachi,capnhat) value
('Chùa 1','2018-01-01 00:00:00','Địa chỉ 1','2018-01-01 00:00:00'),
('Chùa 2','2019-01-01 00:00:00','Địa chỉ 2','2018-01-01 00:00:00'),
('Chùa 3','2020-01-01 00:00:00','Địa chỉ 3','2018-01-01 00:00:00');

insert into kieuthanhviens
(`code`,tenkieu)value
('1','admin'), 
('2','Phật Tử'), 
('3','Thành viên 1');

insert into phattus
(ho,tendem,ten,phapdanh,anhchup,sodienthoai,email,matkhau,ngaysinh,ngayxuatgia,dahoantuc,ngayhoantuc,gioitinh,kieuthanhvienid,ngaycapnhat,chuaid)value
(null,null,'admin',null,null,null,'admin@gmail.com','123456',null,null,null,null,null,1,null,null),
('Hor','yu','1','HuyenM1','/anh1',0341111111,'yu1@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',1),
('Hor','yu','2','HuyenM2','/anh2',0341111112,'yu2@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',2),
('Hor','yu','3','HuyenM3','/anh3',0341111113,'yu3@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',3),
('Hor','yu','4','HuyenM4','/anh4',0341111114,'yu4@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',1),
('Hor','yu','5','HuyenM5','/anh5',0341111115,'yu5@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',2),
('Hor','yu','6','HuyenM6','/anh6',0341111116,'yu6@gmail.com','Abc_123456','1981-01-01','1999-01-01',0,null,1,2,'2018-01-01 00:00:00',3),
('Hor','yud','ha','HuyenM5','/anh7',0341111117,'yu7@gmail.com','Abc_123456','1981-01-01','1999-01-01',1,null,1,2,'2018-01-01 00:00:00',2),
('Hord','yu','ha','HuyenM5','/anh8',0341111118,'yu8@gmail.com','Abc_123456','1981-01-01','1999-01-01',1,null,1,2,'2018-01-01 00:00:00',2),
('Horq','yu','ha','HuyenM5','/anh9',0341111119,'yu9@gmail.com','Abc_123456','1981-01-01','1999-01-01',1,null,1,2,'2018-01-01 00:00:00',2);


UPDATE chuas
SET trutriid = '2'
WHERE id = 1;

UPDATE chuas
SET trutriid = '3'
WHERE id = 2;

UPDATE chuas
SET trutriid = '4'
WHERE id = 3;

insert into daotrangs
(noitochuc,sothanhvienthamgia,thoigiantochuc,noidung,daketthuc,nguoitrutriid)value
('Địa điểm 1',300,'2020-01-01 09:00:00','Nội dung 1',1,2),
('Địa điểm 2',100,'2022-08-01 09:00:00','Nội dung 2',1,2),
('Địa điểm 3',200,'2023-10-01 09:00:00','Nội dung 3',0,2);

insert into dondangkys
(phattuid,trangthaidon,ngayguidon,ngayxuly,nguoixulyid,daotrangid)value
(2,2,'1999-01-01','1999-01-01',1,1),
(3,2,'1999-01-01','1999-01-01',1,1),
(4,2,'1999-01-01','1999-01-01',1,2),
(5,2,'1999-01-01','1999-01-01',1,2),
(6,2,'1999-01-01','1999-01-01',1,3),
(7,2,'1999-01-01','1999-01-01',1,3);

insert into phattudaotrangs
(phattuid,daotrangid,dathamgia,lydokhongthamgia)value
(2,1,1,null),
(2,2,1,null),
(2,3,1,null),
(3,1,1,null),
(3,2,1,null),
(3,3,1,null),
(4,1,1,null),
(4,2,1,null),
(4,3,1,null),
(5,1,1,null),
(5,2,0,'Lý do 1'),
(5,3,1,null),
(6,1,0,'Lý do 1'),
(6,2,1,null),
(6,3,1,null),
(7,1,0,'Lý do 1'),
(7,2,0,'Lý do 2'),
(7,3,0,'Lý do 3');



