import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import {
  addUserToLocalStorage,
  getUserFromLocalStorage,
  removeUserFromLocalStorage,
  getTokenFromLocalStorage,
  addTokenToLocalStorage,
  removeTokenFromLocalStorage,
} from "../utils/localStorage";
import { loginUserThunk, clearStoreThunk, getUserThunk } from "./userThunk";

const initialState = {
  isLoading: false,
  user: getUserFromLocalStorage(),
  token: getTokenFromLocalStorage(),
};

export const loginUser = createAsyncThunk(
  "user/loginUserFlow",
  async (user, thunkAPI) => {
    const result = await thunkAPI.dispatch(loginUserFlow(user));
    thunkAPI.dispatch(getUser());
    return result;
  }
);

export const loginUserFlow = createAsyncThunk(
  "user/loginUser",
  async (user, thunkAPI) => {
    const result = await loginUserThunk("/auth/login", user, thunkAPI);
    thunkAPI.dispatch(getUser());
    return result;
  }
);

export const getUser = createAsyncThunk("user/getUser", async (_, thunkAPI) => {
  const result = await getUserThunk("/employee/get", thunkAPI);
  return result;
});

export const clearStore = createAsyncThunk("user/clearStore", clearStoreThunk);
const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    logoutUser: (state, { payload }) => {
      state.user = null;
      removeUserFromLocalStorage();
      removeTokenFromLocalStorage();
      if (payload) {
        toast.success(payload);
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUserFlow.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(loginUserFlow.fulfilled, (state, { payload }) => {
        const token = payload;
        console.log("token", payload);
        state.isLoading = false;
        state.token = token;
        addTokenToLocalStorage(token);
      })
      .addCase(loginUserFlow.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error(payload);
      })
      .addCase(getUser.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getUser.fulfilled, (state, { payload }) => {
        const user = payload;
        console.log("payload", payload);
        state.isLoading = false;
        state.user = user;
        addUserToLocalStorage(user);

        if (!user.service) {
          toast.error(`User ${user.name} does not belong to any service`);
        } else {
          toast.success(`Welcome ${user.name}`);
        }
      })
      .addCase(getUser.rejected, (state, { payload }) => {
        state.isLoading = false;
        toast.error(payload);
      })
      .addCase(clearStore.rejected, () => {
        toast.error("There was an error..");
      });
  },
});

export const { logoutUser } = userSlice.actions;
export default userSlice.reducer;
