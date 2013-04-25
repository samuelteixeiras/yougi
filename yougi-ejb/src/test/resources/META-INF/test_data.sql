select * from event;
select * from session;
select * from speaker;
select * from speaker_session;
select * from user_account;

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KIJLOPMQKSLDKNCKIKZJSHDNCJDKIKLK','University','2013-11-10','2013-11-11','09:30','16:30','00FDA9744A64448D9CD819658439F336');

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KIJLOPMQKSLDKNCKIKZJSHDNCJDKIKIJ','Tools in Action','2013-11-10','2013-11-11','16:45','18:35','00FDA9744A64448D9CD819658439F336');

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KIJLOPMQKUJHKNCKIKZJSHDNCJDKIKIJ','Hands-on Lab','2013-11-10','2013-11-13','09:30','16:30','00FDA9744A64448D9CD819658439F336');

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KIJLOPMQKUJHKNCKIKZJSQSGDJDKIKIJ','BOFs','2013-11-10','2013-11-14','19:00','22:00','00FDA9744A64448D9CD819658439F336');

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KIJLOPMQKUJHKNCKIKZJSQSGPWSDIKIJ','Quickies','2013-11-13','2013-11-14','13:10','13:50','00FDA9744A64448D9CD819658439F336');

insert into event (id, name, start_date, end_date, start_time, end_time, parent)
values ('KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','Conference','2013-11-12','2013-11-14','09:30','18:50','00FDA9744A64448D9CD819658439F336');

insert into session (id, event, name, start_date, end_date, start_time, end_time)
values ('AQWXSZEDCVFRTGBNHYUJKIOLMPOIUYTR','KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','What is New in JavaFX','2013-11-12','2013-11-12','12:00','13:00');

insert into session (id, event, name, start_date, end_date, start_time, end_time)
values ('BRVZSZEDCVFRTGBNHYUJKIOLMPOIUYTR','KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','Welcome & Announcements','2013-11-12','2013-11-12','9:30','10:00');

insert into session (id, event, name, start_date, end_date, start_time, end_time)
values ('BRVZTAFECVFRTGBNHYUJKIOLMPOIUYTR','KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','Make the Future Java','2013-11-12','2013-11-12','10:00','10:50');

insert into session (id, event, name, start_date, end_date, start_time, end_time)
values ('BRVZTAFEDYGSTGBNHYUJKIOLMPOIUYTR','KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','When Geek Leaks','2013-11-12','2013-11-12','10:50','11:40');

insert into session (id, event, name, start_date, end_date, start_time, end_time, room)
values ('AQWOLQSDCVFRTGBNHYUJKIOLMPOIUYTR','KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ','7 Things: How To Make Good Teams Great','2013-11-12','2013-11-12','12:00','13:00', 'QBHCGDHBNJUHZISKJSHNBCJKIDHJ9KQS');


insert into speaker (id, user_account, short_cv, experience, organization)
values ('KIOLJHGYUJHNBVGTHIJSVCBXGTYHJUHG','45495DAC88E441A18F0A811C0DCB07CF','I am very good at it','I know everything about Java','Oracle');

insert into speaker (id, user_account, short_cv, experience, organization)
values ('KIOLJHGYUJHNBVGTHIJSVCBOLLYHJUHG','A51946308D414EDFA7C31360569AE248','I am very good at it','I know everything about Java','Google');

insert into speaker_session (id, speaker, session)
values ('IKOALQKSJDHFNCHDJNXJUHSYHJDBCNHJ','KIOLJHGYUJHNBVGTHIJSVCBXGTYHJUHG','AQWXSZEDCVFRTGBNHYUJKIOLMPOIUYTR');

insert into speaker_session (id, speaker, session)
values ('IKOALQKSJJHFNCHDJNXJUHSYHJDBCNHJ','KIOLJHGYUJHNBVGTHIJSVCBOLLYHJUHG','AQWXSZEDCVFRTGBNHYUJKIOLMPOIUYTR');

insert into speaker_session (id, speaker, session)
values ('IKOALQOLKKHFNCHDJNXJUHSYHJDBCNHJ','KIOLJHGYUJHNBVGTHIJSVCBOLLYHJUHG','BRVZTAFEDYGSTGBNHYUJKIOLMPOIUYTR');

insert into speaker_session (id, speaker, session)
values ('IKOALQOLKKHFNCHDJNXJUHSOQLXCCNHJ','KIOLJHGYUJHNBVGTHIJSVCBXGTYHJUHG','AQWOLQSDCVFRTGBNHYUJKIOLMPOIUYTR');

insert into track (id, name, event, color, description)
values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJHUSI','Enterprise Java', 'KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ', 'EEEEEE', 'All about enterprise java applications');

insert into track (id, name, event, color, description)
values ('QBHCGDHBNJUHZISKJSHNBCJOLKHJHUSI','Java Embedded', 'KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ', 'CCCCCC', 'Java running in several tiny devices');

insert into track (id, name, event, color, description)
values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS','Security', 'KASKIPMQKUJHKNCKIKZJSQSGPWSDIKIJ', 'FF0000', 'Securing your Java App');

insert into venue (id, name, address, latitude, longitude, website)
values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS','MetroPolis Business Center','Groenendaallaan 394, 2030 Antwerp, Belgium','51.245954','4.417888','metropolisantwerpen.cinenews.be');

insert into event_venue (id, event, venue) values ('JUIKHJUIKJHUYJHUJNHBJIKUJHYUJHYU','00FDA9744A64448D9CD819658439F336','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');

insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS','Room 3','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJO102','Room 4','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQ2','Room 5','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJOK3S','Room 6','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJO2QS','Room 7','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJ22QS','Room 8','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');
insert into room (id, name, venue) values ('QBHCGDHBNJUHZISKJSHNBCJKIDHJ9KQS','Room 9','QBHCGDHBNJUHZISKJSHNBCJKIDHJOKQS');

update session set room = 'QBHCGDHBNJUHZISKJSHNBCJKIDHJ22QS';