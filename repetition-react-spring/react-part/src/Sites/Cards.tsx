import React, {useEffect, useState} from "react";
import './../Css/App.css';
import {AuthorService} from "../Services/AuthorService";
import {Author} from "../Types/Author";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";


const Cards = () => {
    const [authors, setAuthors] = useState<Author[]>([]);
    let [filteredAuthors, setFilteredAuthors] = useState<Author[]>(authors);

    if (filteredAuthors.length === 0) {
        filteredAuthors = authors;
        console.log(filteredAuthors)
    }

    useEffect(() => {
        AuthorService().getAllEmployees()
            .then((data) => setAuthors(data));
    },[]);

    const handleFilter = (event: { target: { value: string; }; }) => {
        const searchWord = event.target.value.toLowerCase();
        let newFilter = authors.filter((value) => {
            return Object.values(value).join("").toLowerCase().includes(searchWord)
        })
        setFilteredAuthors(newFilter);
    }

    return (
        <div className="wrapper">
            <div className="search-wrapper">
                <label htmlFor="search-form">
                    <input
                        type="search"
                        name="search-form"
                        id="search-form"
                        className="search-input"
                        placeholder="Search for..."
                        onChange={handleFilter}
                    />
                    <span className="sr-only">Search countries here</span>
                </label>
                <Link to={"/add"} style={{color: 'inherit', textDecoration: 'inherit'}}>
                    <Button>Add Employee</Button>
                </Link>
            </div>
            <ul className="card-grid">
                {filteredAuthors.map((auth:Author, i: number) => (
                    <>
                    <Link to={"detail/" + auth.authorId} style={{color: 'inherit', textDecoration: 'none'}}>
                    <li>
                        <article className="card" key={i}>
                            <div className="card-content">
                                <h2 className="card-name">{auth.name}</h2>
                                <ol className="card-list">
                                    <li>
                                        ID: <span>{auth.authorId}</span>
                                    </li>
                                    <li>
                                       Birthday: <span> {auth.birthday}</span>
                                    </li>
                                    <li>
                                        Profile URL: <span>{auth.pp_url}</span>
                                    </li>
                                </ol>
                            </div>
                        </article>
                    </li>
                    </Link></>
                ))}
            </ul>
        </div>
    );
}

export default Cards;
