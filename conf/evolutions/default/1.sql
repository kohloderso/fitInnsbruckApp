# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  activity_id               integer not null,
  athlete_id                varchar(255) not null,
  begin_of_activity         varchar(255),
  end_of_activity           varchar(255),
  day                       timestamp,
  sportID                   integer,
  place_objectid            integer,
  calories                  float,
  constraint pk_activity primary key (activity_id))
;

create table athlete (
  id                        varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  birthday                  timestamp,
  height                    integer,
  weight                    integer,
  role_id                   bigint,
  constraint pk_athlete primary key (id))
;

create table facility (
  objectid                  integer not null,
  name                      varchar(255),
  address                   varchar(255),
  typeID                    integer,
  roof                      boolean,
  lat                       varchar(255),
  lon                       varchar(255),
  hoursID                   integer,
  priceID                   integer,
  constraint pk_facility primary key (objectid))
;

create table facility_type (
  type_id                   integer not null,
  description               varchar(255),
  constraint pk_facility_type primary key (type_id))
;

create table open_hours_day (
  day_id                    integer not null,
  constraint pk_open_hours_day primary key (day_id))
;

create table open_period (
  op_id                     integer not null,
  open_hours_day_day_id     integer not null,
  period_begin              varchar(255),
  period_end                varchar(255),
  opening_hours_hours_id    integer,
  constraint pk_open_period primary key (op_id))
;

create table opening_hours (
  hours_id                  integer not null,
  mondayID                  integer,
  tuesdayID                 integer,
  wednesdayID               integer,
  thursdayID                integer,
  fridayID                  integer,
  saturdayID                integer,
  sundayID                  integer,
  constraint pk_opening_hours primary key (hours_id))
;

create table pricing (
  price_id                  integer not null,
  adult                     float,
  child                     float,
  youth                     float,
  student                   float,
  constraint pk_pricing primary key (price_id))
;

create table security_role (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_security_role primary key (id))
;

create table sport_type (
  sport_id                  integer not null,
  description               varchar(255),
  constraint pk_sport_type primary key (sport_id))
;


create table facility_sport_type (
  facility_objectid              integer not null,
  sport_type_sport_id            integer not null,
  constraint pk_facility_sport_type primary key (facility_objectid, sport_type_sport_id))
;
create sequence activity_seq;

create sequence athlete_seq;

create sequence facility_seq;

create sequence facility_type_seq;

create sequence open_hours_day_seq;

create sequence open_period_seq;

create sequence opening_hours_seq;

create sequence pricing_seq;

create sequence security_role_seq;

create sequence sport_type_seq;

alter table activity add constraint fk_activity_athlete_1 foreign key (athlete_id) references athlete (id);
create index ix_activity_athlete_1 on activity (athlete_id);
alter table activity add constraint fk_activity_sport_2 foreign key (sportID) references sport_type (sport_id);
create index ix_activity_sport_2 on activity (sportID);
alter table activity add constraint fk_activity_place_3 foreign key (place_objectid) references facility (objectid);
create index ix_activity_place_3 on activity (place_objectid);
alter table athlete add constraint fk_athlete_role_4 foreign key (role_id) references security_role (id);
create index ix_athlete_role_4 on athlete (role_id);
alter table facility add constraint fk_facility_facilityType_5 foreign key (typeID) references facility_type (type_id);
create index ix_facility_facilityType_5 on facility (typeID);
alter table facility add constraint fk_facility_openingHours_6 foreign key (hoursID) references opening_hours (hours_id);
create index ix_facility_openingHours_6 on facility (hoursID);
alter table facility add constraint fk_facility_prices_7 foreign key (priceID) references pricing (price_id);
create index ix_facility_prices_7 on facility (priceID);
alter table open_period add constraint fk_open_period_open_hours_day_8 foreign key (open_hours_day_day_id) references open_hours_day (day_id);
create index ix_open_period_open_hours_day_8 on open_period (open_hours_day_day_id);
alter table open_period add constraint fk_open_period_openingHours_9 foreign key (opening_hours_hours_id) references opening_hours (hours_id);
create index ix_open_period_openingHours_9 on open_period (opening_hours_hours_id);
alter table opening_hours add constraint fk_opening_hours_monday_10 foreign key (mondayID) references open_hours_day (day_id);
create index ix_opening_hours_monday_10 on opening_hours (mondayID);
alter table opening_hours add constraint fk_opening_hours_tuesday_11 foreign key (tuesdayID) references open_hours_day (day_id);
create index ix_opening_hours_tuesday_11 on opening_hours (tuesdayID);
alter table opening_hours add constraint fk_opening_hours_wednesday_12 foreign key (wednesdayID) references open_hours_day (day_id);
create index ix_opening_hours_wednesday_12 on opening_hours (wednesdayID);
alter table opening_hours add constraint fk_opening_hours_thursday_13 foreign key (thursdayID) references open_hours_day (day_id);
create index ix_opening_hours_thursday_13 on opening_hours (thursdayID);
alter table opening_hours add constraint fk_opening_hours_friday_14 foreign key (fridayID) references open_hours_day (day_id);
create index ix_opening_hours_friday_14 on opening_hours (fridayID);
alter table opening_hours add constraint fk_opening_hours_saturday_15 foreign key (saturdayID) references open_hours_day (day_id);
create index ix_opening_hours_saturday_15 on opening_hours (saturdayID);
alter table opening_hours add constraint fk_opening_hours_sunday_16 foreign key (sundayID) references open_hours_day (day_id);
create index ix_opening_hours_sunday_16 on opening_hours (sundayID);



alter table facility_sport_type add constraint fk_facility_sport_type_facili_01 foreign key (facility_objectid) references facility (objectid);

alter table facility_sport_type add constraint fk_facility_sport_type_sport__02 foreign key (sport_type_sport_id) references sport_type (sport_id);

# --- !Downs

drop table if exists activity cascade;

drop table if exists athlete cascade;

drop table if exists facility cascade;

drop table if exists facility_sport_type cascade;

drop table if exists facility_type cascade;

drop table if exists open_hours_day cascade;

drop table if exists open_period cascade;

drop table if exists opening_hours cascade;

drop table if exists pricing cascade;

drop table if exists security_role cascade;

drop table if exists sport_type cascade;

drop sequence if exists activity_seq;

drop sequence if exists athlete_seq;

drop sequence if exists facility_seq;

drop sequence if exists facility_type_seq;

drop sequence if exists open_hours_day_seq;

drop sequence if exists open_period_seq;

drop sequence if exists opening_hours_seq;

drop sequence if exists pricing_seq;

drop sequence if exists security_role_seq;

drop sequence if exists sport_type_seq;

