import React from "react";
import { RouterProvider } from "react-router-dom";
import { router } from "./router/router";
import { useSelector } from "react-redux";
const App = () => {
    const authState = useSelector((state)=> state.auth);
    const miscState = useSelector((state)=> state.misc);

    return (
        <>
            <RouterProvider router={router} />
        </>
    );
};
export default App;