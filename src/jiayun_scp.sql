drop database if exists jiayun_scp;

create database jiayun_scp 
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
use jiayun_scp;


create table org_type (
	id int unsigned auto_increment primary key,
	name varchar(255) unique
);
insert into org_type values(1, '个人');
insert into org_type values(2, '企业');
insert into org_type values(3, '事业单位');

create table customer_address(
	id int unsigned auto_increment primary key,
	province varchar(255),
	city varchar(255),
	street varchar(255),
	post_code int unsigned
);
insert into customer_address values
(1, '北京市', '海淀区', '中关村东路40号 理想大厦 F-402', 100075),
(2, '天津市', '河西区', '缘小区8号楼5单元402', 210035);

create table customers(
	id int unsigned auto_increment primary key,
	org_type_id int unsigned,
	name varchar(255) not null default '匿名',
	contact_name varchar(255),
	reg_date date,
	-- list of addresses
	phone_1 varchar(255) default '',
	phone_2 varchar(255) default '',
	phone_3 varchar(255) default '',
	phone_4 varchar(255) default '',
	description varchar(1024),
	name_and_phone varchar(255) not null unique,
	foreign key(org_type_id) references org_type(id)
);

insert into customers values
(1, 1, '陈旗', '陈旗', '20151201', '13801117256',NULL,NULL,NULL,NULL, "陈旗_13801117256"),
(2, 1, '张明', '张明', '20160301', '13701315979',NULL,NULL,NULL,NULL, "张明_13701315979");


create table customer_address_join(
	customer_id int unsigned,
	address_id int unsigned,
	primary key (customer_id, address_id),
	foreign key(customer_id) references customers(id),
	foreign key(address_id) references customer_address(id)
);
insert into customer_address_join values
(1,1),(2,2);

create table prod_cat1(
	id int unsigned auto_increment primary key,
	name varchar(255) not null unique,
	description varchar(255)
);
insert into prod_cat1 values
	(1, '套装', null),
	(2, '植物', null),
	(3, '容器', null),
	(4, '附件', null),
	(5, '土', null),
	(6, '服务', null);


create table prod_cat2(
	id int unsigned auto_increment primary key,
	name varchar(255) not null unique,
	cat1_id int unsigned not null,
	description varchar(255),
	foreign key(cat1_id) references prod_cat1(id)
);
insert into prod_cat2 values
	(1, '套装', 1, null),
	(null, '花卉', 2,  null),(null,'赏叶', 2, null),(null,'果菜',2, null),
	(null, '桌面', 3,  null),(null, '落地', 3, null),
	(null, '附件', 4,  null),
	(null, '配方土',  5,  null),
	(null, '服务', 6, null);

create table prod_part(
	id int unsigned auto_increment primary key,
	name varchar(255) unique not null,
	description varchar(255),
	listprice decimal(23,2) not null default 0.0,
	cat2_id int unsigned not null,
	foreign key(cat2_id) references prod_cat2(id)
);

-- insert into prod_part values
--	(1, '杜鹃花', null, 100.0, 1),
--	(2, '第一代木盒', null, 100.0, 4),
--	(3, '通用配方土', null, 20.0, 6);

create table prod_selling(
	id int unsigned auto_increment primary key,
	name varchar(255) not null,
	description varchar(255),
	listprice decimal(23,2) not null default 0.0,
	online_date date,
	offline_date date,
	cat2_id int unsigned not null,
	foreign key(cat2_id) references prod_cat2(id)
);
-- insert into prod_selling values
--	(1, '杜鹃花一代', null, 250.0, 20150101, 20991231,8),
--	(2, '杜鹃花', null, 100.0, 20150101, 20991231,1),
--	(3, '第一代木盒', null, 100, 20150101,20161231,4),
--	(4, '通用配方土', null, 20.0, 20150101,20991231,6);
	
create table prod_part_item(
	id int unsigned auto_increment primary key,
	part_id int unsigned not null,
	quantity int unsigned not null default 1,
	selling_id int unsigned,
	foreign key(part_id) references prod_part(id),
	foreign key(selling_id) references prod_selling(id)
);
-- insert into prod_part_item values
--	(1, 1, 1, 1),
--	(2, 2, 1, 1),
--	(3, 3, 1, 1),
--	(4, 1, 1, 2),
--	(5, 2, 1, 3),
--	(6, 3, 1, 4);

create table user_sale_type (
	id int unsigned auto_increment primary key,
	name varchar(255) unique not null,
	description varchar(255)
);
insert into user_sale_type values
(1, '天使志愿者', null),
(2, '天使用户', null),
(3, '普通用户', null),
(4, '大客户', null);


create table sales_item(
	id int unsigned auto_increment primary key,
	prod_selling_id int unsigned not null,
	quantity int unsigned,
	list_price decimal(10,2) not null,
	discount_price decimal(10,2) not null,
	discount decimal(4,3) not null,
	total_price decimal(10,2) not null,
	description varchar(255),
	foreign key(prod_selling_id) references prod_selling(id)
);

create table service_record (
	id int unsigned auto_increment primary key,
	time date,
	type varchar(255) not null,
	service_person varchar(255),
	record varchar(1024) not null
);

create table sales_order(
	id int unsigned auto_increment primary key,
	sn varchar(255) not null,
	customer_id int unsigned not null,
	user_sale_type_id int unsigned not null,
	prod_summary varchar(255),
	order_date date not null,
	deliver_date date,
	list_price decimal(10,2) not null,
	discount_price decimal(10,2) not null,
	discount decimal(4,3) not null,
	description varchar(1024),
	channel_name varchar(255),
	-- sales_item
	-- service_record
	foreign key (customer_id) references customers(id),
	foreign key (user_sale_type_id) references user_sale_type(id)
);

create table order_item_join(
	order_id int unsigned not null,
	sales_item_id int unsigned not null,
	primary key(order_id, sales_item_id),
	foreign key(order_id) references sales_order(id),
	foreign key(sales_item_id) references sales_item(id)
);

create table order_service_join(
	order_id int unsigned not null,
	service_record_id int unsigned not null,
	primary key(order_id, service_record_id),
	foreign key(order_id) references sales_order(id),
	foreign key(service_record_id) references service_record(id)
);

create table year_order(
	year int unsigned primary key,
	order_number int unsigned not null
);

create table user(
	id int unsigned auto_increment primary key,
	name varchar(255) not null unique,
	description varchar(255) not null unique, 
	pass_md5 varchar(255)
);

create table role(
	id int unsigned auto_increment primary key,
	role varchar(255) not null unique
);

create table user_role_join(
	user_id int unsigned not null,
	role_id int unsigned not null,
	primary key(user_id, role_id),
	foreign key(user_id) references user(id),
	foreign key(role_id) references role(id)
);

insert into user values(1, 'super', 'super', '$2a$10$FZmhwo4WR3Mt0Eif/a/8H.3CuAnNOUb36CWrKIBpixdeZ.x3knGXG');
insert into user values(2, 'zhs', '周帅', '$2a$10$FN.YE8JM66/Awp8AjKuV5eA2m5FpIMYQdAjmNKVM3totzdGCErC/u');
insert into user values(3, 'dsy', '杜淑媛', '$2a$10$FN.YE8JM66/Awp8AjKuV5eA2m5FpIMYQdAjmNKVM3totzdGCErC/u');
insert into user values(4, 'yc', '杨春', '$2a$10$FN.YE8JM66/Awp8AjKuV5eA2m5FpIMYQdAjmNKVM3totzdGCErC/u');
insert into user values(5, 'yx', '尹欣', '$2a$10$FN.YE8JM66/Awp8AjKuV5eA2m5FpIMYQdAjmNKVM3totzdGCErC/u');
insert into role values(1, 'ROLE_SUPER'),(2,'ROLE_ADMIN'),(3,'ROLE_USER');
insert into role values(4, 'ROLE_SALES'),(5,'ROLE_PRODUCT'),(6,'ROLE_FINANCE');
insert into user_role_join values(1,1),(1,2),(1,3);
insert into user_role_join values(2,2),(2,3);
insert into user_role_join values(3,2),(3,3);
insert into user_role_join values(4,2),(4,3);
insert into user_role_join values(5,2),(5,3);

insert into year_order values
(2015,1024),(2016,1024),(2017,1024),(2018,1024),(2019,1024),
(2020,1024),(2021,1024),(2022,1024),(2023,1024),(2024,1024),(2025,1024),(2026,1024),(2027,1024),(2028,1024),(2029,1024),
(2030,1024),(2031,1024),(2032,1024),(2033,1024),(2034,1024),(2035,1024),(2036,1024),(2037,1024),(2038,1024),(2039,1024),
(2040,1024),(2041,1024),(2042,1024),(2043,1024),(2044,1024),(2045,1024),(2046,1024),(2047,1024),(2048,1024),(2049,1024),
(2050,1024),(2051,1024),(2052,1024),(2053,1024),(2054,1024),(2055,1024),(2056,1024),(2057,1024),(2058,1024),(2059,1024),
(2060,1024),(2061,1024),(2062,1024),(2063,1024),(2064,1024),(2065,1024),(2066,1024),(2067,1024),(2068,1024),(2069,1024),
(2070,1024),(2071,1024),(2072,1024),(2073,1024),(2074,1024),(2075,1024),(2076,1024),(2077,1024),(2078,1024),(2079,1024),
(2080,1024),(2081,1024),(2082,1024),(2083,1024),(2084,1024),(2085,1024),(2086,1024),(2087,1024),(2088,1024),(2089,1024),
(2090,1024),(2091,1024),(2092,1024),(2093,1024),(2094,1024),(2095,1024),(2096,1024),(2097,1024),(2098,1024),(2099,1024),
(2100,1024),(2101,1024),(2102,1024),(2103,1024),(2104,1024),(2105,1024),(2106,1024),(2107,1024),(2108,1024),(2109,1024),
(2110,1024),(2111,1024),(2112,1024),(2113,1024),(2114,1024),(2115,1024),(2116,1024),(2117,1024),(2118,1024),(2119,1024);

create table exp_type1(
	id int unsigned auto_increment primary key,
	name varchar(255) not null unique,
	description varchar(255)
);

insert into exp_type1 values
(1, '原材料', ''),
(2, '交通费', ''),
(3, '办公费', ''),
(4, '其它', '');

create table exp_type2(
	id int unsigned auto_increment primary key,
	t1_id int unsigned not null,
	name varchar(255) not null unique,
	description varchar(255),
	foreign key (t1_id) references exp_type1(id)
);
insert into exp_type2 values
(null, 1, '拼栽辅料', ''),
(null, 2, '火车票', ''),
(null, 2, '出租车', ''),
(null, 3, '文具', ''),
(null, 3, '印刷', ''),
(null, 3, '财务运行', ''),
(null, 4, '其它', '');

create table exp_record(
	id int unsigned auto_increment primary key,
	sn varchar(255) not null unique,
	exp_date date not null,
	supplier varchar(255) ,
	invoice_num varchar(255) ,
	t2_id int unsigned not null,
	exp_name varchar(255),
	summary varchar(1024) ,
	amount decimal(12,3) not null default 0.0,
	staff_id int unsigned not null,
	owner_id int unsigned not null,
	foreign key(t2_id) references exp_type2(id),
	foreign key(staff_id) references user(id),
	foreign key(owner_id) references user(id)
);

create table year_expense(
	year int unsigned primary key,
	exp_num int unsigned not null
);

insert into year_expense values
(2015,0),(2016,0),(2017,0),(2018,0),(2019,0),
(2020,0),(2021,0),(2022,0),(2023,0),(2024,0),(2025,0),(2026,0),(2027,0),(2028,0),(2029,0),
(2030,0),(2031,0),(2032,0),(2033,0),(2034,0),(2035,0),(2036,0),(2037,0),(2038,0),(2039,0),
(2040,0),(2041,0),(2042,0),(2043,0),(2044,0),(2045,0),(2046,0),(2047,0),(2048,0),(2049,0),
(2050,0),(2051,0),(2052,0),(2053,0),(2054,0),(2055,0),(2056,0),(2057,0),(2058,0),(2059,0),
(2060,0),(2061,0),(2062,0),(2063,0),(2064,0),(2065,0),(2066,0),(2067,0),(2068,0),(2069,0),
(2070,0),(2071,0),(2072,0),(2073,0),(2074,0),(2075,0),(2076,0),(2077,0),(2078,0),(2079,0),
(2080,0),(2081,0),(2082,0),(2083,0),(2084,0),(2085,0),(2086,0),(2087,0),(2088,0),(2089,0),
(2090,0),(2091,0),(2092,0),(2093,0),(2094,0),(2095,0),(2096,0),(2097,0),(2098,0),(2099,0),
(2100,0),(2101,0),(2102,0),(2103,0),(2104,0),(2105,0),(2106,0),(2107,0),(2108,0),(2109,0),
(2110,0),(2111,0),(2112,0),(2113,0),(2114,0),(2115,0),(2116,0),(2117,0),(2118,0),(2119,0);
