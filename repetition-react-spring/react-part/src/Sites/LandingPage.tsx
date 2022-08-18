import React, {useEffect, useState} from 'react';
import Cards from "../Sites/Cards";


function LandingPage() {
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(true);
    useEffect(() => {
        return () => {
            if (localStorage.getItem('isLoggedIn') === 'true') {
                setIsLoggedIn(true);
            }
        };
    });

    return (
        <div>
            {isLoggedIn && <Cards/>}
        </div>
    );
}

export default LandingPage;
