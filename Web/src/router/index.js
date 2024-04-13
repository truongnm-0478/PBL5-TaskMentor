import { createRouter, createWebHistory } from 'vue-router'
import ErrorView from '../views/Error.vue'
import HomeView from '../views/Home.vue'
import LoginView from '../views/Login.vue'
import RegisterView from '../views/Register.vue'
import Appointment from '../views/Student/Appointment.vue'


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
    { path: '/:pathMatch(.*)*', redirect: { name: 'notfound'} },

]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})


export default router