import React from "react";

const field = (formik, name, type = "text", placeholder = "") => ({
    id: name,
    type,
    placeholder,
    className: `form-control ${formik.touched[name] && formik.errors[name] ? "is-invalid" : ""}`,
    ...formik.getFieldProps(name),
});

const UserFormFields = ({ formik }) => (
    <div className="row g-3">
        <div className="col-md-6">
            <label className="form-label">Username</label>
            <input {...field(formik, "username", "text", "e.g. jsmith")} />
            {formik.touched.username && formik.errors.username && <div className="invalid-feedback">{formik.errors.username}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Name</label>
            <input {...field(formik, "name", "text", "First name")} />
            {formik.touched.name && formik.errors.name && <div className="invalid-feedback">{formik.errors.name}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Surname</label>
            <input {...field(formik, "surname", "text", "Last name")} />
            {formik.touched.surname && formik.errors.surname && <div className="invalid-feedback">{formik.errors.surname}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Password</label>
            <input {...field(formik, "password", "password", "Min 8 characters")} />
            {formik.touched.password && formik.errors.password && <div className="invalid-feedback">{formik.errors.password}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Date of Birth</label>
            <input {...field(formik, "birthDay", "date")} />
            {formik.touched.birthDay && formik.errors.birthDay && <div className="invalid-feedback">{formik.errors.birthDay}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Birth Place</label>
            <input {...field(formik, "birthPlace", "text", "City")} />
            {formik.touched.birthPlace && formik.errors.birthPlace && <div className="invalid-feedback">{formik.errors.birthPlace}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">SSN</label>
            <input {...field(formik, "ssn", "text", "###-##-####")} />
            {formik.touched.ssn && formik.errors.ssn && <div className="invalid-feedback">{formik.errors.ssn}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Phone Number</label>
            <input {...field(formik, "phoneNumber", "text", "555-555-5555")} />
            {formik.touched.phoneNumber && formik.errors.phoneNumber && <div className="invalid-feedback">{formik.errors.phoneNumber}</div>}
        </div>
        <div className="col-md-6">
            <label className="form-label">Gender</label>
            <select className={`form-select ${formik.touched.gender && formik.errors.gender ? "is-invalid" : ""}`} {...formik.getFieldProps("gender")}>
                <option value="">Select gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
            </select>
            {formik.touched.gender && formik.errors.gender && <div className="invalid-feedback">{formik.errors.gender}</div>}
        </div>
    </div>
);

export const baseUserSchema = {
    username: (Yup) => Yup.string().min(4).max(16).required("Username is required"),
    name: (Yup) => Yup.string().min(4).max(16).required("Name is required"),
    surname: (Yup) => Yup.string().min(4).max(16).required("Surname is required"),
    password: (Yup) => Yup.string().min(8).max(60).required("Password is required"),
    birthDay: (Yup) => Yup.string().required("Birth date is required"),
    birthPlace: (Yup) => Yup.string().min(2).max(16).required("Birth place is required"),
    ssn: (Yup) => Yup.string().matches(/^\d{3}-\d{2}-\d{4}$/, "Format: ###-##-####").required("SSN is required"),
    phoneNumber: (Yup) => Yup.string().length(12).required("Phone is required"),
    gender: (Yup) => Yup.string().required("Gender is required"),
};

export const baseInitialValues = {
    username: "", name: "", surname: "", password: "",
    birthDay: "", birthPlace: "", ssn: "", phoneNumber: "", gender: "",
};

export default UserFormFields;
