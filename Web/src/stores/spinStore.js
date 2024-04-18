import { defineStore } from 'pinia';

export const useSpinStore = defineStore('spin', {
    state: () => ({
        loading: false,
    }),
    actions: {
        startLoading() {
            this.loading = true
        },
        stopLoading() {
            this.loading = false
        },
    },
});
