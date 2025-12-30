-- Migration script: Update database from old 'cars' table to new structure
-- Run this in pgAdmin or psql to update your database

-- Step 1: Drop the old 'cars' table if it exists
DROP TABLE IF EXISTS cars CASCADE;

-- Step 2: Create automotives table (automotive groups/companies)
CREATE TABLE IF NOT EXISTS automotives (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Step 3: Create customer_cars table (renamed from cars)
-- Links customer cars to automotive groups via automotive_id foreign key
CREATE TABLE IF NOT EXISTS customer_cars (
    id SERIAL PRIMARY KEY,
    vin VARCHAR(40) NOT NULL,
    automotive_id BIGINT,
    FOREIGN KEY (automotive_id) REFERENCES automotives(id) ON DELETE SET NULL
);

-- Step 4: Add indexes for performance
CREATE INDEX IF NOT EXISTS idx_customer_cars_vin ON customer_cars(vin);
CREATE INDEX IF NOT EXISTS idx_customer_cars_automotive_id ON customer_cars(automotive_id);

-- Verification: Check tables were created
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
  AND table_name IN ('automotives', 'customer_cars');


