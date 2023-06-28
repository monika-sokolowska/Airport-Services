import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./reducers/userSlice";
import serviceSlice from "./reducers/serviceSlice";
import flightsSlice from "./reducers/flightsSlice";
import generalManagerSlice from "./reducers/generalManagerSlice";
import standManagerSlice from "./reducers/standManagerSlice";
import employeeServiceSlice from "./reducers/employeeServiceSlice";

export const store = configureStore({
  reducer: {
    user: userSlice,
    service: serviceSlice,
    flights: flightsSlice,
    generalManager: generalManagerSlice,
    standManager: standManagerSlice,
    employeeService: employeeServiceSlice,
  },
});
