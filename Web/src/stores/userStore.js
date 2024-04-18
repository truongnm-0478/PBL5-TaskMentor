import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        user: JSON.parse(localStorage.getItem('user')) || null,
        accessToken: localStorage.getItem('accessToken') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        isLoggedIn: localStorage.getItem('isLoggedIn') === 'true' || false,
    }),
    getters: {
        getUsername: (state) => state.user ? state.user.username : null,
        getUserRole: (state) => state.user ? state.user.role : null,
        getUser: (state) => state.user,
    },
    actions: {
        setUser(user) {
            this.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        },
        setAccessToken(token) {
            this.accessToken = token;
            localStorage.setItem('accessToken', token);
        },
        setRefreshToken(token) {
            this.refreshToken = token;
            localStorage.setItem('refreshToken', token);
        },
        setIsLoggedIn(value) {
            this.isLoggedIn = value;
            localStorage.setItem('isLoggedIn', value.toString());
        },
        clearUser() {
            this.user = null;
            this.accessToken = null;
            this.refreshToken = null;
            this.isLoggedIn = false;
            localStorage.removeItem('user');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('isLoggedIn');
        },
    },
})
