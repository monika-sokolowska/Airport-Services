import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {
  assignStandManagerThunk,
  getAvailableStandManagersThunk,
} from "./generalManagerThunk";
import { toast } from "react-toastify";

const initialState = {
  isLoading: false,
  standManagers: [],
};

export const assignStandManager = createAsyncThunk(
  "generalManager/assignStandManager",
  async (url, data, thunkAPI) => {
    return assignStandManagerThunk(url, data);
  }
);

export const getAvailableStandManagers = createAsyncThunk(
  "generalManager/getAvailableStandManagers",
  async () => {
    return getAvailableStandManagersThunk("/generalManager/standManagers");
  }
);

const generalManagerSlice = createSlice({
  name: "generalManager",
  initialState,
  reducers: {},

  extraReducers: (builder) => {
    builder
      .addCase(getAvailableStandManagers.pending, (state) => {
        state.isLoading = true;
        console.log("state", state);
      })
      .addCase(getAvailableStandManagers.fulfilled, (state, { payload }) => {
        console.log("payload", payload);
        state.standManagers = payload;
        console.log("state.standManagers", state.standManagers);
      })
      .addCase(getAvailableStandManagers.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error("Something went wrong...");
      });
  },
});

export default generalManagerSlice.reducer;
