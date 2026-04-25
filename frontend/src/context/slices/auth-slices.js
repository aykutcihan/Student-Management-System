import { createSlice } from "@reduxjs/toolkit";
import { EncryptStorage } from "encrypt-storage";

const encryptStorage = new EncryptStorage("school-mgmt-secret-key");

const storedUser = encryptStorage.getItem("user");

export const authSlice = createSlice({
    name: "auth",
    initialState: {
        isLoggedIn: !!storedUser,
        user: storedUser || null,
    },
    reducers: {
        login(state, action) {
            state.isLoggedIn = true;
            state.user = action.payload;
            encryptStorage.setItem("token", action.payload.token);
            encryptStorage.setItem("user", action.payload);
        },
        logout(state) {
            state.isLoggedIn = false;
            state.user = null;
            encryptStorage.removeItem("token");
            encryptStorage.removeItem("user");
        },
    },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
