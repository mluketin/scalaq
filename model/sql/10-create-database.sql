-- Create database owner
CREATE ROLE "scalaq" PASSWORD 'scalaq' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN;

-- Create database
CREATE DATABASE "scalaq" OWNER "scalaq" ENCODING 'utf8' TEMPLATE "template1";
