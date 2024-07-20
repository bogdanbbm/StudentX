import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode';

export const verifyJWT = () => {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (Cookies.get('token')) {
                resolve(true);
            } else {
                reject(new Error('Invalid token'));
            }
        }, 1000);
    });
};

export const extractInfoJWT = () => {
    const token = Cookies.get('token');
    if (token) {
        try {
            const decodedToken = jwtDecode(token);
            return decodedToken;
            
        } catch (error) {
            console.error('Invalid token:', error);
            return {};
        }
    }
    return {};
}
