create database if not exists loan_db;
use loan_db;

create table if not exists `user`(
usr_id int auto_increment not null,
pcode varchar(8) not null,
permission int not null,
primary key(usr_id)
);

create table if not exists loan_officer(
officer_id_no int not null,
officer_name varchar(30),
primary key(officer_id_no),
foreign key(officer_id_no) references `user`(usr_id) on delete cascade on update cascade
);

create table if not exists customer(
cust_id_no int not null,
cust_name varchar(30),
primary key(cust_id_no),
foreign key(cust_id_no) references `user`(usr_id) on delete cascade on update cascade
);

create table if not exists loan(
loan_no int auto_increment not null,
loan_status_id int,
officer_id_no int,
cust_id_no int,
loan_amt decimal(9,2),
primary key(loan_no),
foreign key(officer_id_no) references `user`(usr_id) on delete cascade on update cascade,
foreign key(cust_id_no) references `user`(usr_id) on delete cascade on update cascade
);

create table if not exists repayment(
loan_no int,
amt_repaid decimal(9,2),
date_repaid date,
primary key(loan_no),
foreign key(loan_no) references loan(loan_no) on delete cascade on update cascade
);


/* Sample Data */
/* =========== */

/* Users */

insert into `user`(pcode,permission) values("12345678",2);
insert into `user`(pcode,permission) values("87654321",2);
insert into `user`(pcode,permission) values("01010101",1);

/* Customers */

insert into customer(cust_id_no, cust_name) values(1,"Alton Thompson");
insert into customer(cust_id_no, cust_name) values(2,"Peter Jackson");

/* Loan  Officer */

insert into loan_officer(officer_id_no, officer_name) values(3,"Winnie ThePoo");

/* Loan */

insert into loan(loan_status_id,officer_id_no,cust_id_no,loan_amt) values(1,3,2,500000.00);
insert into loan(loan_status_id,officer_id_no,cust_id_no,loan_amt) values(1,3,1,3000000.00);
insert into loan(loan_status_id,officer_id_no,cust_id_no,loan_amt) values(2,3,2,150000.00);
insert into loan(loan_status_id,officer_id_no,cust_id_no,loan_amt) values(1,3,1,100000.00);

/* Repayment */

insert into repayment(loan_no,amt_repaid,date_repaid) values(2,1500000.00,'2015-08-11');
