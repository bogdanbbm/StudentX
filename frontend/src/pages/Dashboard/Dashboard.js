import './dashboard.css';
import { verifyJWT, extractInfoJWT } from '../../JWT.js';
import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { Users } from './Users.js';
import { Groups } from './Groups.js';
import { Students } from './Students.js';
import { Teachers } from './Teachers.js';
import { Courses } from './Courses.js';
import { Feedback } from './Feedback.js';
import { Feedbacks } from './Feedbacks.js';
import Cookies from 'js-cookie';


const FunFact = () => {
    const [ funFact, setFunFact ] = useState('Loading...');
    const [ riddle, setRiddle ] = useState({title: 'Loading...', question: '...', answer: '...', });
    const [ showRiddle, setShowRiddle ] = useState(false);

    useEffect(() => {
        fetch('https://api.api-ninjas.com/v1/facts', {
            method: 'GET',
            headers: { 'X-Api-Key': 'vybE4jgSTak1kzPRrqA9Jg==surIhlyXgt8prX6n'},
            contentType: 'application/json'
        })
        .then(response => response.json())
        .then(data => {
            setFunFact(data[0].fact);
        })
        .catch(error => {
            console.error('Error fetching fun fact:', error);
        });
    }, []); 

    useEffect(() => {
        fetch('https://api.api-ninjas.com/v1/riddles', {
            method: 'GET',
            headers: { 'X-Api-Key': 'vybE4jgSTak1kzPRrqA9Jg==surIhlyXgt8prX6n'},
            contentType: 'application/json'
        })
        .then(response => response.json())
        .then(data => {
            setRiddle({title: data[0].title, question: data[0].question, answer: data[0].answer});
            setShowRiddle(false);
        })
        .catch(error => {
            console.error('Error fetching riddle:', error);
        });
    }, []);

    return (
        <>
            <div className='section-funfact'>
                Ready for a riddle?
                <br/>
                <br/>
                Title: {riddle.title}
                <br/>
                Question: {riddle.question}
                <br/>
                {
                    showRiddle ?
                    <> Answer: {riddle.answer} </> : 
                    <> Answer: {'<hidden>'} <button className='button-users' onClick={() => setShowRiddle(true)}>Show Answer</button> </>
                        
                }
            </div>
            <div className='section-funfact'>
                Fun fact: {funFact}
            </div>
        </>
        
    )
}

const DashSection = ({ section }) => {
    const renderSection = () => {
        switch (section) {
            case 'none':
                return <FunFact />;
            case 'users':
                return <Users />;
            case 'groups':
                return <Groups />;
            case 'students':
                return <Students />;
            case 'teachers':
                return <Teachers />;
            case 'courses':
                return <Courses />;
            case 'feedback':
                return <Feedback />;
            case 'feedbacks':
                return <Feedbacks />;
            case 'logout':
                Cookies.remove('token');
                window.location.pathname = '/';
                return null;
            default:
                return null;
        }
    };

    return (
        <div className='section-dashboard'>
            {renderSection()}
        </div>
    );
};

const LoggedIn = () => {
    const userBody = extractInfoJWT();
    const { username, role } = userBody;
    const [section, setSection] = useState('none');

    const handleSection = (section) => {
        setSection(section);
    }

    return (
        <div className='container-dashboard'>
            <div className="sidebar">
                <div className="sidebar-header" onClick={() => handleSection('none')}>
                    Hello, {username}
                </div>
                <ul className="sidebar-menu">
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('users')}>Users</li>}
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('groups')}>Groups</li>}
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('students')}>Students</li>}
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('teachers')}>Teachers</li>}
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('courses')}>Courses</li>}
                    {role && role === 'STUDENT' && <li onClick={() => handleSection('feedback')}>Feedback</li>}
                    {role && role === 'ADMIN' && <li onClick={() => handleSection('feedbacks')}>Feedbacks</li>}
                    <li onClick={() => handleSection('logout')}> Logout </li> 
                </ul>
            </div>

            <DashSection section={section} />

        </div>

    )
}

const NotLoggedIn = () => {
    return (
        <div className="container-login">
            <div className="title-login">StudentX</div>
            <div className="subtitle-login">Another Basic App</div>
        </div>
    )
}

export const Dashboard = () => {
    const [verified, setVerified] = useState(false);

    useEffect(() => {
        const verifyPromise = verifyJWT();

        toast.promise(
            verifyPromise,
            {
                position: 'top-center',
                pending: 'Verifying token...',
                success: 'Token verified successfully!',
                error: 'Token verification failed. Please log in again.'

            }
        );

        verifyPromise
            .then(isVerified => setVerified(isVerified))
            .catch(error => setVerified(false));
    }, []);

    return (
        <>
            {
                verified ? <LoggedIn /> : <NotLoggedIn />
            }
        </>
    )
}