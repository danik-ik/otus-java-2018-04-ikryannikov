create table if not exists users (
    id bigint auto_increment
    ,name varchar(256)
    ,bornDate datetime
    ,rating float
    ,primary key (id))