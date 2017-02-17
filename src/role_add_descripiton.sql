alter table role add column description varchar(255);
update role set description = "超级用户" where id = 1;
update role set description = "管理员" where id = 2;
update role set description = "普通用户" where id = 3;
update role set description = "销售" where id = 4;
update role set description = "产品管理" where id = 5;
update role set description = "财务管理" where id = 6;
alter table role modify description varchar(255) not null unique;