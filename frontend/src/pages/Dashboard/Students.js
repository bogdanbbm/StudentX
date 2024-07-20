import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

export const Students = () => {
    const [students, setStudents] = useState([]);
    const [newStudent, setNewStudent] = useState(
        { user: {role: 'STUDENT', username: '', password: ''}, 
          firstName: '', 
            lastName: '', 
            fatherInitial: '',
            country: '', 
            age: 0, 
            phoneNumber: '', 
            group: {id: ''} });
    const [editStudent, setEditStudent] = useState({ id: '', user: {}, firstName: '', lastName: '', fatherInitial: '', country: '', age: 0, phoneNumber: '', group: {} });
    const [isEditing, setIsEditing] = useState(false);
    const [isAdding, setIsAdding] = useState(false);

    useEffect(() => {
        fetch('http://localhost:8080/api/students', {
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
                setStudents(data);
            })
            .catch(error => {
                console.error('Error fetching students:', error);
            });

    }, []);

    const handleDelete = (id) => {
        const fetchPromise = fetch(`http://localhost:8080/api/students/${id}`, {
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
                setStudents(students.filter(student => student.id !== id));
            })

        toast.promise(
            fetchPromise,
            {
                pending: 'Deleting student...',
                success: 'Student deleted successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }

    const handleAdd = () => {
        const fetchPromise = fetch('http://localhost:8080/api/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(newStudent)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 500) {
                    setIsAdding(false);
                    throw new Error('An error occurred. Please try again later.');
                }
                setNewStudent({ user: {role: 'STUDENT', username: '', password: ''}, firstName: '', lastName: '', fatherInitial: '', country: '', age: 0, phoneNumber: '', group: {id: ''} });
                setIsAdding(false);
            })

        toast.promise(
            fetchPromise,
            {
                pending: 'Adding student...',
                success: 'Student added successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }

    const handleEdit = () => {
        const fetchPromise = fetch(`http://localhost:8080/api/students/${editStudent.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(editStudent)
        })
            .then(response => response.json())
            .then(data => {
                if (data.errorCode) {
                    throw new Error(data.message);
                }
                setStudents(students.map(student => student.id === editStudent.id ? editStudent : student));
                setIsEditing(false);
                setEditStudent({ id: '', user: {}, firstName: '', lastName: '', fatherInitial: '', country: '', age: 0, phoneNumber: '', group: {} });
            })

        toast.promise(
            fetchPromise,
            {
                pending: 'Updating student...',
                success: 'Student updated successfully!',
                error: {
                    render({ data }) {
                        return data.message || 'An error occurred. Please try again later.';
                    }
                }
            }
        );
    }

    const handleEditClick = (student) => {
        setIsEditing(true);
        setEditStudent(student);
    }

    const handleAddClick = () => {
        setIsAdding(true);
    }

    return (
        <>
            <div className='section-users'>
                {!isAdding && !isEditing && <button className='button-users' onClick={handleAddClick}>Add Student</button>}

                {isAdding && (
                    <div className="form-container">
                        <h3>Add New Student</h3>
                        <input 
                            type="text" 
                            placeholder="Username"
                            value={newStudent.user.username}
                            onChange={(e) => setNewStudent(prevState => ({ ...prevState, user: { ...prevState.user, username: e.target.value } }))}
                        />
                        <input 
                            type="password"
                            placeholder="Password"
                            value={newStudent.user.password}
                            onChange={(e) => setNewStudent(prevState => ({ ...prevState, user: { ...prevState.user, password: e.target.value } }))}
                        />
                        <input
                            type="text"
                            placeholder="First Name"
                            value={newStudent.firstName}
                            onChange={(e) => setNewStudent({ ...newStudent, firstName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={newStudent.lastName}
                            onChange={(e) => setNewStudent({ ...newStudent, lastName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Father Initial"
                            value={newStudent.fatherInitial}
                            onChange={(e) => setNewStudent({ ...newStudent, fatherInitial: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Country"
                            value={newStudent.country}
                            onChange={(e) => setNewStudent({ ...newStudent, country: e.target.value })}
                        />
                        <input
                            type="number"
                            placeholder="Age"
                            value={newStudent.age}
                            onChange={(e) => setNewStudent({ ...newStudent, age: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Phone Number"
                            value={newStudent.phoneNumber}
                            onChange={(e) => setNewStudent({ ...newStudent, phoneNumber: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Group ID"
                            value={newStudent.group.id}
                            onChange={(e) => setNewStudent({ ...newStudent, group: { id: e.target.value } })}
                        />
                        <button className='button-users' onClick={handleAdd}>Add Student</button>
                    </div>
                )}

                {isEditing && (
                    <div className="form-container">
                        <h3>Edit Student</h3>
                        <input
                            type="text"
                            placeholder="Student ID"
                            value={editStudent.id}
                            onChange={(e) => setEditStudent({ ...editStudent, id: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="First Name"
                            value={editStudent.firstName}
                            onChange={(e) => setEditStudent({ ...editStudent, firstName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={editStudent.lastName}
                            onChange={(e) => setEditStudent({ ...editStudent, lastName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Father Initial"
                            value={editStudent.fatherInitial}
                            onChange={(e) => setEditStudent({ ...editStudent, fatherInitial: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Country"
                            value={editStudent.country}
                            onChange={(e) => setEditStudent({ ...editStudent, country: e.target.value })}
                        />
                        <input
                            type="number"
                            placeholder="Age"
                            value={editStudent.age}
                            onChange={(e) => setEditStudent({ ...editStudent, age: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Phone Number"
                            value={editStudent.phoneNumber}
                            onChange={(e) => setEditStudent({ ...editStudent, phoneNumber: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Group ID"
                            value={editStudent.group.id}
                            onChange={(e) => setEditStudent({ ...editStudent, group: { id: e.target.value } })}
                        />

                        <button className='button-users' onClick={handleEdit}>Edit Student</button>
                    </div>
                )}

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Father Initial</th>
                            <th>Country</th>
                            <th>Age</th>
                            <th>Phone Number</th>
                            <th>Group</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            students && students.map(student => (
                                <tr key={student.id}>
                                    <td>{student.id}</td>
                                    <td>{student.user.username}</td>
                                    <td>{student.firstName}</td>
                                    <td>{student.lastName}</td>
                                    <td>{student.fatherInitial}</td>
                                    <td>{student.country}</td>
                                    <td>{student.age}</td>
                                    <td>{student.phoneNumber}</td>
                                    <td>{student.group.name}</td>
                                    <td>
                                        <button className='button-users' onClick={() => handleEditClick(student)}>Edit</button>
                                        <button className='button-users' onClick={() => handleDelete(student.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        }
                        {
                            students.length === 0 && (
                                <tr>
                                    <td colSpan='9'>No students found</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </>
    )
}

