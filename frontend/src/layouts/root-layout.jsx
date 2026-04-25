import { useEffect } from 'react'
import { useLocation } from 'react-router-dom';

const RootLayout = ({children}) => {
    const { pathname } = useLocation();

    useEffect(() => {
        // window.scrollTo(0,0);
        document.documentElement.scrollTo({top: 0})
    }, [pathname]);


    return children;
}

export default RootLayout