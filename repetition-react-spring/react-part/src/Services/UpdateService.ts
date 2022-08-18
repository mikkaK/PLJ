import {AxiosInstance} from "axios";
import Api from "./Api";
import {Author} from "../Types/Author";


export const UpdateService = (api: AxiosInstance = Api) => ({
    UpdateAuthor: async (author: Author, id: number) => {
        await api.put('author/' + id, {
            authorId: author.authorId,
            birthday: author.birthday,
            pp_url: author.pp_url,
            name: author.name,

        })
            .then(function (response) {
                console.log(response.statusText);
            })
            .catch(function (error) {
                console.log(error);
            })
    }
});
export default UpdateService;
