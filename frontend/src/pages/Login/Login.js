import './login.css'
import React, { useState } from 'react';
import { toast } from 'react-toastify';
import Cookies from 'js-cookie';

export const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        if (username === '') {
            toast.error('Username cannot be empty');
            return;
        }
    
        if (password === '') {
            toast.error('Password cannot be empty');
            return;
        }
    
        const loginPromise = fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'username': username,
                'password': password
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.token) {
                Cookies.set('token', data.token);
                window.location.href = '/dashboard';
                return data;
            }
    
            if (data.errorCode) {
                Cookies.remove('token');
                throw new Error(data.message);
            }
        });
    
        toast.promise(
            loginPromise,
            {
                pending: 'Logging in...',
                success: 'Logged in successfully!',
                error: {
                    render({ data }) {
                        // data will be the thrown error message
                        return data.message || 'An error occurred. Please try again later';
                    }
                }
            }
        );
    }
    

    return (
    <>
        <div className="container-login">
            <div className="title-login">StudentX</div>
            <div className="subtitle-login">Another Basic App</div>
            <div className="login-box">
                <form className='form-login'>
                    <label className="label-login" htmlFor="username">Username</label>
                    <input className='input-login' type="text" id="username" name="username" onChange={(e) => setUsername(e.target.value)}/>
                    <label className="label-login" htmlFor="password">Password</label>
                    <input className='input-login' type="password" id="password" name="password" onChange={(e) => setPassword(e.target.value)}/>
                    
                    <div className="register-login">
                        <a className='href-login' href="/register">Register New Account</a>
                    </div>

                    <div className='button-login' type="submit" onClick={handleLogin}>Login</div>

                </form>
            </div>
        </div>
    </>  
    )
}

