CREATE TYPE propertyType AS ENUM ('APPARTMENT', 'CONDO', 'HOUSE');
CREATE TABLE property
(
    id            UUID NOT NULL PRIMARY KEY,
    parkingSpaces int,
    petsAllowed   boolean,
    bedrooms      int,
    bathrooms     int,
    address       varchar(100),
    propertyType  propertyType,
    propertyID    UUID,
    price         float
);