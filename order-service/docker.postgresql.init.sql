DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles WHERE rolname = 'postgres'
   ) THEN
CREATE ROLE postgres WITH LOGIN PASSWORD 'devsecops';
END IF;
END
$$;

DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'order_service'
   ) THEN
      CREATE DATABASE order_service OWNER postgres;
END IF;
END
$$;
