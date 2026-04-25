import { createBrowserRouter } from "react-router-dom";
import {
    AboutPage,
    ContactPage,
    HomePage,
    AdminPage,
    ChooseLessonPage,
    ContactMessagesPage,
    CoursesPage,
    DashboardPage,
    DeanPage,
    EventsPage,
    GradesMeetsPage,
    LessonsPage,
    LoginPage,
    MeetPage,
    NotFoundPage,
    StudentInfoPage,
    StudentPage,
    TeacherPage,
    UnauthorizedPage,
    ViceDeanPage,
} from "../pages/pages";
import ProtectedRoute from "./protected-route";
import UserLayout from "../layouts/user-layout";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <UserLayout />,
        children: [
            { index: true, element: <HomePage /> },
            { path: "about", element: <AboutPage /> },
            { path: "contact", element: <ContactPage /> },
            { path: "courses", element: <CoursesPage /> },
            { path: "events", element: <EventsPage /> },
            { path: "login", element: <LoginPage /> },
            { path: "unauthorized", element: <UnauthorizedPage /> },
            {
                path: "dashboard",
                element: <ProtectedRoute />,
                children: [
                    { index: true, element: <DashboardPage /> },
                    {
                        element: <ProtectedRoute roles={["ADMIN"]} />,
                        children: [
                            { path: "admin-management", element: <AdminPage /> },
                            { path: "dean-management", element: <DeanPage /> },
                            { path: "vice-dean-management", element: <ViceDeanPage /> },
                            { path: "contact-messages", element: <ContactMessagesPage /> },
                            { path: "lessons-management", element: <LessonsPage /> },
                        ],
                    },
                    {
                        element: <ProtectedRoute roles={["ADMIN", "MANAGER"]} />,
                        children: [
                            { path: "teacher-management", element: <TeacherPage /> },
                        ],
                    },
                    {
                        element: <ProtectedRoute roles={["ADMIN", "MANAGER", "ASSISTANT_MANAGER"]} />,
                        children: [
                            { path: "student-management", element: <StudentPage /> },
                        ],
                    },
                    {
                        element: <ProtectedRoute roles={["TEACHER"]} />,
                        children: [
                            { path: "student-info-management", element: <StudentInfoPage /> },
                            { path: "meet-management", element: <MeetPage /> },
                        ],
                    },
                    {
                        element: <ProtectedRoute roles={["STUDENT"]} />,
                        children: [
                            { path: "choose-lesson", element: <ChooseLessonPage /> },
                        ],
                    },
                    {
                        element: <ProtectedRoute roles={["TEACHER", "STUDENT"]} />,
                        children: [
                            { path: "grades-meets", element: <GradesMeetsPage /> },
                        ],
                    },
                ],
            },
        ],
    },
    { path: "*", element: <NotFoundPage /> },
]);
