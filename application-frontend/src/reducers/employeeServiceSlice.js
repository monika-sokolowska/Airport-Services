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
  start: "",
};

export const getAssignedFlight = createAsyncThunk(
  "employeeService/getAssignedFlight",
  async (userId) => {
    return getAssignedFlightThunk(`/service/getServiceInfo/${userId}`);
  }
);

export const getStartService = createAsyncThunk(
  "employeeService/getStartService",
  async (data) => {
    const { userId, flightId } = data;
    return getStartServiceThunk(`/flight/serviceStart/${userId}/${flightId}`);
  }
);

export const postLanded = createAsyncThunk(
  "employeeService/postLanded",
  async (flightNumber) => {
    return postLandedThunk(`/flight/navigator/landed?number=${flightNumber}`);
  }
);

export const postFinished = createAsyncThunk(
  "employeeService/postFinished",
  async (data) => {
    const { userId, flightId } = data;

    return postFinishedThunk(`/flight/finished/${userId}/${flightId}`);
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
      })
      .addCase(getAssignedFlight.fulfilled, (state, { payload }) => {
        state.assignedFlight = payload;
      })
      .addCase(getAssignedFlight.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      })
      .addCase(getStartService.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getStartService.fulfilled, (state, { payload }) => {
        state.serviceStart = payload;
      })
      .addCase(getStartService.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default employeeServiceSlice.reducer;
