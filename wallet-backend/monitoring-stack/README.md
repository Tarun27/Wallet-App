# 🛠 Monitoring Stack for Microservices

This folder sets up a complete **observability stack** for monitoring and debugging your microservices using open-source tools: **Prometheus**, **Grafana**, **Loki**, **Promtail**, and **Tempo**.

---

## 📦 What's Included

| Tool        | Purpose                                      |
|-------------|----------------------------------------------|
| **Prometheus** | Collects metrics (CPU, memory, HTTP latency, etc.) from microservices |
| **Grafana**    | Visualizes metrics, logs, and traces via dashboards |
| **Loki**       | Stores and indexes logs from services (like a lightweight ELK) |
| **Promtail**   | Reads local log files and sends them to Loki |
| **Tempo**      | Collects and correlates trace data across microservices (for debugging flows) |

---

## 🧠 How It Works

- Each microservice (e.g., `user-service`) exposes:
    - Metrics at `/actuator/prometheus`
    - Logs written to a file (e.g., `/var/log/user-service/user.log`)
    - Traces sent via OpenTelemetry to Tempo

- Prometheus **scrapes metrics** from each service every 15 seconds.

- Promtail **collects logs** from mounted log directories and pushes them to Loki.

- Grafana connects to **Prometheus, Loki, and Tempo** to show everything in one place.

---

## 🚀 How to Use

### 1. 📁 Folder Structure

monitoring-stack/
├── docker-compose.yml # Starts all monitoring tools
├── prometheus.yml # Configures targets Prometheus scrapes from
├── promtail-config.yml # Defines which logs to ship to Loki
└── tempo.yaml # Configures trace collection via OTLP


---

### 2. 🧑‍💻 Run the Monitoring Stack

```bash
cd monitoring-stack
docker-compose up -d
This will start:

Prometheus on http://localhost:9090
Grafana on http://localhost:3000
Username: admin
Password: admin (or as set in docker-compose)

3. 🧩 Microservices Integration
Each microservice (e.g., user-service) must:

Use the shared Docker network: wallet-net
Expose metrics at /actuator/prometheus (Spring Boot + Micrometer)
Write logs to /var/log/<service-name>/
Send traces to Tempo at http://tempo:4318
See the example Docker Compose and application.yml setup in the microservices for details.



🧪 Verifying the Setup

Prometheus
Open http://localhost:9090/targets
Ensure all services are listed and marked as UP
Grafana
Login at http://localhost:3000
Add data sources:
Prometheus: http://prometheus:9090
Loki: http://loki:3100
Tempo: http://tempo:3200 (or port 4318 depending on setup)
Import dashboards or create your own (JVM, HTTP metrics, logs, etc.)
Loki
Go to Grafana → Explore → Select Loki
Run a query like: {job="user-service"} to see logs
Tempo
Use Grafana Tempo datasource to view end-to-end traces
You’ll need OpenTelemetry integration in your service code

note: username and password for grafana

username: admin
password: admin