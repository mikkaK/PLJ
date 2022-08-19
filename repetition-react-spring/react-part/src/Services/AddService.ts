import {AxiosInstance} from "axios";
import {Author} from "../Types/Author";
import Api from "./Api";

export const UpdateService = (api: AxiosInstance = Api) => ({
    addAuthor : async (author: Author) => {
        await api.post('author/', {
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
