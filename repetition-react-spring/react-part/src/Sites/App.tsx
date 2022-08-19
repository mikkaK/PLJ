import React from 'react';
import '../Css/App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import DetailPage from "./DetailPage";
import LogInPage from "./LogInPage";
import RegisterPage from "./RegisterPage";
import Navbar from "../Components/Navbar";
import LandingPage from "./LandingPage";
import AddPage from "./AddPage";

function App() {
    return (
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Navbar/>} />
                        <Route index element={<LandingPage />}/>
                        <Route path="detail/:id" element={<DetailPage/>}/>
                        <Route path="register" element={<RegisterPage/>}/>
                        <Route path="login" element={<LogInPage/>}/>
                        <Route path="add" element={<AddPage />}/>
                </Routes>
            </BrowserRouter>
    );
}

export default App;
