insert into author (id, role, username, password) values (1, 'ADMIN', 'admin', 'admin');
insert into author (id, role, username, password) values (2, 'USER', 'user1', 'user1');
insert into author (id, role, username, password) values (3, 'USER', 'User2', 'User2');

insert into comment (id, comment, author_id) values (1, 'This is the first comment.', 2);
insert into comment (id, comment, author_id) values (2, 'This is the second comment.', 2);
insert into comment (id, comment, author_id) values (3, 'This is the third comment.', 2);
insert into comment (id, comment, author_id) values (4, 'This is the fourth comment.', 3);
insert into comment (id, comment, author_id) values (5, 'This is the fifth comment.', 3);
insert into comment (id, comment, author_id) values (6, 'This is the sixth comment.', 3);