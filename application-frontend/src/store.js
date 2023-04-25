import { configureStore } from "@reduxjs/toolkit";
import userSlice from "./reducers/userSlice";
import serviceSlice from "./reducers/serviceSlice";

export const store = configureStore({
  reducer: {
    user: userSlice,
    service: serviceSlice,
  },
});
