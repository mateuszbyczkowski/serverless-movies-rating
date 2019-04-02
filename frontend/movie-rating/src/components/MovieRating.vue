<template>
    <div class="rating">
        <div class="loading" v-if="loading">
            <div id="loading"></div>
        </div>
        <div class="row" style="z-index: 2">
            <h3>{{ title }}</h3>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Rate</th>
                    <th scope="col">Author</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item, index) in movies" :key="index">
                    <td>{{item.id}}</td>
                    <td>{{item.name}}</td>
                    <td><input type="number" step="any" max="5" min="1" v-model="item.rate"/></td>
                    <td>{{item.author}}</td>
                    <td><a href="#" @click="rateMovie(item.id, item.rate)">Rate movie</a> |
                        <a href="#" @click="deleteMovie(item.id)">Delete</a></td>
                </tr>
                </tbody>
            </table>
        </div>

        <span class="alert" v-if="info !== ''" style="color:blue; background-color: lightblue;">{{info}}</span>
        <span class="alert" v-if="error !== ''" style="color:red; background-color: lightcoral;">{{error}}</span>

        <button style="navbutton_background_color: aliceblue; margin-left: 150px">
            <a href="#add" @click="toggleAdd">Toggle add form</a>
        </button>

        <hr/>
        <div id="add" v-if="showAdd" class="row add-container">
            <form>
                <div>
                    <label for="newMovieTitle" class="label-movie">Name </label>
                    <input v-model="newMovieTitle" type="newMovieTitle" id="newMovieTitle" placeholder="Movie name"/>
                </div>
                <div>
                    <label for="newMovieAuthor" class="label-movie">Author </label>
                    <input v-model="newMovieAuthor" type="newMovieAuthor" id="newMovieAuthor" placeholder="Author   "/>
                </div>
                <div>
                    <label for="newMovieRate" class="label-movie">Rate </label>
                    <input type="number" step="any" max="5" min="1" v-model="newMovieRate" id="newMovieRate"/>
                </div>
                <button type="submit" @click="addMovie" class="btn add-movie">Add movie</button>
            </form>
        </div>
    </div>
</template>

<script>
    import MovieService from "../services/MovieService";

    export default {
        name: 'MovieRating',
        props: {
            title: String
        },
        data() {
            return {
                loading: false,
                showAdd: false,
                movies: null,
                error: '',
                info: '',
                newMovieTitle: '',
                newMovieAuthor: '',
                newMovieRate: Number,
                service: MovieService.getInstance()
            }
        },
        beforeMount() {
            this.fetchData();
        },
        methods: {
            rateMovie: function (id, rate) {
                this.loading = true;
                this.service.rateMovie(id, rate)
                    .then(() => {
                        this.error = '';
                        this.info = 'Successfully rated movie!';
                        this.fetchData();
                        this.loading = false;
                    })
                    .catch(() => {
                        this.info = '';
                        this.error = "Can't rate movie!";
                        this.loading = false;
                    });
            },
            addMovie: function () {
                this.loading = true;
                this.service.addMovie({
                    "name": this.newMovieTitle,
                    "author": this.newMovieAuthor,
                    "rate": this.newMovieRate,
                })
                    .then(() => {
                        this.error = '';
                        this.info = 'Successfully added movie!';
                        this.fetchData();
                        this.loading = false;
                    })
                    .catch(() => {
                        this.info = '';
                        this.error = "Can't add movie!";
                        this.loading = false;
                    });
            },
            deleteMovie: function (id) {
                this.loading = true;
                this.service.deleteMovie(id)
                    .then(() => {
                        this.error = '';
                        this.info = 'Movie deleted';
                        this.fetchData();
                        this.loading = false;
                    })
                    .catch(() => {
                        this.error = "Can't delete movie!";
                        this.info = '';
                        this.loading = false;
                    });
            },
            fetchData() {
                this.loading = true;
                this.service.getMovies()
                    .then(resp => {
                        this.error = '';
                        this.movies = resp;
                        this.loading = false;
                    })
                    .catch(err => {
                        this.error = err;
                        this.info = '';
                        this.loading = false;
                    });
            },
            toggleAdd() {
                this.showAdd = !this.showAdd;
            }
        }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }

    .add-container {
        display: block;
        margin-right: 80px;
        text-align: right;
    }

    .add-movie {
        border: 1px darkgray solid;
        background: wheat;
        margin-right: 30px;
        margin-bottom: 50px;
    }

    .add-movie:hover {
        background: aliceblue;
    }

    .label-movie {
        margin-right: 10px;
    }

    .loading {
        background-color: rgba(0, 0, 0, 0.7);
        z-index: 100;
        width: 100vw;
        height: 120vh;
        position: absolute;
        top: 0;
        left: 0;
        justify-content: center;
        align-items: center;
        display: flex;
    }

    #loading {
        margin-top: -100px;
        display: inline-block;
        width: 50px;
        height: 50px;
        border: 3px solid rgba(255, 255, 255, .3);
        border-radius: 50%;
        border-top-color: #fff;
        animation: spin 1s ease-in-out infinite;
        -webkit-animation: spin 1s ease-in-out infinite;
    }

    @keyframes spin {
        to {
            -webkit-transform: rotate(360deg);
        }
    }

    @-webkit-keyframes spin {
        to {
            -webkit-transform: rotate(360deg);
        }
    }
</style>
