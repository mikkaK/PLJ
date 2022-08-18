import * as React from 'react';
import './../Css/Login.css' ;
import {Link, useNavigate} from "react-router-dom";
import {RegisterService} from "../Services/RegisterService";


function RegisterPage() {
    const navigate = useNavigate();
    let handleSubmit = async (event: any) => {
        event.preventDefault();
        RegisterService().registerUser(event.target.email.value, event.target.password.value)
            .then(() => navigate("/"));
    }

    return (
        <div>
            <div id="login-box">
                <div className="left">
                    <h1>Sign up</h1>
                    <form onSubmit={handleSubmit}>
                        <input type="text" name="email" placeholder="E-mail"/>
                        <input type="password" name="password" placeholder="Password"/>
                        <Link to={"/"}>
                            <input type="submit" name="signup_submit" value="Sign me up"/>
                        </Link>
                    </form>
                </div>

                <div className="right">
                    <Link to={"/login"}>
                        <button className="social-signin">Log In</button>
                    </Link>
                </div>
                <div className="or">OR</div>
            </div>
        </div>
    )
}

export default RegisterPage;
