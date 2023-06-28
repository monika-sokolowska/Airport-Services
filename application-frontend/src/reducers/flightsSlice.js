import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getFlightsThunk } from "./flightThunk";
import { toast } from "react-toastify";

const initialState = {
  departedFlights: [],
  flights: [],
  isLoading: false,
};

export const getFlights = createAsyncThunk("flights/getFlights", async () => {
  return getFlightsThunk("/flight/flightsByStatus");
});

export const getDepartedFlights = createAsyncThunk(
  "flights/getDepartedFlights",
  async () => {
    return getFlightsThunk("/flight/flightsByStatus?status=departure");
  }
);

const flightsSlice = createSlice({
  name: "flights",
  initialState,
  reducers: {},

  extraReducers: (builder) => {
    builder
      .addCase(getFlights.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getFlights.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.flights = payload;
        console.log("state.fligths", state.flights);
      })
      .addCase(getFlights.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      })
      .addCase(getDepartedFlights.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getDepartedFlights.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.departedFlights = payload;
        console.log("state.departedFlights", state.departedFlights);
      })
      .addCase(getDepartedFlights.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default flightsSlice.reducer;
