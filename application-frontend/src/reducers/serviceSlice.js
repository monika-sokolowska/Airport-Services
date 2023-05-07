import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { changeServiceThunk } from "./serviceThunk";

const initialState = {
  service: "LUGGAGE_ARRIVAL",
};

export const changeServiceAction = createAsyncThunk(
  "service/changeService",
  changeServiceThunk
);

const serviceSlice = createSlice({
  name: "service",
  initialState,
  reducers: {
    changeService: (state, { payload }) => {
      state.service = payload;
    },
  },
});

export const { changeService } = serviceSlice.actions;
export default serviceSlice.reducer;
