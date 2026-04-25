///  ADMIN COMPONENTS ///
// 1. Admin Page
import AdminList from "./admin/admin-page/admin-list/admin-list";
import NewAdminForm from "./admin/admin-page/new-admin-form/new-admin-form";
// 2.Choose Lesson Page
import LessonProgramListSelected from "./admin/choose-lesson-page/lesson-program-list-selected/lesson-program-list-selected";
import LessonProgramListUnselected from "./admin/choose-lesson-page/lesson-program-list-unselected/lesson-program-list-unselected";
// 3. Contact Messages Page
import ContactMessageList from "./admin/contact-messages-page/contact-message-list/contact-message-list";
// 4. Dashboard Page
import Navigation from "./admin/dashboard-page/navigation/navigation";
// 5. Dean Page
import DeanList from "./admin/dean-page/dean-list/dean-list";
import EditDeanForm from "./admin/dean-page/edit-dean-form/edit-dean-form";
import NewDeanForm from "./admin/dean-page/new-dean-form/new-dean-form";
// 6. Grades Meets Page
import GradesList from "./admin/grades-meets-page/grades-list/grades-list";
import GradesMeetsList from "./admin/grades-meets-page/grades-meets-list/grades-meets-list";
// 7. Lesson Page
import EducationTermList from "./admin/lesson-page/education-term-list/education-term-list";
import LessonList from "./admin/lesson-page/lesson-list/lesson-list";
import LessonProgramList from "./admin/lesson-page/lesson-program-list/lesson-program-list";
import LessonProgramListUnassigned from "./admin/lesson-page/lesson-program-list-unassigned/lesson-program-list-unassigned";
import NewEducationTermForm from "./admin/lesson-page/new-education-term-form/new-education-term-form";
import NewLessonForm from "./admin/lesson-page/new-lesson-form/new-lesson-form";
import NewLessonProgramForm from "./admin/lesson-page/new-lesson-program-form/new-lesson-program-form";
// 8. Meet Page
import EditMeetForm from "./admin/meet-page/edit-meet-form/edit-meet-form";
import MeetList from "./admin/meet-page/meet-list/meet-list";
import NewMeetForm from "./admin/meet-page/new-meet-form/new-meet-form";
// 9. Student Info Page
import EditStudentInfoForm from "./admin/student-info-page/edit-student-info-form/edit-student-info-form";
import NewStudentInfoForm from "./admin/student-info-page/new-student-info-form/new-student-info-form";
import StudentInfoList from "./admin/student-info-page/student-info-list/student-info-list";
// 10. Student Page
import EditStudentForm from "./admin/student-page/edit-student-form/edit-student-form";
import NewStudentForm from "./admin/student-page/new-student-form/new-student-form";
import StudentList from "./admin/student-page/student-list/student-list";
// 11. Teacher Page
import EditTeacherForm from "./admin/teacher-page/edit-teacher-form/edit-teacher-form";
import NewTeacherForm from "./admin/teacher-page/new-teacher-form/new-teacher-form";
import TeacherList from "./admin/teacher-page/teacher-list/teacher-list";
// 12. Vice Dean Page
import EditViceDeanForm from "./admin/vice-dean-page/edit-vice-dean-form/edit-vice-dean-form";
import NewViceDeanForm from "./admin/vice-dean-page/new-vice-dean-form/new-vice-dean-form";
import ViceDeanList from "./admin/vice-dean-page/vice-dean-list/vice-dean-list";

///  COMMON COMPONENTS ///
import ButtonSpinner from "./common/button-spinner/button-spinner";
import Footer from "./common/footer/footer";
import Menubar from "./common/menubar/menubar";
import MobileApp from "./common/mobile-app/mobile-app";
import PageHeader from "./common/page-header/page-header";
import PasswordInput from "./common/password-input/password-input";
import Spacer from "./common/spacer/spacer";
import Topbar from "./common/topbar/topbar";
import UserMenu from "./common/user-menu/user-menu";

///  USER COMPONENTS ///
// 1. About Page
import InstructorCard from "./user/about-page/instructor-card/instructor-card";
import Instructors from "./user/about-page/instructors/instructors";
import Welcome from "./user/about-page/welcome/welcome";

// 2. Contact Page
import Contact from "./user/contact-page/contact/contact";
import ContactForm from "./user/contact-page/contact-form/contact-form";
import GetInTouch from "./user/contact-page/get-in-touch/get-in-touch";
// 3. Courses Page
import CourseCard from "./user/courses-page/course-card/course-card";
import Courses from "./user/courses-page/courses/courses";
// 4. Events Page
import EventCard from "./user/events-page/event-card/event-card";
import Events from "./user/events-page/events/events";
// 5. Home Page
import FeaturedCourses from "./user/home-page/featured-courses/featured-courses";
import Slider from "./user/home-page/slider/slider";
import UpcomingEvents from "./user/home-page/upcoming-events/upcoming-events";
// 6. Login Page
import LoginForm from "./user/login-page/login-form/login-form";

export {
    /// ADMIN COMPONENTS ///
    AdminList,
    NewAdminForm,
    LessonProgramListSelected,
    LessonProgramListUnselected,
    ContactMessageList,
    Navigation,
    DeanList,
    EditDeanForm,
    NewDeanForm,
    GradesList,
    GradesMeetsList,
    EducationTermList,
    LessonList,
    LessonProgramList,
    LessonProgramListUnassigned,
    NewEducationTermForm,
    NewLessonForm,
    NewLessonProgramForm,
    EditMeetForm,
    MeetList,
    NewMeetForm,
    EditStudentInfoForm,
    NewStudentInfoForm,
    StudentInfoList,
    EditStudentForm,
    NewStudentForm,
    StudentList,
    EditTeacherForm,
    NewTeacherForm,
    TeacherList,
    EditViceDeanForm,
    NewViceDeanForm,
    ViceDeanList,

    /// COMMON COMPONENTS ///
    ButtonSpinner,
    Footer,
    Menubar,
    MobileApp,
    PageHeader,
    PasswordInput,
    Spacer,
    Topbar,
    UserMenu,

    /// USER COMPONENTS ///
    InstructorCard,
    Instructors,
    Welcome,
    Contact,
    ContactForm,
    GetInTouch,
    CourseCard,
    Courses,
    EventCard,
    Events,
    FeaturedCourses,
    Slider,
    UpcomingEvents,
    LoginForm,
};