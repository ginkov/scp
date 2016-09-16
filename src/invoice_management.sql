create table invoice(
	id int unsigned auto_increment primary key,
	sn varchar(255) not null unique,
	description varchar(255),
	issue_date date,
	issuer varchar(255),
	amount decimal(10,3) not null default 0.0,
	is_original tinyint unsigned not null default 0,
	expense_type varchar(255) not null default 'EXPENSE'
);

create table expense_invoice_join(
	expense_id int unsigned,
	invoice_id int unsigned,
	primary key(expense_id, invoice_id),
	foreign key(expense_id) references exp_record(id),
	foreign key(invoice_id) references invoice(id)
);