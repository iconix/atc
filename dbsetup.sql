DROP TABLE IF EXISTS ClientAccounts;
DROP TABLE IF EXISTS GPSCoordinate;
DROP TABLE IF EXISTS PinLocations;

CREATE TABLE ClientAccounts(accountID CHAR(16), deviceID CHAR(64), password CHAR(64), email CHAR(32));
CREATE TABLE GPSCoordinate(accountID CHAR(16), deviceID CHAR(64), time BIGINT, longitude DOUBLE, latitude DOUBLE);
CREATE TABLE PinLocations(accountID CHAR(16), time BIGINT, longitude DOUBLE, latitude DOUBLE, title CHAR(255), description CHAR(255));
