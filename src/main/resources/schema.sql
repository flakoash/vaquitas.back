create table Groups (
    id varchar(24) primary key,
    name varchar(50) not null,
    icon varchar(200) not null,
    last_Settle_Up BIGINT(19) not null,
    created_At BIGINT(19) not null
);

create table User (
    id varchar(24) primary key,
    name varchar(50) not null,
    username varchar(50) not null,
    password varchar(25) not null,
    phone_Number varchar(25) not null,
    photo varchar(200) not null
);

create table User_Groups (
    user_id varchar(24) not null,
    group_id varchar(24) not null
);
alter table User_Groups
    add foreign key (user_id) references User(id);
alter table User_Groups
    add foreign key (group_id) references Groups(id);


create table Transactions (
    id varchar(24) primary key,
    title varchar (150) not null,
    description varchar (300) null,
    amount DECIMAL(19,2) not null,
    user_id varchar(24) not null,
    group_id varchar(24) not null,
    created_At BIGINT(19) not null
);
alter table Transactions
    add foreign key (user_id) references User(id);
alter table Transactions
    add foreign key (group_id) references Groups(id);

create table Involved (
    id varchar(24) primary key,
    user_id varchar(24) not null,
    transaction_id varchar(24) not null,
    amount DECIMAL(19,2) not null,
    created_At BIGINT(19) not null
);
alter table Involved
    add foreign key (user_id) references User(id);
alter table Involved
    add foreign key (transaction_id) references Transactions(id);