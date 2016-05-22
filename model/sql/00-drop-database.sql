-- Terminate all database connections
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'scalaq';

-- Drop database
DROP DATABASE "scalaq";

-- Drop owner
DROP ROLE "scalaq";
