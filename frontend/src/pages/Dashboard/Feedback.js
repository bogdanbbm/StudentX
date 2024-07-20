import React, { useEffect } from 'react';
import { useState } from 'react';
import Cookies from 'js-cookie';
import { toast } from 'react-toastify';
import './feedback.css';

export const Feedback = () => {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState('');
    const [rating, setRating] = useState(0);
    const [goodAspects, setGoodAspects] = useState([]);
    const [comments, setComments] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/lectures', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === 500) {
                toast.error('An error occurred. Please log in again.');
                return;
            }
            setCourses(data);
        })
        .catch(error => {
            console.error('Error fetching lectures:', error);
        })
    }, []);

    const handleCourseChange = (event) => {
        const selectedCourse = event.target.value;
        setSelectedCourse(selectedCourse);
    };

    const handleRatingChange = (event) => {
        const selectedRating = parseInt(event.target.value);
        setRating(selectedRating);
    };

    const handleGoodAspectChange = (event) => {
        const aspectValue = event.target.value;
        const isChecked = event.target.checked;

        if (isChecked) {
            setGoodAspects((prevGoodAspects) => [...prevGoodAspects, aspectValue]);
        } else {
            setGoodAspects((prevGoodAspects) => prevGoodAspects.filter((aspect) => aspect !== aspectValue));
        }
    };

    const handleCommentsChange = (event) => {
        const comments = event.target.value;
        setComments(comments);
    }

    const handleSubmit = () => {
        const body = {
            lecture: selectedCourse,
            grade: rating,
            pluses: goodAspects,
            text: comments
        };

        console.log(body);

        fetch('http://localhost:8080/api/feedback', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(body)
        })
        .then(response => {
            if (response.status === 500) {
                throw new Error('An error occurred. Please try again later.');
            }
            return response.json();
        })
        .then(data => {
            toast.success('Feedback sent successfully!');
        })
        .catch(error => {
            console.error('Error sending feedback:', error);
            toast.error('An error occurred. Please try again later.');
        });

        setSelectedCourse(0);
        setRating(0);
        setGoodAspects([]);
        setComments('');
    }

    return (
        <div className="feedback-form">
            <h2>Feedback Form</h2>
            
            <label htmlFor="curs">Select the course</label>
            <select id="curs" name="curs" value={selectedCourse} onChange={handleCourseChange}>
                <option value="Select Course">Select Course</option>
                {courses && courses.map(course => (
                    <option key={course.id} value={course.name}>{course.name}</option>
                ))}
            </select>
            
            <label>Please rate the course</label>
            <div className="rating">
            {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((value) => (
                <React.Fragment key={value}>
                    <input
                        type="radio"
                        id={`nota${value}`}
                        name="nota"
                        value={value}
                        checked={rating === value} // Check if this radio is selected
                        onChange={handleRatingChange} // Call handleRatingChange when selection changes
                    />
                    <label htmlFor={`nota${value}`}>{value}</label>
                </React.Fragment>
            ))}
            </div>
            
            <label htmlFor="aspecte-bune">What was good about the course?</label>
            <div className="checkboxes">
                <input
                    type="checkbox"
                    id="cursCheckbox"
                    name="aspecte-bune"
                    value="curs"
                    checked={goodAspects.includes('curs')} // Check if this checkbox is selected
                    onChange={handleGoodAspectChange} // Call handleGoodAspectChange when selection changes
                />
                <label htmlFor="cursCheckbox">Lecture</label>
                <input
                    type="checkbox"
                    id="temeCheckbox"
                    name="aspecte-bune"
                    value="teme"
                    checked={goodAspects.includes('teme')} // Check if this checkbox is selected
                    onChange={handleGoodAspectChange} // Call handleGoodAspectChange when selection changes
                />
                <label htmlFor="temeCheckbox">Homeworks</label>
                <input
                    type="checkbox"
                    id="colaborareCheckbox"
                    name="aspecte-bune"
                    value="colaborare"
                    checked={goodAspects.includes('colaborare')} // Check if this checkbox is selected
                    onChange={handleGoodAspectChange} // Call handleGoodAspectChange when selection changes
                />
                <label htmlFor="colaborareCheckbox">Colaboration</label>
                <input
                    type="checkbox"
                    id="echipaCheckbox"
                    name="aspecte-bune"
                    value="echipa"
                    checked={goodAspects.includes('echipa')} // Check if this checkbox is selected
                    onChange={handleGoodAspectChange} // Call handleGoodAspectChange when selection changes
                />
                <label htmlFor="echipaCheckbox">Team ;)</label>
            </div>
          
            <label htmlFor="comentarii">Free comments</label>
            <textarea id="comentarii" name="comentarii" rows="4" onChange={handleCommentsChange}></textarea>
            
            <div style={{ width: '10%' }} className='button-users' onClick={handleSubmit}>Send</div>
        </div>
    );
}

export default Feedback;
