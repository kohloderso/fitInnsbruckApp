# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table athlete (
  id                        varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  birthday                  timestamp,
  height                    integer,
  weight                    integer,
  constraint pk_athlete primary key (id))
;

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

create sequence athlete_seq;

create sequence facility_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists athlete;

drop table if exists facility;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists athlete_seq;

drop sequence if exists facility_seq;

