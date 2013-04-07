--liquibase formatted sql

--changeset htmfilho:4
alter table event add parent char(32) null;
create index idx_parent_event on event (parent);
alter table event add constraint fk_parent_event foreign key (parent) references event(id) on delete set null;

create table venue (
    id        char(32)     not null,
    name      varchar(100) not null,
    address   varchar(255)     null,
    country   char(3)          null,
    province  char(32)         null,
    city      char(32)         null,
    latitude  varchar(15)      null,
    longitude varchar(15)      null,
    website   varchar(255)     null
) engine = innodb;

alter table venue add constraint pk_venue primary key (id);
create index idx_country_venue on venue (country);
create index idx_province_venue on venue (province);
create index idx_city_venue on venue (city);
alter table venue add constraint fk_country_venue foreign key (country) references country(acronym) on delete set null;
alter table venue add constraint fk_province_venue foreign key (province) references province(id) on delete set null;
alter table venue add constraint fk_city_venue foreign key (city) references city(id) on delete set null;

alter table event drop foreign key fk_event_venue;
alter table event drop foreign key fk_country_event;
alter table event drop foreign key fk_province_event;
alter table event drop foreign key fk_city_event;
alter table event drop index fk_city_event;
alter table event drop index fk_country_event;
alter table event drop index fk_event_venue;
alter table event drop index fk_province_event;

create table event_venue (
    id    char(32) not null,
    event char(32) not null,
    venue char(32) not null
) engine = innodb;

alter table event_venue add constraint pk_event_venue primary key (id);
create index idx_event_venue on event_venue (event);
create index idx_venue_event on event_venue (venue);
alter table event_venue add constraint fk_event_venue foreign key (event) references event(id) on delete cascade;
alter table event_venue add constraint fk_venue_event foreign key (venue) references venue(id) on delete cascade;

insert into venue (id, name, address, country, province, city, latitude, longitude, website) select e.id, p.name, e.address, e.country, e.province, e.city, e.latitude, e.longitude, p.url from event e left join partner p on p.id = e.venue;
insert into event_venue (id, event, venue) select id, id, venue from event;

alter table event drop column external;
alter table event drop column address;
alter table event drop column country;
alter table event drop column province;
alter table event drop column city;
alter table event drop column latitude;
alter table event drop column longitude;
alter table event drop column venue;

--changeset htmfilho:5
alter table user_account add organization varchar(100) null;

alter table speaker drop foreign key fk_event_speaker;
alter table speaker drop foreign key fk_session_speaker;
alter table speaker drop index fk_event_speaker;
alter table speaker drop index fk_session_speaker;
alter table speaker drop column session;
alter table speaker drop column event;

create table room (
    id          char(32)    not null,
    name        varchar(50) not null,
    venue       char(32)    not null,
    description text            null,
    capacity    numeric(4)      null
) engine = innodb;

alter table room add constraint pk_room primary key (id);
create index idx_room_venue on room (venue);
alter table room add constraint fk_room_venue foreign key (venue) references venue(id) on delete cascade;

create table track (
    id          char(32)     not null,
    name        varchar(50)  not null,
    event       char(32)     not null,
    color       char(6)          null,
    description text             null,
    topics      varchar(255)     null
) engine = innodb;

alter table track add constraint pk_track primary key (id);
create index idx_track_event on track (event);
alter table track add constraint fk_track_event foreign key (event) references event(id) on delete cascade;

alter table event_session rename to session;
alter table session change title name varchar(255) not null;
alter table session change abstract description text null;
alter table session change session_date start_date date null;
alter table session add column end_date date null;
alter table session drop column room;
alter table session add room char(32) null;
create index idx_room_session on session (room);
alter table session add constraint fk_room_session foreign key (room) references room (id) on delete set null;
alter table session add track char(32) null;
create index idx_track_session on session (track);
alter table session add constraint fk_track_session foreign key (track) references track (id) on delete set null;

--changeset htmfilho:6
alter table user_account drop organization;

alter table session add detailed_description text null;
alter table session add sponsorship_level varchar(20) null;
alter table session add approved tinyint(1) null;

alter table speaker add experience text null;
alter table speaker add organization varchar(100) null;

create table speaker_session (
    id      char(32) not null,
    speaker char(32) not null,
    session char(32) not null
) engine = innodb;

alter table speaker_session add constraint pk_speaker_session primary key (id);
create index idx_speaker_session on speaker_session (speaker);
create index idx_session_speaker on speaker_session (session);
alter table speaker_session add constraint fk_speaker_session foreign key (speaker) references speaker(id) on delete cascade;
alter table speaker_session add constraint fk_session_speaker foreign key (session) references session(id) on delete cascade;

alter table track drop topics;

alter table event_sponsor rename to sponsorship_event;
alter table sponsorship_event add sponsorship_level varchar(20) null;

create table attendee_session (
    id         char(32)    not null,
    attendee   char(32)    not null,
    session    char(32)    not null,
    bookmark   tinyint(1)      null,
    evaluation varchar(15)     null
) engine = innodb;

alter table attendee_session add constraint pk_attendee_session primary key (id);
create index idx_attendee_session on attendee_session (attendee);
create index idx_session_attendee on attendee_session (session);
alter table attendee_session add constraint fk_attendee_session foreign key (attendee) references attendee(id) on delete cascade;
alter table attendee_session add constraint fk_session_attendee foreign key (session) references session(id) on delete cascade;
