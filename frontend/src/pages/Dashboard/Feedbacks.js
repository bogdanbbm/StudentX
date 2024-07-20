import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

export const Feedbacks = () => {
    const [feedbacks, setFeedbacks] = useState([]);
    
    useEffect(() => {
        fetch('http://localhost:8080/api/feedback', {
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
            setFeedbacks(data);
        })
        .catch(error => {
            console.error('Error fetching feedbacks:', error);
        });
    }, []);

    const handleDelete = (id) => {
        const fetchPromise = fetch(`http://localhost:8080/api/feedback/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            }
        })
        .then(response => {
            if (response.status === 500) {
                throw new Error('An error occurred. Please try again later.');
            }
            setFeedbacks(feedbacks.filter(feedback => feedback.id !== id));
        })

        toast.promise(
            fetchPromise,
            {
                pending: 'Deleting feedback...',
                success: 'Feedback deleted successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }



    return (
        <>
        <div className='section-users'>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Grade</th>
                            <th>Course</th>
                            <th>Pluses</th>
                            <th>Text</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            feedbacks.map(feedback => (
                                <tr key={feedback.id}>
                                    <td>{feedback.id}</td>
                                    <td>{feedback.grade}</td>
                                    <td>{feedback.lecture}</td>
                                    <td>{feedback.pluses}</td>
                                    <td>{feedback.text}</td>
                                    <td>
                                        <button className="button-users" onClick={() => handleDelete(feedback.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        }
                        {
                            feedbacks.length === 0 && (
                                <tr>
                                    <td colSpan='5'>No feedbacks found.</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </>
    )
}