import axios from "axios";
import { getTokenFromLocalStorage } from "./localStorage";

const customFetch = axios.create({
  baseURL: "http://localhost:8091",
});

customFetch.interceptors.request.use((config) => {
  const token = getTokenFromLocalStorage();
  if (token) {
    config.headers["Authorization"] = `Bearer ${token.token}`;
  }
  return config;
});

export default customFetch;
