import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {Author} from "../Types/Author";
import {AuthorService} from "../Services/AuthorService";
import './../Css/DetailPage.css'
import DeleteService from "../Services/DeleteService";
import UpdateService from "../Services/UpdateService";
import {Button} from "@mui/material";

const DetailPage = () => {

    let {id} = useParams();

    let idINT: number;
    if (typeof id === "string") {
        idINT = parseInt(id);
    }


    const [author, setAuthor] = useState<Author | undefined>(undefined);
    const [editMode, setEditMode] = useState<boolean>(false);

    useEffect(() => {
        AuthorService().getAuthorId(id)
            .then((data) => setAuthor(data))
            .catch(function (error) {
                console.log(error);
            });
    }, []);
    const navigate = useNavigate();
    const dateregex = "((0[1-9]|[12]\\d|3[01]).(0[1-9]|1[0-2]).[12]\\d{3})";
    let hasData: boolean = author !== undefined;

    const handleDelete = () => {
        // eslint-disable-next-line no-restricted-globals
        let confirmAction: boolean = confirm("Are you sure you want to delete " + (author ? author.name : '') + " from the database?");
        if (confirmAction) {
            DeleteService().deleteUser(idINT).then(() => navigate("/"));
        }
    };

    const handleEdit = () => {
        if (!editMode) {
            setEditMode(true);
        } else {
            setEditMode(false);
        }

    };

    const handleSubmit = async (event: any) => {
        event.preventDefault();
        const newAuth: Author = {
            authorId: idINT,
            birthday: event.target.birthdate.value,
            name: event.target.firstname.value,
            pp_url: event.target.linkie.value,
        };
        UpdateService().UpdateAuthor(newAuth, idINT).then(() => setEditMode(false));
    };

    return (
        <div>
            <div>
                {hasData ?
                    <article className="card detail" key={id}>
                        <div className="card-content">
                            {editMode ?
                                <div>
                                    <form onSubmit={handleSubmit}>
                                        <h2 className="card-name">First Name: <span><input name="firstname"
                                                                                           type={"text"}
                                                                                           defaultValue={author ? author.name : ''}
                                                                                           maxLength={100}
                                                                                           minLength={2}/></span>
                                        </h2>
                                        <ol className="card-list">
                                            <li>
                                                Birthday: <span><input name="birthdate" type={"text"}
                                                                         defaultValue={author ? author.birthday : ''}
                                                                         pattern={dateregex}/></span>
                                            </li>
                                            <li>
                                                Profil URL: <span><input name="linkie" type={"text"}
                                                                         defaultValue={author ? author.pp_url : ''}
                                                                        /></span>
                                            </li>
                                            <li>
                                                Id: <span>{author ? author.authorId : ''}</span>
                                            </li>
                                            <input type={"submit"}/>
                                        </ol>
                                    </form>
                                </div>
                                :
                                <div>
                                    <h2 className="card-name">{author ? author.name : ''}</h2>
                                    <ol className="card-list">
                                        <li>
                                            Birthday: <span>{author ? author.birthday : ''}</span>
                                        </li>
                                        <li>
                                            Profil URL: <span>{author ? author.pp_url : ''}</span>
                                        </li>
                                        <li>
                                            Id: <span>{author ? author.authorId : ''}</span>
                                        </li>
                                    </ol>
                                </div>
                            }
                        </div>
                    </article>
                    :
                    <> </>
                }
                <div className="bottons">
                    <Button onClick={handleDelete}>Delete</Button>
                    <Button onClick={handleEdit}>{editMode ? "Cancel" : "edit"}</Button>
                </div>
            </div>
        </div>
    );

}

export default DetailPage;
