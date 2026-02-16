# 122_Clean_Code

Code Quality module: SOLID, refactoring, design patterns, SonarQube (Homework tasks 1–4).

## Build and test

```bash
mvn clean test
mvn spring-boot:run
```

---

## SonarQube (Task 4)

```bash
docker compose up -d
docker compose ps
# sonarqube  ...  Up (healthy)
```

open [http://localhost:9000](http://localhost:9000) — when the login page loads, SonarQube is ready.

### 2. First login (one-time)

- Open **http://localhost:9000**
- Login: **admin** / **admin**
- Change password when prompted (or skip if allowed)

### 3. Create project and token (one-time)

- **Create project manually**: Project key e.g. `122-clean-code`, display name `122 Clean Code`
- **Generate token**: My Account → Security → Generate token (name e.g. `maven`), copy the token

### 4. Run analysis

From the project root:

```bash
mvn clean verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.projectKey=122-clean-code \
  -Dsonar.token=YOUR_TOKEN
```

Replace `YOUR_TOKEN` with the token from step 3. If you did not create a project in the UI, SonarQube will create it with key `122-clean-code`.

### 5. View results

- Open **http://localhost:9000**
- Open project **122-clean-code** (or the name you set)
- Check **Issues**: you should see the intentional bugs, vulnerabilities, and code smells from Task 4 (e.g. SQL injection, division by zero, duplication, resource leak)

### Stop SonarQube

```bash
docker compose down
```

Data is stored in Docker volumes; `docker compose up -d` next time keeps the same projects and settings.

---

## Generate error events

http://localhost:8081/unsafe?input=hello
http://localhost:8081/logic-error
http://localhost:8081/smell?type=1
