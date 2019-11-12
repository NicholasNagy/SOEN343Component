CREATE TYPE pets AS ENUM ('SMALL', 'YES', 'NO');
CREATE TABLE property
(
    id            UUID NOT NULL PRIMARY KEY,
    parkingSpaces int,
    petsAllowed   pets,
    bedrooms      int,
    bathrooms     int,
    address       varchar(100)
);