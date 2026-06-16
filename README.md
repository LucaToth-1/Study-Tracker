#  Study Tracker

A full-stack web application for tracking study sessions across subjects. Built with a **Spring Boot** REST API backend and a **React** frontend, it lets users log study time, take notes, and monitor cumulative progress per subject.

---

## Features

- Create, update, and delete subjects
- Log study sessions with duration, timestamp, and optional notes
- View total accumulated study time per subject
- RESTful API with full CRUD support for both subjects and sessions
- Bidirectional JPA entity relationships with clean JSON serialization

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3 |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Serialization | Jackson (`@JsonManagedReference` / `@JsonBackReference`) |
| Frontend | React.js |
| Build Tool | Maven |

---

## Architecture

### Data Model

**`Subject`**
- `id` — auto-generated primary key
- `name` — subject label
- `totalStudyTimeMin` — running total of all session durations (minutes)
- `createdAt` / `updatedAt` — automatically managed timestamps via Hibernate `@CreationTimestamp` / `@UpdateTimestamp`
- `sessions` — one-to-many relationship with `StudySession`

**`StudySession`**
- `id` — auto-generated primary key
- `subject` — many-to-one foreign key reference (`subject_id`)
- `durationMin` — length of the session in minutes
- `notes` — optional session notes (up to 1000 characters)
- `timestamp` — when the session took place

The two entities share a **bidirectional JPA relationship** with `CascadeType.ALL` and `orphanRemoval = true`, ensuring session lifecycle is fully managed through the parent subject.

---

## API Endpoints

### Subjects

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/subjects` | Fetch all subjects |
| `GET` | `/subjects/{id}` | Fetch a subject by ID |
| `POST` | `/subjects` | Create a new subject |
| `PUT` | `/subjects/{id}` | Update a subject |
| `DELETE` | `/subjects/{id}` | Delete a subject and all its sessions |

### Study Sessions

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/subjects/{id}/sessions` | Get all sessions for a subject |
| `POST` | `/subjects/{id}/sessions` | Log a new study session |
| `PUT` | `/sessions/{id}` | Update a session |
| `DELETE` | `/sessions/{id}` | Delete a session |

### Stats

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/subjects/{id}/total-time` | Get total study time (minutes) for a subject |

---

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+
- Maven

### Backend

```bash
git clone https://github.com/your-username/study-tracker.git
cd study-tracker/backend
mvn spring-boot:run
```

The API will start at `http://localhost:8080`. H2 console available at `/h2-console`.

### Frontend

```bash
cd study-tracker/frontend
npm install
npm start
```

The React app will start at `http://localhost:3000`.

---

## Roadmap

- [ ] PostgreSQL migration
- [ ] User authentication (Spring Security + JWT)
- [ ] Per-user subject and session isolation
- [ ] Study session history charts
- [ ] Weekly/monthly study goal setting

---

## License

MIT
