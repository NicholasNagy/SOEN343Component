CREATE TABLE address (
    id uuid PRIMARY KEY NOT NULL,
    address int,
    street varchar(100),
    city varchar(100),
    province varchar(100),
    country varchar(100)
)