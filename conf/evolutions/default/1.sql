# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  id                        integer not null,
  begin_of_activity         timestamp,
  end_of_activity           timestamp,
  calories                  integer,
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
  hoursID                   integer,
  priceID                   integer,
  constraint pk_facility primary key (objectid))
;

create table open_hours_day (
  day_id                    integer not null,
  constraint pk_open_hours_day primary key (day_id))
;

create table open_period (
  op_id                     integer not null,
  open_hours_day_day_id     integer not null,
  begin                     varchar(255),
  end                       varchar(255),
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
  adult                     integer,
  child                     integer,
  youth                     integer,
  student                   integer,
  constraint pk_pricing primary key (price_id))
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

create sequence open_hours_day_seq;

create sequence open_period_seq;

create sequence opening_hours_seq;

create sequence pricing_seq;

create sequence sport_type_seq;

alter table facility add constraint fk_facility_openingHours_1 foreign key (hoursID) references opening_hours (hours_id) on delete restrict on update restrict;
create index ix_facility_openingHours_1 on facility (hoursID);
alter table facility add constraint fk_facility_prices_2 foreign key (priceID) references pricing (price_id) on delete restrict on update restrict;
create index ix_facility_prices_2 on facility (priceID);
alter table open_period add constraint fk_open_period_open_hours_day_3 foreign key (open_hours_day_day_id) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_open_period_open_hours_day_3 on open_period (open_hours_day_day_id);
alter table open_period add constraint fk_open_period_openingHours_4 foreign key (opening_hours_hours_id) references opening_hours (hours_id) on delete restrict on update restrict;
create index ix_open_period_openingHours_4 on open_period (opening_hours_hours_id);
alter table opening_hours add constraint fk_opening_hours_monday_5 foreign key (mondayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_monday_5 on opening_hours (mondayID);
alter table opening_hours add constraint fk_opening_hours_tuesday_6 foreign key (tuesdayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_tuesday_6 on opening_hours (tuesdayID);
alter table opening_hours add constraint fk_opening_hours_wednesday_7 foreign key (wednesdayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_wednesday_7 on opening_hours (wednesdayID);
alter table opening_hours add constraint fk_opening_hours_thursday_8 foreign key (thursdayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_thursday_8 on opening_hours (thursdayID);
alter table opening_hours add constraint fk_opening_hours_friday_9 foreign key (fridayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_friday_9 on opening_hours (fridayID);
alter table opening_hours add constraint fk_opening_hours_saturday_10 foreign key (saturdayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_saturday_10 on opening_hours (saturdayID);
alter table opening_hours add constraint fk_opening_hours_sunday_11 foreign key (sundayID) references open_hours_day (day_id) on delete restrict on update restrict;
create index ix_opening_hours_sunday_11 on opening_hours (sundayID);



alter table facility_sport_type add constraint fk_facility_sport_type_facili_01 foreign key (facility_objectid) references facility (objectid) on delete restrict on update restrict;

alter table facility_sport_type add constraint fk_facility_sport_type_sport__02 foreign key (sport_type_sport_id) references sport_type (sport_id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists activity;

drop table if exists athlete;

drop table if exists facility;

drop table if exists facility_sport_type;

drop table if exists open_hours_day;

drop table if exists open_period;

drop table if exists opening_hours;

drop table if exists pricing;

drop table if exists sport_type;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists activity_seq;

drop sequence if exists athlete_seq;

drop sequence if exists facility_seq;

drop sequence if exists open_hours_day_seq;

drop sequence if exists open_period_seq;

drop sequence if exists opening_hours_seq;

drop sequence if exists pricing_seq;

drop sequence if exists sport_type_seq;

