import { createSlice } from "@reduxjs/toolkit";

export const miscSlice = createSlice({
    name: "misc",
    initialState: {
        currentOperation: null,
        currentRecord: null,
        listRefreshToken: null,
    },
    reducers: {
        setCurrentOperation(state, action) {
            state.currentOperation = action.payload;
        },
        setCurrentRecord(state, action) {
            state.currentRecord = action.payload;
        },
        setListRefreshToken(state, action) {
            state.listRefreshToken = action.payload;
        },
    },
});

export const { setCurrentOperation, setCurrentRecord, setListRefreshToken } =
    miscSlice.actions;
export default miscSlice.reducer;