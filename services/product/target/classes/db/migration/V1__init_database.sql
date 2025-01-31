CREATE TABLE IF NOT EXISTS category(
    id bigint not null  primary key,
    name varchar(255),
    description varchar(255)
);

CREATE TABLE IF NOT EXISTS product(
    id bigint not null  primary key,
    name varchar(255),
    description varchar(255),
    available_quantity double precision not null,
    price numeric(38,2),
    category_id bigint constraint fk1teuweiwu343430943jjhds references category
);

CREATE SEQUENCE if not exists category_seq increment by 50;
CREATE SEQUENCE if not exists product_seq increment by 50;