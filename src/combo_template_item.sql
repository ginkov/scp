create table prod_combo_template(
	id int unsigned primary key,
	combo_id int unsigned references prod_selling(id)
);