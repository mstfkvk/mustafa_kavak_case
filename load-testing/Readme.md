# n11.com Load Test — Locust

Load tests for the search module of [n11.com](https://www.n11.com) using Locust.

---

## Tech Stack

| Tool | Version |
|---|---|
| Python | 3.12.3 |
| Locust | 2.43.3 |

---

## Project Structure

```
load-testing/
├── venv/
├── locustfile.py
└── requirements.txt
```

---

## Test Scenarios

| # | Scenario | Method | Endpoint | Expected Status |
|---|---|---|---|---|
| 1 | Valid search — laptop | GET | /arama?q=laptop | 200 |
| 2 | Valid search — telefon | GET | /arama?q=telefon | 200 |
| 3 | Empty search | GET | /arama?q= | 200, 302 |
| 4 | Special characters | GET | /arama?q=@#$%.,() | 200, 400 |

### Task Weights

Tasks run with different frequencies to simulate real user behavior:

- `search_laptop` → weight 3 (most frequent)
- `search_telefon` → weight 2
- `search_empty` → weight 1
- `search_special_chars` → weight 1

---

## Setup

```bash
# Create virtual environment
python3 -m venv venv
source venv/bin/activate

# Install dependencies
pip install -r requirements.txt
```

---

## Running Tests

### With Web UI

```bash
locust -f locustfile.py
```

Open `http://localhost:8089` and configure:
- **Number of users:** 1
- **Ramp up:** 1
- **Host:** https://www.n11.com

### Headless (terminal only)

```bash
# Run for 60 seconds
locust -f locustfile.py --headless --users 1 --spawn-rate 1 --run-time 60s

# Run for 100 iterations
locust -f locustfile.py --headless --users 1 --spawn-rate 1 --iterations 100
```

---

## Test Results

### Statistics

| Scenario | # Requests | # Fails | Median (ms) | 95th pct (ms) | 99th pct (ms) | Avg (ms) | Min (ms) | Max (ms) |
|---|---|---|---|---|---|---|---|---|
| Search: laptop | 96 | 96 | 88 | 120 | 400 | 97.12 | 79 | 400 |
| Search: telefon araması | 88 | 88 | 87 | 110 | 120 | 89.99 | 78 | 119 |
| Search: empty | 34 | 34 | 88 | 100 | 120 | 90.86 | 81 | 122 |
| Search: special charecters | 32 | 32 | 89 | 120 | 130 | 92.52 | 81 | 131 |
| **Aggregated** | **250** | **250** | **88** | **120** | **150** | **93.17** | **78** | **400** |

---

## Findings

### Finding 1: Cloudflare Bot Protection (403)

**Failure rate: 100%** — All 250 requests failed.

n11.com uses Cloudflare bot protection which detects and blocks non-browser HTTP clients. All requests returned HTTP 403 regardless of headers or wait time between requests.

> This is a valid load test finding. Real load testing of n11.com would require a real browser session or a Cloudflare-bypass capable tool. In a real project, load tests would be run against a test/staging environment, not production.

### Finding 2: Response Time is Stable Despite 403

Even though all requests were blocked, response times are consistent and fast:

- **Median: 88ms** across all scenarios — server responds quickly even for blocked requests
- **95th percentile: 110-120ms** — stable, no significant variance
- **Exception: laptop 99th percentile spiked to 400ms** — likely first connection overhead

### Finding 3: Empty Search Behaves Similarly to Valid Search

Response times for empty search (88ms median) are almost identical to valid searches — suggesting Cloudflare blocks at network level before any application logic runs.

---

## Notes

- Tests were run with `wait_time = between(1, 3)` to simulate human-like behavior
- 1 user, load test type
- Bot protection activates immediately regardless of wait time or session cookies
- In a real project scenario, this test suite would target a staging environment with proper authentication