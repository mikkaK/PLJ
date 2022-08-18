import * as React from 'react';
import './../Css/Login.css';
import {Link, useNavigate} from "react-router-dom";
import {LogInService} from "../Services/LogInService";


function LogInPage() {
    const navigate = useNavigate();
    let handleSubmit = async (event: any) => {
        event.preventDefault();
        LogInService().loginUser(event.target.email.value, event.target.password.value)
            .then(() => (navigate("/")));
    }

    return (
        <div>
            <div id="login-box">
                <div className="left">
                    <h1>Log In</h1>
                    <form onSubmit={handleSubmit}>
                        <input type="text" name="email" placeholder="E-mail"/>
                        <input type="password" name="password" placeholder="Password"/>
                        <input type="submit" name="signup_submit" value="Log me in"/>
                    </form>
                </div>

                <div className="right">
                    <Link to={"/register"}>
                        <button className="social-signin">Register</button>
                    </Link>
                </div>
                <div className="or">OR</div>
            </div>
        </div>
    )
}

export default LogInPage;
