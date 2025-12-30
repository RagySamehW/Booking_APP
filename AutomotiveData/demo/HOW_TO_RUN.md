# How to Run the Application and Update Database

## Prerequisites
1. **PostgreSQL must be running** on your machine (port 5432)
2. **Java 21** installed
3. **Maven** installed (or use the included `mvnw` wrapper)

## Step 1: Create the Database (if it doesn't exist)

Open PostgreSQL (pgAdmin, psql, or any PostgreSQL client) and run:

```sql
CREATE DATABASE "Automotive_System";
```

Or using command line:
```bash
psql -U postgres -c "CREATE DATABASE \"Automotive_System\";"
```

## Step 2: Run the Schema SQL to Create Tables

Connect to the `Automotive_System` database and run the `schema.sql` file:

**Option A: Using psql command line:**
```bash
psql -U postgres -d Automotive_System -f src/main/resources/schema.sql
```

**Option B: Using pgAdmin:**
1. Open pgAdmin
2. Connect to your PostgreSQL server
3. Right-click on `Automotive_System` database → Query Tool
4. Open and run `src/main/resources/schema.sql`

**Option C: Copy-paste the SQL:**
```sql
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
```

## Step 3: Run the Spring Boot Application

**Option A: Using Maven Wrapper (Recommended - works without Maven installed):**

Windows:
```bash
.\mvnw.cmd spring-boot:run
```

Linux/Mac:
```bash
./mvnw spring-boot:run
```

**Option B: Using Maven (if installed):**
```bash
mvn spring-boot:run
```

**Option C: Using your IDE:**
- Right-click on `DemoApplication.java`
- Select "Run DemoApplication"

## Step 4: Verify It's Working

1. **Check the console output** - You should see:
   ```
   ✓ Saved customer car with VIN: 123456
   ✓ Saved customer car with VIN: 654321
   ...
   Initial data loading completed!
   ```

2. **Test the API** (application runs on port 8080 by default):
   ```bash
   # Get all customer cars
   curl http://localhost:8080/api/customer-cars
   
   # Or open in browser:
   http://localhost:8080/api/customer-cars
   ```

## Troubleshooting

### Error: "Connection refused" or "Database does not exist"
- Make sure PostgreSQL is running
- Verify database name is exactly `Automotive_System` (case-sensitive)
- Check `application.yaml` credentials match your PostgreSQL setup

### Error: "Table does not exist"
- Make sure you ran `schema.sql` in Step 2
- Verify you're connected to the correct database

### Error: "Port 8080 already in use"
- Another application is using port 8080
- Change port in `application.yaml`:
  ```yaml
  server:
    port: 8081
  ```

## What Happens When You Run

1. **Application starts** - Spring Boot initializes
2. **Database connection** - Connects to PostgreSQL using R2DBC
3. **CommandLineRunner executes** - Seeds initial customer car data (6 cars with VINs)
4. **Server ready** - API is available at `http://localhost:8080`

## API Endpoints Available

- `GET /api/customer-cars` - Get all customer cars
- `GET /api/customer-cars/{id}` - Get customer car by ID
- `POST /api/customer-cars` - Create new customer car
- `DELETE /api/customer-cars/{id}` - Delete customer car
- `GET /api/customer-cars/vin/{vin}` - Find customer car by VIN
- `GET /api/customer-cars/count` - Count all customer cars


