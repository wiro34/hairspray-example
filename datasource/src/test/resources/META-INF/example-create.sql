create table user (id int not null auto_increment, first_name varchar(255) not null, last_name varchar(255) not null, email varchar(255), age int, sex char(5), created_at timestamp not null, primary key (id));
create table post (id int not null auto_increment, author_id int not null, subject varchar(255) not null, content text, created_at timestamp not null, primary key (id));
