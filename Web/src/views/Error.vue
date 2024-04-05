<template>
    <div id="notfound">
        <div class="notfound-bg"></div>
            <div class="notfound">
                <div class="notfound-404">
                    <h1>404</h1>
                </div>
                <h2>we are sorry, but the page you requested was not found</h2>
                <router-link class="home-btn" to="/">Go Home</router-link>
                <a href="#" class="contact-btn">Contact us</a>
            <div class="notfound-social">
                <a class="btn btn-floating m-1 text-white" role="button">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1.2em" viewBox="0 0 320 512">
                    <path d="M279.14 288l14.22-92.66h-88.91v-60.13c0-25.35 12.42-50.06 52.24-50.06h40.42V6.26S260.43 0 225.36 0c-73.22 0-121.08 44.38-121.08 124.72v70.62H22.89V288h81.39v224h100.17V288z"/></svg>
                </a>
                <a class="btn btn-floating m-1 text-white" role="button">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1.2em" viewBox="0 0 496 512">
                    <path d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z"/></svg>
                </a>
                <a class="btn btn-floating m-1 text-white" role="button">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1.2em" viewBox="0 0 488 512">
                    <path d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"/></svg>
                </a>
                <a class="btn btn-floating m-1 text-white" role="button">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1.2em" viewBox="0 0 448 512">
                    <path d="M224.1 141c-63.6 0-114.9 51.3-114.9 114.9s51.3 114.9 114.9 114.9S339 319.5 339 255.9 287.7 141 224.1 141zm0 189.6c-41.1 0-74.7-33.5-74.7-74.7s33.5-74.7 74.7-74.7 74.7 33.5 74.7 74.7-33.6 74.7-74.7 74.7zm146.4-194.3c0 14.9-12 26.8-26.8 26.8-14.9 0-26.8-12-26.8-26.8s12-26.8 26.8-26.8 26.8 12 26.8 26.8zm76.1 27.2c-1.7-35.9-9.9-67.7-36.2-93.9-26.2-26.2-58-34.4-93.9-36.2-37-2.1-147.9-2.1-184.9 0-35.8 1.7-67.6 9.9-93.9 36.1s-34.4 58-36.2 93.9c-2.1 37-2.1 147.9 0 184.9 1.7 35.9 9.9 67.7 36.2 93.9s58 34.4 93.9 36.2c37 2.1 147.9 2.1 184.9 0 35.9-1.7 67.7-9.9 93.9-36.2 26.2-26.2 34.4-58 36.2-93.9 2.1-37 2.1-147.8 0-184.8zM398.8 388c-7.8 19.6-22.9 34.7-42.6 42.6-29.5 11.7-99.5 9-132.1 9s-102.7 2.6-132.1-9c-19.6-7.8-34.7-22.9-42.6-42.6-11.7-29.5-9-99.5-9-132.1s-2.6-102.7 9-132.1c7.8-19.6 22.9-34.7 42.6-42.6 29.5-11.7 99.5-9 132.1-9s102.7-2.6 132.1 9c19.6 7.8 34.7 22.9 42.6 42.6 11.7 29.5 9 99.5 9 132.1s2.7 102.7-9 132.1z"/></svg>
                </a>
            </div>
        </div>
    </div>
</template>

<script>
export default {};
</script>

<style scoped>
* {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

#notfound {
    position: relative;
    height: 100vh;
}

#notfound .notfound-bg:after {
    content: "";
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: var(--color-brand);
    
}

#notfound .notfound {
    position: absolute;
    left: 50%;
    top: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}

.notfound {
    max-width: 910px;
    width: 100%;
    line-height: 1.4;
    text-align: center;
}

.notfound .notfound-404 {
    position: relative;
    height: 200px;
}

.notfound .notfound-404 h1 {
    font-family: "Montserrat", sans-serif;
    position: absolute;
    left: 50%;
    top: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    font-size: 220px;
    font-weight: 900;
    margin: 0px;
    color: var(--color-white);
    text-transform: uppercase;
    letter-spacing: 10px;
}

.notfound h2 {
    font-family: "Montserrat", sans-serif;
    font-size: 22px;
    font-weight: 700;
    text-transform: uppercase;
    color: var(--color-white);
    margin-top: 20px;
    margin-bottom: 15px;
}

.notfound .home-btn,
.notfound .contact-btn {
    display: inline-block;
    font-weight: 700;
    text-decoration: none;
    background-color: transparent;
    border: 2px solid transparent;
    text-transform: uppercase;
    padding: 13px 25px;
    font-size: 18px;
    border-radius: 40px;
    margin: 7px;
    -webkit-transition: 0.2s all;
    transition: 0.2s all;
}

.notfound .home-btn:hover,
.notfound .contact-btn:hover {
    opacity: 0.9;
}

.notfound .home-btn {
    color: var(--color-brand);
    background: #fff;
}

.notfound .contact-btn {
    border: 2px solid rgba(255, 255, 255, 0.9);
    color: rgba(255, 255, 255, 0.9);
}

.notfound-social {
    margin-top: 25px;
}

.notfound-social > a {
    height: 40px;
    width: 40px;
    font-size: 14px;
    -webkit-transition: 0.2s all;
    transition: 0.2s all;
}

.notfound-social > a svg {
    fill: var(--color-white);
}

.notfound-social > a:hover  {
    background-color: var(--color-white);
    border-radius: 50%;
}

.notfound-social > a:hover svg {
    fill: var(--color-brand);
}

@media only screen and (max-width: 767px) {
    .notfound .notfound-404 h1 {
        font-size: 182px;
    }
}

@media only screen and (max-width: 480px) {
    .notfound .notfound-404 {
        height: 146px;
    }
    .notfound .notfound-404 h1 {
        font-size: 146px;
    }
    .notfound h2 {
        font-size: 16px;
    }
    .notfound .home-btn,
    .notfound .contact-btn {
        font-size: 14px;
    }
}
</style>