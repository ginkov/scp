alter table sales_order add column paystatus varchar(15) not null default "PAID";
alter table sales_order add column paydate date default null;