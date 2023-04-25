import { changeService } from "./serviceSlice";

export const changeServiceThunk = async (serviceName, thunkAPI) => {
  try {
    thunkAPI.dispatch(changeService(serviceName));
    return Promise.resolve();
  } catch (error) {
    return Promise.reject();
  }
};
