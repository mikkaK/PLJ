import {AxiosInstance} from "axios";
import Api from "./Api";

export const LogInService = (api: AxiosInstance = Api) => ({
    loginUser: async (email: string, password: string) => {
        await api.post('login', {
            email: email,
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
