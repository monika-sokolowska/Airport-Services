import customFetch from "../utils/axios";
import { toast } from "react-toastify";

export const assignStandManagerThunk = async (url) => {
  try {
    const resp = await customFetch.post(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {
    toast.error("Something went wrong...");
  }
};

export const getAvailableStandManagersThunk = async (url) => {
  try {
    const resp = await customFetch.get(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {
    toast.error("Could not fetch data");
  }
};
