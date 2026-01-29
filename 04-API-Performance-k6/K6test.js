import http from 'k6/http';
import { check, sleep } from 'k6';

const baseUrl = 'https://dummyjson.com';

const endpoints = ['/products', '/carts/1', '/users/1'];

const TEST_TYPE = (__ENV.TEST_TYPE || 'smoke').toLowerCase();

export const options =
  TEST_TYPE === 'load'
    ? {
        vus: 20,
        duration: '30s',
      }
    : {
        vus: 5,
        duration: '10s',
        gracefulStop: '0s',
        gracefulRampDown: '0s',
      };

export default function () {
  // /products
  let res = http.get(baseUrl + endpoints[0]);
  check(res, {
    'Products list status is 200': (r) => r.status === 200,
    'Products list has products': (r) => (r.json('products') || []).length > 0,
  });

  // /carts/1
  res = http.get(baseUrl + endpoints[1]);
  check(res, {
    'Cart status is 200': (r) => r.status === 200,
    'Cart contains products': (r) => (r.json('products') || []).length > 0,
  });

  // /users/1
  res = http.get(baseUrl + endpoints[2]);
  check(res, {
    'User status is 200': (r) => r.status === 200,
    'User has id': (r) => r.json('id') !== undefined,
  });

  sleep(1);
}
