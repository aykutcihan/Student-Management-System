import React, { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useDispatch } from "react-redux";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../../../context/slices/auth-slices";
import api from "../../../api/api";
import { FiLock, FiUser, FiArrowRight, FiEye, FiEyeOff } from "react-icons/fi";

const schema = Yup.object({
    username: Yup.string().required("Username is required"),
    password: Yup.string().required("Password is required"),
});

const LoginPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [showPw, setShowPw] = useState(false);

    const formik = useFormik({
        initialValues: { username: "", password: "" },
        validationSchema: schema,
        onSubmit: async (values, { setSubmitting }) => {
            setError(null);
            try {
                const { data } = await api.post("/auth/login", values);
                dispatch(login(data));
                navigate("/dashboard");
            } catch (err) {
                setError(err.response?.data?.message || "Invalid username or password.");
            } finally {
                setSubmitting(false);
            }
        },
    });

    return (
        <div className="login-page">
            <div className="container py-5">
                <div className="row justify-content-center">
                    <div className="col-lg-10 col-xl-9">
                        <div className="login-card card row g-0">
                            <div className="col-lg-5 login-side d-none d-lg-flex">
                                <div>
                                    <div style={{ width: 56, height: 56, borderRadius: 12, background: "rgba(255,255,255,.15)", display: "flex", alignItems: "center", justifyContent: "center", fontSize: "1.5rem", fontWeight: 700, marginBottom: "1.5rem" }}>S</div>
                                    <h3>CampusMS Portal</h3>
                                    <p className="mt-2 mb-4">Access your academic portal. Manage students, courses, and more.</p>
                                    <ul className="list-unstyled" style={{ fontSize: ".85rem" }}>
                                        {["Student & teacher management", "Course scheduling", "Grade tracking", "Meeting management"].map((t) => (
                                            <li key={t} className="mb-2 d-flex align-items-center gap-2" style={{ opacity: .85 }}>
                                                <span style={{ color: "#86efac" }}>✓</span> {t}
                                            </li>
                                        ))}
                                    </ul>
                                    <div className="mt-4 pt-4" style={{ borderTop: "1px solid rgba(255,255,255,.15)", fontSize: ".8rem", opacity: .7 }}>
                                        Don't have access? <br />Contact your administrator.
                                    </div>
                                </div>
                            </div>

                            <div className="col-lg-7">
                                <div className="p-4 p-md-5">
                                    <h4 className="fw-bold mb-1" style={{ color: "#0f172a" }}>Welcome back</h4>
                                    <p className="text-muted mb-4" style={{ fontSize: ".88rem" }}>Sign in to your account to continue</p>

                                    {error && (
                                        <div className="alert alert-danger d-flex align-items-center gap-2 py-2" style={{ fontSize: ".85rem" }}>
                                            <FiLock size={14} /> {error}
                                        </div>
                                    )}

                                    <form onSubmit={formik.handleSubmit} noValidate>
                                        <div className="mb-3">
                                            <label className="form-label">Username</label>
                                            <div className="input-group">
                                                <span className="input-group-text" style={{ background: "#f8fafc", borderColor: "#d1d5db" }}>
                                                    <FiUser size={15} color="#64748b" />
                                                </span>
                                                <input
                                                    type="text"
                                                    placeholder="Enter your username"
                                                    className={`form-control ${formik.touched.username && formik.errors.username ? "is-invalid" : ""}`}
                                                    {...formik.getFieldProps("username")}
                                                />
                                                {formik.touched.username && formik.errors.username && (
                                                    <div className="invalid-feedback">{formik.errors.username}</div>
                                                )}
                                            </div>
                                        </div>

                                        <div className="mb-4">
                                            <label className="form-label">Password</label>
                                            <div className="input-group">
                                                <span className="input-group-text" style={{ background: "#f8fafc", borderColor: "#d1d5db" }}>
                                                    <FiLock size={15} color="#64748b" />
                                                </span>
                                                <input
                                                    type={showPw ? "text" : "password"}
                                                    placeholder="Enter your password"
                                                    className={`form-control ${formik.touched.password && formik.errors.password ? "is-invalid" : ""}`}
                                                    {...formik.getFieldProps("password")}
                                                />
                                                <button type="button" className="input-group-text" style={{ background: "#f8fafc", borderColor: "#d1d5db", cursor: "pointer" }} onClick={() => setShowPw(!showPw)}>
                                                    {showPw ? <FiEyeOff size={15} color="#64748b" /> : <FiEye size={15} color="#64748b" />}
                                                </button>
                                                {formik.touched.password && formik.errors.password && (
                                                    <div className="invalid-feedback">{formik.errors.password}</div>
                                                )}
                                            </div>
                                        </div>

                                        <button type="submit" className="btn btn-primary w-100 d-flex align-items-center justify-content-center gap-2 py-2" disabled={formik.isSubmitting}>
                                            {formik.isSubmitting ? (
                                                <><span className="spinner-border spinner-border-sm" /> Signing in...</>
                                            ) : (
                                                <>Sign In <FiArrowRight size={16} /></>
                                            )}
                                        </button>
                                    </form>

                                    <div className="text-center mt-4">
                                        <Link to="/" className="text-muted" style={{ fontSize: ".82rem" }}>← Back to Home</Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
