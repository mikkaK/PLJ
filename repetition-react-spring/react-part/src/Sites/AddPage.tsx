import React from "react";
import './../Css/DetailPage.css';
import {useNavigate} from "react-router-dom";
import AddService from "../Services/AddService";
import {Author} from "../Types/Author";

function AddPage() {

    const navigate = useNavigate();
    const dateregex = "((0[1-9]|[12]\\d|3[01]).(0[1-9]|1[0-2]).[12]\\d{3})";
    const handleSubmit = async (event: any) => {
        event.preventDefault();
        const newAuth: Author = {
            authorId: 0,
            birthday: event.target.birthdate.value,
            name: event.target.name.value,
            pp_url: event.target.linkie.value,
        };
        AddService().addAuthor(newAuth).then(() => navigate("/"));
    };

    return (
        <div>
            <div>
                <article className="card detail">
                    <div className="card-content">
                        <div>
                            <form onSubmit={handleSubmit}>
                                <h2 className="card-name">Add Author</h2>
                                <ol className="card-list">
                                    <li>
                                        First Name: <span><input name="name" type={"text"} placeholder={"..."}
                                                                 required={true} maxLength={100} minLength={2}/></span>
                                    </li>
                                    <li>
                                        Birth Date: <span><input name="birthdate" type={"text"}
                                                                 placeholder={"dd.mm.yyyy"} required={true}
                                                                 pattern={dateregex}/></span>
                                    </li>
                                    <li>
                                        Profile Picture URL: <span><input name="linkie" type={"url"} placeholder={"..."}
                                                                 required={true} maxLength={100} minLength={2}/></span>
                                    </li>
                                    <input type={"submit"}/>
                                </ol>
                            </form>
                        </div>
                    </div>
                </article>
            </div>
        </div>
    );
}

export default AddPage;
