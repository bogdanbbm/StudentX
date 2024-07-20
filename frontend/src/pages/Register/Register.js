import React from 'react'
import './register.css'
import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'

const StudentForm = () => {
    const [newStudent, setNewStudent] = useState({
        user: {
            role: 'STUDENT',
            username: '',
            password: ''
        },
        firstName: '',
        lastName: '',
        fatherInitial: '',
        country: '',
        age: '',
        phoneNumber: '',
        group: {
            id: ''
        }
    });

    const [groups, setGroups] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/groups', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJ1c2VybmFtZSI6InNlcnZpY2UiLCJpYXQiOjE3MTY4MzIyMTksImV4cCI6MTcxNzAxMjIxOX0.oBxFSd9zgSgh6JNrnJzYmvaiouMoWbrdD4tPFTxFd7w`
            }
        })
            .then(response => response.json())
            .then(data => {
                setGroups(data);
            })
            .catch(error => {
                console.error('Error fetching groups:', error);
            });
    }, []);

    const handleAdd = () => {
        const fetchPromise = fetch('http://localhost:8080/api/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJ1c2VybmFtZSI6InNlcnZpY2UiLCJpYXQiOjE3MTY4MzIyMTksImV4cCI6MTcxNzAxMjIxOX0.oBxFSd9zgSgh6JNrnJzYmvaiouMoWbrdD4tPFTxFd7w`
            },
            body: JSON.stringify(newStudent)
        })
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    throw new Error(data.error);
                }
                if (data.status === 500) {
                    throw new Error('An error occurred, please try again later');
                }
                if (data.errorCode) {
                    throw new Error(data.message);
                }

                setNewStudent({
                    user: {
                        role: 'STUDENT',
                        username: '',
                        password: ''
                    },
                    firstName: '',
                    lastName: '',
                    fatherInitial: '',
                    country: '',
                    age: '',
                    phoneNumber: '',
                    group: {
                        id: ''
                    }
                });
            })

            toast.promise(fetchPromise, {
                pending: 'Adding student...',
                success: {
                    render() {
                        return 'Student added successfully';
                    },
                    onClose() {
                        window.location.pathname = "/";
                    }
                },
                error: {
                    render({ data }) {
                        return data.message;
                    }
                }
            });

    }

    return (

        <div style={{ width: '20%', margin: "40px auto", boxShadow: "42px 38px 4px 0px rgba(0, 0, 0, 0.35)" }} className="form-container">
            <h3>Register Student</h3>
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Username"
                onChange={(e) => setNewStudent(prevState => ({ ...prevState, user: { ...prevState.user, username: e.target.value } }))}
            />
            <input
                style={{ width: '80%' }}
                type="password"
                placeholder="Password"
                value={newStudent.user.password}
                onChange={(e) => setNewStudent(prevState => ({ ...prevState, user: { ...prevState.user, password: e.target.value } }))}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="First Name"
                value={newStudent.firstName}
                onChange={(e) => setNewStudent({ ...newStudent, firstName: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Last Name"
                value={newStudent.lastName}
                onChange={(e) => setNewStudent({ ...newStudent, lastName: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Father Initial"
                value={newStudent.fatherInitial}
                onChange={(e) => setNewStudent({ ...newStudent, fatherInitial: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Country"
                value={newStudent.country}
                onChange={(e) => setNewStudent({ ...newStudent, country: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="number"
                placeholder="Age"
                value={newStudent.age}
                onChange={(e) => setNewStudent({ ...newStudent, age: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Phone Number"
                value={newStudent.phoneNumber}
                onChange={(e) => setNewStudent({ ...newStudent, phoneNumber: e.target.value })}
            />
            <select
                style={{ width: '80%' }}
                onChange={(e) => setNewStudent({ ...newStudent, group: { id: e.target.value } })}
                className='select-users'
            >
                <option value=''>Select Group</option>
                {groups.map(group => (
                    <option key={group.id} value={group.id}>{group.name}</option>
                ))}
            </select>
            <button className='button-users' onClick={handleAdd}>Register</button>
        </div>
    )

}

const ProfessorForm = () => {
    const [newTeacher, setNewTeacher] = useState({
        user: { role: "TEACHER", username: "", password: "" },
        firstName: "",
        lastName: "",
        phoneNumber: ""
    });

    const handleAdd = () => {
        const fetchPromise = fetch("http://localhost:8080/api/professors", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJ1c2VybmFtZSI6InNlcnZpY2UiLCJpYXQiOjE3MTY4MzIyMTksImV4cCI6MTcxNzAxMjIxOX0.oBxFSd9zgSgh6JNrnJzYmvaiouMoWbrdD4tPFTxFd7w`
            },
            body: JSON.stringify(newTeacher)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 500) {
                    throw new Error("An error occurred. Please try again later.");
                }
                if (data.errorCode) {
                    throw new Error(data.message);
                }
                setNewTeacher({
                    user: { role: "TEACHER", username: "", password: "" },
                    firstName: "",
                    lastName: "",
                    phoneNumber: ""
                });
            });

        toast.promise(fetchPromise, {
            pending: "Adding teacher...",
            success: {
                render() {
                    return "Teacher added successfully";
                },
                onClose() {
                    window.location.pathname = "/";
                }
            },
            error: {
                render({ data }) {
                    return data.message;
                }
            }
        });
    }

    return (
        <div style={{ width: '20%', margin: "40px auto", boxShadow: "42px 38px 4px 0px rgba(0, 0, 0, 0.35)" }} className="form-container">
            <h3>Register Teacher</h3>
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Username"
                onChange={(e) => setNewTeacher(prevState => ({ ...prevState, user: { ...prevState.user, username: e.target.value } }))}
            />
            <input
                style={{ width: '80%' }}
                type="password"
                placeholder="Password"
                value={newTeacher.user.password}
                onChange={(e) => setNewTeacher(prevState => ({ ...prevState, user: { ...prevState.user, password: e.target.value } }))}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="First Name"
                value={newTeacher.firstName}
                onChange={(e) => setNewTeacher({ ...newTeacher, firstName: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Last Name"
                value={newTeacher.lastName}
                onChange={(e) => setNewTeacher({ ...newTeacher, lastName: e.target.value })}
            />
            <input
                style={{ width: '80%' }}
                type="text"
                placeholder="Phone Number"
                value={newTeacher.phoneNumber}
                onChange={(e) => setNewTeacher({ ...newTeacher, phoneNumber: e.target.value })}
            />
            <button className='button-users' onClick={handleAdd}>Register</button>
        </div>
    )
}

export const Register = () => {
    const [userType, setUserType] = useState('');

    return (
        <div className="container-login">


            {
                userType === '' &&
                <>
                    <div className="title-login">Register</div>
                    <div style={{ paddingTop: '20px' }} className="subtitle-login">... To Another Basic App</div>
                    <div className="container-register">
                        <div className="user-type">
                            {['Student', 'Professor'].map((value) => (
                                <div
                                    key={value}
                                    className='user-type-item'
                                    onClick={() => setUserType(value)}>
                                    {value}

                                </div>
                            ))}
                        </div>
                    </div>
                </>

            }

            {
                userType === 'Student' &&
                <>
                    <div style={{ marginTop: '400px' }} className="title-login">Register</div>
                    <div style={{ paddingTop: '20px' }} className="subtitle-login">... To Another Basic App</div>
                    <StudentForm />
                </>
            }

{
                userType === 'Professor' &&
                <>
                    <div style={{ marginTop: '400px' }} className="title-login">Register</div>
                    <div style={{ paddingTop: '20px' }} className="subtitle-login">... To Another Basic App</div>
                    <ProfessorForm />
                </>
            }

        </div>
    )
}
