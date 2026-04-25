import React from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { FiMapPin, FiPhone, FiMail, FiClock, FiSend } from "react-icons/fi";
import { saveContactMessage } from "../../../services/contactMessageService";
import Swal from "sweetalert2";

const schema = Yup.object({
    name: Yup.string().min(4).max(16).required("Name is required"),
    email: Yup.string().email("Invalid email").min(5).max(20).required("Email is required"),
    subject: Yup.string().min(4).max(50).required("Subject is required"),
    message: Yup.string().min(4).max(50).required("Message is required"),
});

const ContactPage = () => {
    const formik = useFormik({
        initialValues: { name: "", email: "", subject: "", message: "" },
        validationSchema: schema,
        onSubmit: async (values, { resetForm, setSubmitting }) => {
            try {
                await saveContactMessage(values);
                resetForm();
                Swal.fire({ icon: "success", title: "Message Sent!", text: "We'll get back to you shortly.", confirmButtonColor: "#1e40af" });
            } catch {
                Swal.fire({ icon: "error", title: "Error", text: "Could not send message. Please try again.", confirmButtonColor: "#1e40af" });
            } finally {
                setSubmitting(false);
            }
        },
    });

    const f = (name) => ({
        ...formik.getFieldProps(name),
        className: `form-control ${formik.touched[name] && formik.errors[name] ? "is-invalid" : ""}`,
    });

    return (
        <div>
            <div style={{ background: "linear-gradient(135deg,#1e3a8a,#1e40af)", color: "#fff", padding: "4rem 0" }}>
                <div className="container text-center">
                    <h2 className="fw-bold mb-2">Get In Touch</h2>
                    <p style={{ opacity: .85 }}>We're here to help. Send us a message and we'll respond as soon as possible.</p>
                </div>
            </div>

            <div className="container py-5">
                <div className="row g-5">
                    <div className="col-lg-4">
                        <h5 className="fw-bold mb-4">Contact Information</h5>
                        {[
                            { icon: <FiMapPin />, label: "Address", value: "123 University Avenue, Academic City" },
                            { icon: <FiPhone />, label: "Phone", value: "+1 (555) 000-0000" },
                            { icon: <FiMail />, label: "Email", value: "info@campus.edu" },
                            { icon: <FiClock />, label: "Office Hours", value: "Mon–Fri: 8:00 AM – 5:00 PM" },
                        ].map((item) => (
                            <div key={item.label} className="d-flex gap-3 mb-4">
                                <div style={{ width: 40, height: 40, borderRadius: ".5rem", background: "#eff6ff", color: "#1e40af", display: "flex", alignItems: "center", justifyContent: "center", flexShrink: 0 }}>
                                    {item.icon}
                                </div>
                                <div>
                                    <div style={{ fontSize: ".75rem", fontWeight: 600, color: "#64748b", textTransform: "uppercase", letterSpacing: ".05em" }}>{item.label}</div>
                                    <div style={{ fontSize: ".88rem", color: "#1e293b" }}>{item.value}</div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className="col-lg-8">
                        <div className="card p-4 border-0 shadow-sm">
                            <h5 className="fw-bold mb-4">Send a Message</h5>
                            <form onSubmit={formik.handleSubmit} noValidate>
                                <div className="row g-3">
                                    <div className="col-md-6">
                                        <label className="form-label">Full Name</label>
                                        <input {...f("name")} placeholder="John Smith" />
                                        {formik.touched.name && formik.errors.name && <div className="invalid-feedback">{formik.errors.name}</div>}
                                    </div>
                                    <div className="col-md-6">
                                        <label className="form-label">Email Address</label>
                                        <input type="email" {...f("email")} placeholder="john@example.com" />
                                        {formik.touched.email && formik.errors.email && <div className="invalid-feedback">{formik.errors.email}</div>}
                                    </div>
                                    <div className="col-12">
                                        <label className="form-label">Subject</label>
                                        <input {...f("subject")} placeholder="How can we help?" />
                                        {formik.touched.subject && formik.errors.subject && <div className="invalid-feedback">{formik.errors.subject}</div>}
                                    </div>
                                    <div className="col-12">
                                        <label className="form-label">Message</label>
                                        <textarea rows={5} {...f("message")} placeholder="Your message..." style={{ resize: "none" }} />
                                        {formik.touched.message && formik.errors.message && <div className="invalid-feedback">{formik.errors.message}</div>}
                                    </div>
                                    <div className="col-12">
                                        <button type="submit" className="btn btn-primary px-4 d-flex align-items-center gap-2" disabled={formik.isSubmitting}>
                                            <FiSend size={14} /> {formik.isSubmitting ? "Sending..." : "Send Message"}
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ContactPage;
