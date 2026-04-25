import React, { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { login } from "../../../../context/slices/auth-slices";
import api from "../../../../api/api";
import { Button } from "react-bootstrap";

const validationSchema = Yup.object({
    username: Yup.string().required("Kullanıcı adı zorunludur"),
    password: Yup.string().required("Şifre zorunludur"),
});

const LoginForm = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [error, setError] = useState(null);

    const formik = useFormik({
        initialValues: { username: "", password: "" },
        validationSchema,
        onSubmit: async (values, { setSubmitting }) => {
            setError(null);
            try {
                const { data } = await api.post("/auth/login", values);
                dispatch(login(data));
                navigate("/dashboard");
            } catch (err) {
                setError(
                    err.response?.data?.message ||
                    "Kullanıcı adı veya şifre hatalı."
                );
            } finally {
                setSubmitting(false);
            }
        },
    });

    return (
        <div className="container">
            <div className="row justify-content-center mt-5">
                <div className="col-md-4">
                    <div className="card shadow-sm p-4">
                        <h4 className="text-center mb-4">Giriş Yap</h4>

                        {error && (
                            <div className="alert alert-danger">{error}</div>
                        )}

                        <form onSubmit={formik.handleSubmit} noValidate>
                            <div className="mb-3">
                                <label className="form-label">Kullanıcı Adı</label>
                                <input
                                    type="text"
                                    className={`form-control ${
                                        formik.touched.username && formik.errors.username
                                            ? "is-invalid"
                                            : ""
                                    }`}
                                    {...formik.getFieldProps("username")}
                                />
                                {formik.touched.username && formik.errors.username && (
                                    <div className="invalid-feedback">
                                        {formik.errors.username}
                                    </div>
                                )}
                            </div>

                            <div className="mb-3">
                                <label className="form-label">Şifre</label>
                                <input
                                    type="password"
                                    className={`form-control ${
                                        formik.touched.password && formik.errors.password
                                            ? "is-invalid"
                                            : ""
                                    }`}
                                    {...formik.getFieldProps("password")}
                                />
                                {formik.touched.password && formik.errors.password && (
                                    <div className="invalid-feedback">
                                        {formik.errors.password}
                                    </div>
                                )}
                            </div>

                            <Button
                                type="submit"
                                variant="primary"
                                className="w-100"
                                disabled={formik.isSubmitting}
                            >
                                {formik.isSubmitting ? "Giriş yapılıyor..." : "Giriş Yap"}
                            </Button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginForm;
