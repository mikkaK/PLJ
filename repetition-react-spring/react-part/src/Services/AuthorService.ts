import {AxiosInstance} from "axios";
import Api from "./Api";

export const AuthorService = (api: AxiosInstance = Api) => ({
    getAllEmployees: async () => {
        const data = await api.get('author');
        return data['data'];
    },
    getAuthorId: async (id: any) => {
        const data = await api.get('author/' + id);
        return data['data'];
    }
});
