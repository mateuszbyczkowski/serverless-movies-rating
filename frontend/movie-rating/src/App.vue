<template>
    <div class="container">
        <div id="app">
            <img alt="App logo" class="small-img" src="./assets/logo.png">
            <Home title="Movie Rating application"/>
            <MovieRating title="You can rate your movies here!"/>
        </div>
    </div>
</template>

<script>
    import Home from './components/Home.vue'
    import MovieRating from "./components/MovieRating";

    export default {
        name: 'app',
        components: {
            MovieRating,
            Home
        }
    }
</script>

<style>
    .container {
        height: 100vh;
        background-color: goldenrod;
    }

    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
        background-color: goldenrod;
    }

    .small-img {
        max-width: 300px;
    }
</style>
