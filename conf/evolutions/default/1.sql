# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                        integer not null,
  begin_of_activity         timestamp,
  end_of_activity           timestamp,
  sport                     integer,
  calories                  integer,
  constraint ck_activity_sport check (sport in (0,1,2)),
  constraint pk_activity primary key (id))
;

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
  lat                       varchar(255),
  lon                       varchar(255),
  opID                      integer,
  priceID                   integer,
  constraint pk_facility primary key (objectid))
;

create table opening_hours (
  op_id                     integer not null,
  constraint pk_opening_hours primary key (op_id))
;

create table pricing (
  price_id                  integer not null,
  adult                     integer,
  child                     integer,
  youth                     integer,
  student                   integer,
  constraint pk_pricing primary key (price_id))
;

create sequence activity_seq;

create sequence athlete_seq;

create sequence facility_seq;

create sequence opening_hours_seq;

create sequence pricing_seq;

alter table facility add constraint fk_facility_openingHours_1 foreign key (opID) references opening_hours (op_id) on delete restrict on update restrict;
create index ix_facility_openingHours_1 on facility (opID);
alter table facility add constraint fk_facility_prices_2 foreign key (priceID) references pricing (price_id) on delete restrict on update restrict;
create index ix_facility_prices_2 on facility (priceID);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists activity;

drop table if exists athlete;

drop table if exists facility;

drop table if exists opening_hours;

drop table if exists pricing;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists activity_seq;

drop sequence if exists athlete_seq;

drop sequence if exists facility_seq;

drop sequence if exists opening_hours_seq;

drop sequence if exists pricing_seq;

