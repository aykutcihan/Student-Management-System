import { configureStore } from "@reduxjs/toolkit";
import authReducer from "./slices/auth-slices";
import miscReducer from "./slices/misc-slices";

export const store = configureStore({
    reducer: {
        auth: authReducer,
        misc: miscReducer,
    },
});