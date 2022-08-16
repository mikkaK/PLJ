import {AxiosInstance} from "axios";
import Api from "./Api";

export const RegisterService = (api: AxiosInstance = Api) => ({
    registerUser: async (email: string, password: string) => {
        await api.post('register', {
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
