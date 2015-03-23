# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table facility (
  objectid                  integer not null,
  name                      varchar(255),
  address                   varchar(255),
  type                      varchar(255),
  description               varchar(255),
  lat                       varchar(255),
  lon                       varchar(255),
  constraint pk_facility primary key (objectid))
;

create table user (
  id                        varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  birthday                  timestamp,
  height                    integer,
  weight                    integer,
  constraint pk_user primary key (id))
;

create sequence facility_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists facility;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists facility_seq;

drop sequence if exists user_seq;

