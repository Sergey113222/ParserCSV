CREATE DATABASE parser_database;

create table parser
(
    id            bigint auto_increment               primary key,
    url           varchar(200)                        null,
    tesla         int                                 null ,
    musk          int                                 null,
    gigafactory   int                                 null,
    elon_musk     int                                 null,
    total         int                                 null
);