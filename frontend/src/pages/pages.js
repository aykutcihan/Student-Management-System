//ADMIN PAGES
import AdminPage from "./admin/admin-page/admin-page";
import ContactMessagesPage from "./admin/contact-messages-page/contact-messages-page";
import ChooseLessonPage from "./admin/choose-lesson-page/choose-lesson-page";
import DashboardPage from "./admin/dashboard-page/dashboard-page";
// export { default as DeanPage } from "./admin/dean-page/dean-page";
import DeanPage from "./admin/dean-page/dean-page";
import GradesMeetsPage from "./admin/grades-meets-page/grades-meets-page";
import LessonsPage from "./admin/lessons-page/lessons-page";
import MeetPage from "./admin/meet-page/meet-page";
import StudentInfoPage from "./admin/student-info-page/student-info-page";
import StudentPage from "./admin/student-page/student-page";
import TeacherPage from "./admin/teacher-page/teacher-page";
import ViceDeanPage from "./admin/vice-dean-page/vice-dean-page";


//COMMON PAGES

import LoadingPage from "./common/loading-page/loading-page";
import NotFoundPage from "./common/not-found-page/not-found-page";
import UnauthorizedPage from "./common/unauthorized-page/unauthorized-page";

//USER PAGES

import AboutPage from "./user/about-page/about-page";
import ContactPage from "./user/contact-page/contact-page";
import CoursesPage from "./user/courses-page/courses-page";
import EventsPage from "./user/events-page/events-page";
import HomePage from "./user/home-page/home-page";
import LoginPage from "./user/login-page/login-page";



export{
    //ADMIN PAGES
    AdminPage,
    ChooseLessonPage,
    ContactMessagesPage,
    DashboardPage,
    DeanPage,
    GradesMeetsPage,
    LessonsPage,
    MeetPage,
    StudentInfoPage,
    StudentPage,
    TeacherPage,
    ViceDeanPage,

    //COMMON PAGES
    LoadingPage,
    NotFoundPage,
    UnauthorizedPage,

    //USER PAGES
    AboutPage,
    ContactPage,
    CoursesPage,
    EventsPage,
    HomePage,
    LoginPage
};