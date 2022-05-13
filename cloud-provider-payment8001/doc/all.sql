create table `payment`(
    `id`        bigint (20)         not null     auto_increment      comment 'id',
    `serial`    varchar (200)      default'',
    primary key (`id`)
) engine=innodb AUTO_INCREMENT=1 default charset=utf8;

select * from payment;

insert into payment (id, serial) values (1,'aaabbb01');
