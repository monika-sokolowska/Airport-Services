import customFetch from "../utils/axios";
import { toast } from "react-toastify";

export const getFlightsThunk = async (url) => {
  try {
    const resp = await customFetch.get(url);
    console.log("resp", resp.data);
    return resp.data;
  } catch (error) {
    toast.error("Could not fetch data");
  }
};
