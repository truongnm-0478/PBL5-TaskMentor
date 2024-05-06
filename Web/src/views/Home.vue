<template>
    <a-flex class="container-homepage" gap="middle" align="center" justify="center" vertical>
        <span class="welcome"> Welcome to Task Mentor ! </span>
        <span id="typed" class="slogan"></span>
        <div class="icon-container">
            <InstagramOutlined class="icon"/>
            <LinkedinOutlined class="icon"/>
            <FacebookOutlined class="icon"/>
            <GithubOutlined class="icon"/>
        </div>
        <div class="shape1"></div>
        <div class="shape2"></div>
        <div class="shape3"></div>
    </a-flex>
</template>

<script setup>
import {InstagramOutlined, LinkedinOutlined, FacebookOutlined, GithubOutlined} from '@ant-design/icons-vue'
import Typed from 'typed.js'
import {onMounted} from "vue";

onMounted(() => {
    const options = {
        strings: ['Da Nang University - University of Science and Technology'],
        typeSpeed: 60,
        backSpeed: 60,
        loop: true
    }

    const typed = new Typed('#typed', options)
})
</script>

<style scoped>
.container-homepage {
    height: calc(100vh - 100px);
    border-radius: 10px;
    background-color: var(--color-white);
    position: relative;
    //overflow: hidden;
}

.container-homepage .welcome {
    font-size: 3.2vw;
    color: var(--color-blue);
    text-transform: uppercase;
    font-weight: bold;
    position: relative;
    z-index: 2;
}

.container-homepage .slogan {
    font-size: 1.2vw;
    color: #1A5DB6;
    height: 30px;
    position: relative;
    z-index: 2;
}

.icon-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

.icon {
    font-size: 2.3vw;
    color: var(--color-blue);
    cursor: pointer;
    margin: 0 6px;
    position: relative;
    z-index: 2;
}

.icon:hover {
    color: #1A5DB6;
}

.shape1 {
    background-color: #E6F4FF;
    height: 500px;
    width: 500px;
    position: absolute;
    border-radius: 300px;
    top: -80px;
    left: -70px;
}
.shape2 {
    background-color: #E6F4FF;
    height: 400px;
    width: 400px;
    position: absolute;
    border-radius: 300px;
    right: -80px;
    bottom: 70px;
}
.shape3 {
    background-color: #E6F4FF;
    height: 40px;
    width: 40px;
    position: absolute;
    border-radius: 300px;
    left: 80px;
    bottom: 70px;
}

</style>
