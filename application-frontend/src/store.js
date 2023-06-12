import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./reducers/userSlice";
import serviceSlice from "./reducers/serviceSlice";
import flightsSlice from "./reducers/flightsSlice";
import generalManagerSlice from "./reducers/generalManagerSlice";

export const store = configureStore({
  reducer: {
    user: userSlice,
    service: serviceSlice,
    flights: flightsSlice,
    generalManager: generalManagerSlice,
  },
});
