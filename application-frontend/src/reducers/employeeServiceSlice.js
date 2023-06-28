import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {
  getAssignedFlightThunk,
  getStartServiceThunk,
  postFinishedThunk,
  postLandedThunk,
} from "./employeeServiceThunk";
import { toast } from "react-toastify";

const initialState = {
  isLoading: false,
  assignedFlight: {},
  serviceStart: "",
};

export const getAssignedFlight = createAsyncThunk(
  "employeeService/getAssignedFlight",
  async (userId) => {
    return getAssignedFlightThunk(`/service/getServiceInfo/${userId}`);
  }
);

export const getStartService = createAsyncThunk(
  "employeeService/getAssignedFlight",
  async (userId) => {
    return getStartServiceThunk(`/employeeService/${userId}/serviceStart`);
  }
);

export const postLanded = createAsyncThunk(
  "employeeService/postLanded",
  async (flightNumber) => {
    return postLandedThunk(`/flight/navigator/landed?number=${flightNumber}`);
  }
);

export const postFinished = createAsyncThunk(
  "standManager/postFinished",
  async (data) => {
    const { userId, flightId } = data;

    //TODO flightId to endpoint
    return postFinishedThunk(`/standManager/${userId}/finished`);
  }
);

const employeeServiceSlice = createSlice({
  name: "employeeService",
  initialState,
  reducers: {},

  extraReducers: (builder) => {
    builder
      .addCase(getAssignedFlight.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getAssignedFlight.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.assignedFlight = payload;
        console.log("state.assignedFlights", state.assignedFlights);
      })
      .addCase(getAssignedFlight.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default employeeServiceSlice.reducer;
