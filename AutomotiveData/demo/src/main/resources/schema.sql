-- Create automotives table (automotive groups/companies)
CREATE TABLE IF NOT EXISTS automotives (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create customer_cars table (renamed from cars)
-- Links customer cars to automotive groups via automotive_id foreign key
CREATE TABLE IF NOT EXISTS customer_cars (
    id SERIAL PRIMARY KEY,
    vin VARCHAR(40) NOT NULL,
    automotive_id BIGINT,
    FOREIGN KEY (automotive_id) REFERENCES automotives(id) ON DELETE SET NULL
);

-- Optional: Add index on VIN for faster lookups
CREATE INDEX IF NOT EXISTS idx_customer_cars_vin ON customer_cars(vin);

-- Optional: Add index on automotive_id for faster joins
CREATE INDEX IF NOT EXISTS idx_customer_cars_automotive_id ON customer_cars(automotive_id);
