import {AxiosInstance} from "axios";
import Api from "./Api";

export const RegisterService = (api: AxiosInstance = Api) => ({
    registerUser: async (username: string, password: string) => {
        await api.post('user/', {
            username: username,
            password: password
        })
            .then(function (response) {
                localStorage.setItem('Token', response.data.accessToken);
                localStorage.setItem('isLoggedIn', 'true');
            })
            .catch(function (error) {
                console.log(error);
            })
    }
});
