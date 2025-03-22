
### 1. **Start the Database**
```bash
# Build and start containers in detached mode
docker compose up -d --build postgres
```

### 2. **Verify Database Status**
```bash
# Check container status
docker ps | grep rag_db

# Expected output:
# rag_db    postgres:15   Up 2 minutes  0.0.0.0:5432->5432/tcp
```

### 3. **Verify pgvector Installation**
```bash
# Check installed extensions
docker exec rag_db psql -U postgres -d rag_db -c "\dx"

# Should see 'vector' in the list:
# Name  | Version | Schema | Description 
#--------+---------+------------+-------------
# plpgsql | 1.0     | pg_catalog | PL/pgSQL procedural language
# vector  | 1.0     | public     | vector data type and similarity search
```

### 4. **Connect to Database (Interactive Shell)**
```bash
docker exec -it rag_db psql -U postgres -d rag_db
```

### 7. **Common Maintenance Commands**

**Stop the database:**
```bash
docker compose down
```

**Start existing database:**
```bash
docker compose up -d postgres
```

**Full clean restart (⚠️ deletes all data):**
```bash
docker compose down -v && docker compose up -d --build postgres
```

---

### **Full Workflow Cheat Sheet**
```bash
# Start database
docker compose up -d --build postgres

# Verify installation
docker exec rag_db psql -U postgres -d rag_db -c "\dx"

# Connect interactively
docker exec -it rag_db psql -U postgres -d rag_db

# Stop database
docker compose down

# Remove everything (including data)
docker compose down -v
```

---

### **Important Notes**
1. **Data Persistence**
    - Data persists between restarts via the `postgres_data` volume
    - To reset completely, run `docker compose down -v`

2. **Connection Details**
    - Host: `localhost`
    - Port: `5432`
    - Database: `rag_db`
    - User: `postgres`
    - Password: `postgres`

3. **Prerequisites**
    - Docker and Docker Compose installed
    - The directory structure must have:
      ```
      📁 postgres/
      ├── Dockerfile-postgres
      └── init/
          └── 01-init.sql
      ```

---

### **Troubleshooting**
If you get permission errors:
```bash
# Fix file permissions
chmod -R 755 postgres/init/*.sql
```

If the extension isn't created:
```bash
# Manually create extension after connecting
docker exec -it rag_db psql -U postgres -d rag_db -c "CREATE EXTENSION vector;"
```

Check container logs:
```bash
docker compose logs postgres
```