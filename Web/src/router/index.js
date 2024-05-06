import { createRouter, createWebHistory } from 'vue-router'
import ErrorView from '@/views/Error.vue'
import HomeView from '@/views/Home.vue'
import LoginView from '@/views/Login.vue'
import RegisterView from '@/views/Register.vue'
import Appointment from '@/views/user/Appointment.vue'
import CreateClass from '@/views/user/teacher/CreateClass.vue'
import Kanban from '@/views/user/student/Kanban.vue'
import Requirement from '@/views/user/student/Requirement.vue'
import QRAndCode from '@/views/user/QRAndCode.vue'
import ListClass from '@/views/user/teacher/ListClass.vue'
import JoinClass from '@/views/user/student/JoinClass.vue'
import ListClassStudent from '@/views/user/student/ListClass.vue'
import Class from '@/views/user/Class.vue'
import Notification from '@/views/user/student/Notification.vue'
import User from '@/views/admin/User.vue'
import TeamManager from '@/views/user/student/TeamManagement.vue'
import ProjectManagement from '@/views/user/ProjectManagement.vue'
import ListRequirement from '@/views/user/ListRequirement.vue'
import Chart from '@/views/admin/Chart.vue'
import AppointmentClass from '@/views/user/teacher/AppointmentClass.vue'
import UpdateUser from '@/views/admin/UpdateUser.vue'
import UserDetail from '@/views/admin/UserDetail.vue'
import Teacher from '@/views/admin/Teacher.vue'
import CreateTeacher from '@/views/admin/CreateTeacher.vue'
import ChangePassword from '@/views/ChangePassword.vue'


import DashboardView from '../views/admin/DashboardView.vue'

const routes =  [
    { path: '/', component: HomeView },
    { path: '/manager', name: 'manager', redirect: { name: 'dashboard'},
        children: [
            { path: 'dashboard', name: 'dashboard', component: DashboardView, meta: { layout: 'ManagerLayout' } },
        ]
    },
    { path: '/login', name: 'login', component: LoginView, meta: { layout: 'AuthLayout' } },
    { path: '/register', name: 'register', component: RegisterView, meta: { layout: 'AuthLayout' } },
    { path: '/404', name: 'notfound', component: ErrorView, meta: { layout: 'NotFoundLayout'} },
    { path: '/appointment', name: 'appointment', component: Appointment },
    { path: '/createClass', name: 'createClass', component: CreateClass },
    { path: '/requirement', name: 'requirement', component: Requirement },
    { path: '/kanban', name: 'Kanban', component: Kanban },
    { path: '/QRAndCode', name: 'QRAndCode', component: QRAndCode },
    { path: '/joinClass', name: 'JoinClass', component: JoinClass },
    { path: '/listClass', name: 'ListClass', component: ListClass },
    { path: '/student/team', name: 'TeamManager', component: TeamManager },
    { path: '/student/listClass', name: 'ListClassStudent', component: ListClassStudent },
    { path: '/notification', name: 'Notification', component: Notification },
    { path: '/:pathMatch(.*)*', redirect: { name: 'notfound'} },
    { path: '/class', name: 'Class', component: Class },
    { path: '/project', name: 'ProjectManagement', component: ProjectManagement },
    { path: '/project/requirement', name: 'ListRequirement', component: ListRequirement },
    { path: '/appointment/class', name: 'AppointmentClass', component: AppointmentClass },
    { path: '/change-password', name: 'ChangePassword', component: ChangePassword },

    { path: '/admin/dashboard', name: 'Chart', component: Chart },
    { path: '/admin/user', name: 'User', component: User },
    { path: '/admin/teacher/create', name: 'CreateTeacher', component: CreateTeacher },
    { path: '/admin/teacher', name: 'Teacher', component: Teacher },
    { path: '/admin/user/update/:id', name: 'UpdateUser', component: UpdateUser },
    { path: '/admin/user/detail/:id', name: 'UserDetail', component: UserDetail }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})


export default router