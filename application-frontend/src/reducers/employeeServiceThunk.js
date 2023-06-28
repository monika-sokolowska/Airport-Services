import customFetch from "../utils/axios";
import { toast } from "react-toastify";

export const getAssignedFlightThunk = async (url) => {
  try {
    const resp = await customFetch.get(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {}
};

export const getStartServiceThunk = async (url) => {
  try {
    const resp = await customFetch.get(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {}
};

export const postFinishedThunk = async (url) => {
  try {
    const resp = await customFetch.post(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {
    toast.error("Something went wrong...");
  }
};

export const postLandedThunk = async (url) => {
  try {
    const resp = await customFetch.post(url);
    toast.success(`Plane landed - information sent`);
    return resp.data;
  } catch (error) {
    toast.error("Request was not sent due to error");
  }
};
