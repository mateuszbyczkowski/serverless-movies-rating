import axios from 'axios'


class MovieService {

  // private
  url = 'https://mz3ivtfl4i.execute-api.eu-west-1.amazonaws.com/dev/movies';

  // private 
  constructor() {

  }

  // private
  static instance;

  static getInstance() {
    if (!MovieService.instance) MovieService.instance = new MovieService();
    return MovieService.instance; 
  }

  getMovies() {
    return axios.get(this.url)
      .then(response => response.data);
  }

    rateMovie(id, rate) {
      return axios.put(`${this.url}/${id}`, {'rate': rate})
      .then(response => response.data);
    }

     addMovie(movie) {
        return axios.post(this.url, movie)
    }

    deleteMovie(id) {
      return axios.delete(`${this.url}/${id}`)
      .then(response => response.data);
    }
}

export default MovieService
