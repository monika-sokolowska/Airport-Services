import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getFlightsThunk } from "./flightThunk";
import { toast } from "react-toastify";

const initialState = {
  fligths: [],
  isLoading: false,
};

export const getFlights = createAsyncThunk("flights/getFlights", async () => {
  return getFlightsThunk("/flight/flightsByStatus");
});

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
        state.fligths = payload;
        console.log("state.fligths", state.fligths);
      })
      .addCase(getFlights.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default flightsSlice.reducer;
