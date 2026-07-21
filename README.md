# system-proposal-processor

## Bahasa Indonesia

### Ringkasan
`system-proposal-processor` adalah service Quarkus untuk memproses proposal polis asuransi. Service ini mendukung:
- Endpoint submit event ke Kafka (`/proposals/submit`)
- Endpoint CRUD proposal (`/proposals`)
- Perhitungan risk score dan underwriting status

### Teknologi
- Java 17
- Quarkus 3.x
- PostgreSQL (runtime default)
- Kafka
- Panache (Hibernate ORM)

### Menjalankan Dependency Lokal (opsional)
Gunakan Docker Compose jika ingin menyalakan PostgreSQL dan Kafka lokal:

```bash
docker compose up -d
```

### Menjalankan Aplikasi (dev mode)

```bash
./mvnw quarkus:dev
```

Di konfigurasi default, service berjalan pada port `8082`.

### Kontrak API CRUD
Catatan: kontrak API CRUD memakai DTO request/response, bukan entity JPA langsung.

#### 1) Create Proposal
- `POST /proposals`

Request body:
```json
{
  "proposalId": "PRO-001",
  "customerId": "CUST-001",
  "customerName": "Budi",
  "productCode": "LIFE-100",
  "age": 35,
  "occupation": "Teacher"
}
```

Response `201 Created`:
```json
{
  "proposalId": "PRO-001",
  "customerId": "CUST-001",
  "customerName": "Budi",
  "productCode": "LIFE-100",
  "age": 35,
  "occupation": "Teacher",
  "riskScore": 30.0,
  "underwritingStatus": "APPROVED",
  "createdAt": "2026-07-21T10:00:00"
}
```

#### 2) Get All Proposals
- `GET /proposals`

Response `200 OK`: array dari `ProposalResponse`.

#### 3) Get Proposal by ID
- `GET /proposals/{proposalId}`

Response `200 OK`: `ProposalResponse`.

#### 4) Update Proposal
- `PUT /proposals/{proposalId}`

Request body sama seperti create (termasuk `proposalId` yang harus sama dengan path).

Response `200 OK`: `ProposalResponse` yang sudah diperbarui.

#### 5) Delete Proposal
- `DELETE /proposals/{proposalId}`

Response `204 No Content`.

### Endpoint Submit Event
- `POST /proposals/submit`
- Menerima payload `PolicyProposalSubmittedEvent` dan meneruskan ke Kafka topic submit.
- Response: `202 Accepted`.

### Menjalankan Test

```bash
./mvnw test
```

Untuk integration test CRUD, project menyediakan `@QuarkusTest` dengan test profile DB in-memory (`CrudDbTestProfile`).

---

## English

### Overview
`system-proposal-processor` is a Quarkus service for insurance policy proposal processing. It includes:
- Kafka submit endpoint (`/proposals/submit`)
- CRUD proposal endpoints (`/proposals`)
- Risk score and underwriting status calculation

### Tech Stack
- Java 17
- Quarkus 3.x
- PostgreSQL (default runtime)
- Kafka
- Panache (Hibernate ORM)

### Run Local Dependencies (optional)
Use Docker Compose to start local PostgreSQL and Kafka:

```bash
docker compose up -d
```

### Run the Application (dev mode)

```bash
./mvnw quarkus:dev
```

With the default config, the service runs on port `8082`.

### CRUD API Contract
Note: CRUD API uses dedicated request/response DTOs instead of exposing JPA entities directly.

#### 1) Create Proposal
- `POST /proposals`

Request body:
```json
{
  "proposalId": "PRO-001",
  "customerId": "CUST-001",
  "customerName": "Budi",
  "productCode": "LIFE-100",
  "age": 35,
  "occupation": "Teacher"
}
```

Response `201 Created`:
```json
{
  "proposalId": "PRO-001",
  "customerId": "CUST-001",
  "customerName": "Budi",
  "productCode": "LIFE-100",
  "age": 35,
  "occupation": "Teacher",
  "riskScore": 30.0,
  "underwritingStatus": "APPROVED",
  "createdAt": "2026-07-21T10:00:00"
}
```

#### 2) Get All Proposals
- `GET /proposals`

Response `200 OK`: array of `ProposalResponse`.

#### 3) Get Proposal by ID
- `GET /proposals/{proposalId}`

Response `200 OK`: `ProposalResponse`.

#### 4) Update Proposal
- `PUT /proposals/{proposalId}`

Request body is the same as create (including `proposalId`, which must match the path parameter).

Response `200 OK`: updated `ProposalResponse`.

#### 5) Delete Proposal
- `DELETE /proposals/{proposalId}`

Response `204 No Content`.

### Submit Event Endpoint
- `POST /proposals/submit`
- Accepts `PolicyProposalSubmittedEvent` payload and forwards it to Kafka submit topic.
- Response: `202 Accepted`.

### Run Tests

```bash
./mvnw test
```

For CRUD integration tests, the project includes `@QuarkusTest` with an in-memory DB test profile (`CrudDbTestProfile`).
