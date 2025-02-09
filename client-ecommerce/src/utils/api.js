import axios from "axios";

const api = axios.create({
  baseURL: `http://localhost:8080/api/v1`,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    "Cache-Control": "no-store, no-cache, must-revalidate, proxy-revalidate",
  },
  withCredentials: true,
});

const imageApi = axios.create({
  baseURL: `http://localhost:8080/api/v1`,
  headers: {
    // "Content-Type": "application/json",
    Accept: "application/json",
    "Cache-Control": "no-store, no-cache, must-revalidate, proxy-revalidate",
  },
  withCredentials: true,
});


export { api, imageApi };