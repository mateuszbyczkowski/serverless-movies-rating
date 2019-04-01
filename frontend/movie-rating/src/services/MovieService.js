import axios from 'axios'

const url = 'https://mz3ivtfl4i.execute-api.eu-west-1.amazonaws.com/dev/movies';

class MovieService {
    static getMovies() {
        return new Promise(async (resolve, reject) => {
            try {
                const serverResponse = await axios.get(url);
                const unparsedData = serverResponse.data;
                resolve(unparsedData.map(movies => ({
                    ...movies
                })))
            } catch (error) {
                reject(error)
            }
        })
    }

    static rateMovie(id, rate) {
        return new Promise(async (resolve, reject) => {
            try {
                const serverResponse = await axios.put(url + "/" + id, {"rate": rate});
                const unparsedData = serverResponse.data;
                resolve(unparsedData.map(movies => ({
                    ...movies
                })))
            } catch (error) {
                reject(error)
            }
        })
    }

    static addMovie(movie) {
        return axios.post(url, movie)
    }

    static deleteMovie(id) {
        return new Promise(async (resolve, reject) => {
            try {
                const serverResponse = await axios.delete(url + "/" + id);
                const unparsedData = serverResponse.data;
                resolve(unparsedData.map(movies => ({
                    ...movies
                })))
            } catch (error) {
                reject(error)
            }
        })
    }
}

export default MovieService
