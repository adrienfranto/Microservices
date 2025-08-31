DO
$$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database
      WHERE datname = 'inventory_service'
   ) THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE inventory_service');
END IF;
END
$$;
