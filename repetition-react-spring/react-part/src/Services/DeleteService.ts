import {AxiosInstance} from "axios";
import Api from "./Api";

export const DeleteService = (api: AxiosInstance = Api) => ({
    deleteUser: async (id: number) => {
        await api.delete('author/' + id, {})
            .then(function () {
            })
            .catch(function (error) {
                console.log(error);
            })
    }
});
export default DeleteService;
