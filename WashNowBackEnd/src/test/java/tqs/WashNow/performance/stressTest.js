import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend } from 'k6/metrics';

export const options = {
  stages: [
    { duration: '10s', target: 10 },
    { duration: '30s', target: 50 },
    { duration: '30s', target: 100 },
    { duration: '30s', target: 200 },
    { duration: '1m', target: 200 },  // hold at 200
    { duration: '30s', target: 0 },
  ],
  thresholds: {
    'http_req_duration': ['p(95)<1000'], // 95% of requests should be under 1000ms
    'http_req_failed': ['rate<0.01'],   // <1% errors
  },
};

let washSessionTrend = new Trend('wash_session_created');
let stationTrend = new Trend('stations_created');
let paymentTrend = new Trend('payments_successful');

const BASE = 'http://deti-tqs-11.ua.pt:8080'; // MUDAR AQUI O URL QUANDO QUERES EXECUTAR LOCALMENTE OU NA VM

export default function () {
  // 0. Scrape Prometheus endpoint
  let res = http.get(`${BASE}/actuator/prometheus`);
  check(res, { 'prometheus metrics available': r => r.status === 200 });

  // 1. Health check
  res = http.get(`${BASE}/actuator/health`);
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

   // 7. Create new station
  let stationPayload = JSON.stringify({
      name: `Station_${__VU}_${__ITER}`,
      location: 'Test Avenue 123'
    });
  res = http.post(`${BASE}/api/CarwashStations`, stationPayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  check(res, { 'station created': r => r.status === 201 });
  let stationId = res.json('id');
  stationTrend.add(1);

  // 8. Create new person
  let personPayload = JSON.stringify({
    name: `User_${__VU}_${__ITER}`,
    email: `user_${__VU}_${__ITER}@washnow.com`,
    role: 'OWNER',
    balance: 25.0
  });
  res = http.post(`${BASE}/api/Persons`, personPayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  check(res, { 'person created': r => r.status === 201 });
  let personId = res.json('id');

  // 9. List Carwash Bays
  res = http.get(`${BASE}/api/CarwashBays`);
  check(res, { 'got carwash bays': r => r.status === 200 });

  // 10. Simulate payment
  let paymentPayload = JSON.stringify({
    amount: 15.50,
    method: 'CASH',
    personId: personId
  });
  res = http.post(`${BASE}/api/Payments`, paymentPayload, {
    headers: { 'Content-Type': 'application/json' }
  });
  if (check(res, { 'payment created': r => r.status === 201 })) {
    paymentTrend.add(1);
  }

  sleep(1);
}
