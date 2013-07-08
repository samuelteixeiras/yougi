--liquibase formatted sql

--changeset htmfilho:3
alter table user_account drop column birth_date;
alter table user_account drop column postal_code;