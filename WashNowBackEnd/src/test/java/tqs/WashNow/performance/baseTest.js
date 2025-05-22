import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend } from 'k6/metrics';

export const options = {
  vus: 30,          // 30 virtual users
  duration: '30s',  // for about 30 seconds
  thresholds: {
    'http_req_duration': ['p(95)<500'], // 95% of requests should be under 500ms
    'http_req_failed': ['rate<0.01'],   // <1% errors
  },
};

// Measure custom metric: number of wash sessions created
let washSessionTrend = new Trend('wash_session_created');

const BASE = 'http://localhost:8080';

export default function () {
  // 1. Health check
  let res = http.get(`${BASE}/actuator/health`);
  check(res, {
    'health is up': r => r.status === 200
  });

  // 2. Create a new Booking
  let bookingPayload = JSON.stringify({
    personId: 1,
    stationId: 1,
    time: '2025-05-21T10:00:00'
  });
  res = http.post(`${BASE}/api/bookings`, bookingPayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  check(res, { 'booking created': r => r.status === 201 });
  let bookingId = res.json('id');

  // 3. GET the Booking by ID
  res = http.get(`${BASE}/api/bookings/${bookingId}`);
  check(res, { 'get booking by id': r => r.status === 200 });

  // 4. Update the Booking
  let updatePayload = JSON.stringify({ time: '2025-05-21T11:00:00' });
  res = http.put(`${BASE}/api/bookings/${bookingId}`, updatePayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  check(res, { 'booking updated': r => r.status === 200 });

  // 5. Delete the Booking
  res = http.del(`${BASE}/api/bookings/${bookingId}`);
  check(res, { 'booking deleted': r => r.status === 204 });

  // 6. Hit another resource: create a WashSession
  let wsPayload = JSON.stringify({
    bayId: 1,
    bookingId: bookingId,
    status: 'STARTED'
  });
  res = http.post(`${BASE}/api/WashSessions`, wsPayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  if (check(res, { 'wash session created': r => r.status === 201 })) {
    washSessionTrend.add(1);
  }

  // 7. Scrape Prometheus endpoint
  res = http.get(`${BASE}/actuator/prometheus`);
  check(res, { 'prometheus metrics available': r => r.status === 200 });

  sleep(1);
}
