import axios, {AxiosInstance} from "axios";

const Api: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/',
    timeout: 100000,
})

export default Api;
