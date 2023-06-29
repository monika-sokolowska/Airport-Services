import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {
  getAssignedFlightsThunk,
  getAvailableThunk,
  assignEmployeeThunk,
  postFinishedThunk,
} from "./standManagerThunk";
import { toast } from "react-toastify";

const initialState = {
  isLoading: false,
  assignedFlights: [],
  availableEmployees: [],
};

export const assignEmployee = createAsyncThunk(
  "standManager/assignEmployee",
  async (data, thunkAPI) => {
    const { id, url } = data;
    const result = await assignEmployeeThunk(url);
    thunkAPI.dispatch(getAvailable(id));
    return result;
  }
);

export const getAssignedFlights = createAsyncThunk(
  "standManager/getAssignedFlights",
  async (userId) => {
    return getAssignedFlightsThunk(`/standManager/${userId}/getFlights`);
  }
);

export const getAvailable = createAsyncThunk(
  "standManager/getAvailable",
  async (userId) => {
    return getAvailableThunk(
      `/standManager/${userId}/getEmployees?isBusy=false`
    );
  }
);

export const postFinished = createAsyncThunk(
  "standManager/postFinished",
  async (data) => {
    const { userId, flightId } = data;

    return postFinishedThunk(`/standManager/${userId}/finished/${flightId}`);
  }
);

const standManagerSlice = createSlice({
  name: "standManager",
  initialState,
  reducers: {},

  extraReducers: (builder) => {
    builder
      .addCase(getAssignedFlights.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getAssignedFlights.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.assignedFlights = payload;
        console.log("state.assignedFlights", state.assignedFlights);
      })
      .addCase(getAssignedFlights.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      })
      .addCase(getAvailable.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getAvailable.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.availableEmployees = payload;
        console.log("state.availableEmployees", state.availableEmployees);
      })
      .addCase(getAvailable.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      })
      .addCase(assignEmployee.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(assignEmployee.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        toast.success("Assigned employee");
      })
      .addCase(assignEmployee.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default standManagerSlice.reducer;
