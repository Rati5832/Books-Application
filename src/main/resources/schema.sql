create table Customer (
      id int not null auto_increment,
      firstname varchar(255),
      lastname varchar(255),
      email varchar(255),
      password varchar(255),
      balance decimal(10,2) not null,
      role ENUM ('USER','ADMIN'),
      img bytea,
      primary key(id)
);

create table Book (
     id int not null auto_increment,
     title varchar(255),
     author varchar(255),
     cost decimal not null,
     primary key(id)
);

create table Token (
     id int not null auto_increment,
     token varchar(255),
     token_type ENUM('BEARER'),
     revoked boolean,
     expired boolean,
     customer_id int,
     primary key (id),
     foreign key (customer_id) references Customer(id)
);


create table book_customers(

     customer_id int not null,
     book_id int not null,
     primary key(customer_id, book_id)

);

alter table book_customers add constraint FK6iv5l89qmitedn5m2a71kta2t foreign key(customer_id) references Customer(id);
alter table book_customers add constraint FK6iv5l89qmitedn5m2a71kta7t foreign key(book_id) references Book(id);