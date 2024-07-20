import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

export const Teachers = () => {
    const [teachers, setTeachers] = useState([]);
    const [newTeacher, setNewTeacher] = useState({
        user: { role: "TEACHER", username: "", password: "" },
        firstName: "",
        lastName: "",
        phoneNumber: ""
    });
    const [editTeacher, setEditTeacher] = useState({
        id: "",
        user: {},
        firstName: "",
        lastName: "",
        phoneNumber: ""
    });
    const [isEditing, setIsEditing] = useState(false);
    const [isAdding, setIsAdding] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8080/api/professors", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                'Authorization': `Bearer ${Cookies.get("token")}`
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 500) {
                    toast.error("An error occurred. Please log in again.");
                    return;
                }
                setTeachers(data);
            })
            .catch(error => {
                console.error("Error fetching teachers:", error);
            });
    }, []);

    const handleDelete = (id) => {
        const fetchPromise = fetch(`http://localhost:8080/api/professors/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${Cookies.get("token")}`
            }
        }).then(response => {
            if (response.status === 500) {
                throw new Error("An error occurred. Please try again later.");
            }
            setTeachers(teachers.filter(teacher => teacher.id !== id));
        });

        toast.promise(fetchPromise, {
            pending: "Deleting teacher...",
            success: "Teacher deleted successfully!",
            error: "An error occurred. Please try again later."
        });
    }

    const handleAdd = () => {
        const fetchPromise = fetch("http://localhost:8080/api/professors", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${Cookies.get("token")}`
            },
            body: JSON.stringify(newTeacher)
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 500) {
                    setIsAdding(false);
                    throw new Error("An error occurred. Please try again later.");
                }
                setNewTeacher({
                    user: { role: "TEACHER", username: "", password: "" },
                    firstName: "",
                    lastName: "",
                    phoneNumber: ""
                });
                setIsAdding(false);
            });

        toast.promise(fetchPromise, {
            pending: "Adding teacher...",
            success: "Teacher added successfully!",
            error: "An error occurred. Please try again later."
        });
    }

    const handleEdit = () => {
        const fetchPromise = fetch(`http://localhost:8080/api/professors/${editTeacher.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${Cookies.get("token")}`
            },
            body: JSON.stringify(editTeacher)
        })
            .then(response => response.json())
            .then(data => {
                if (data.errorCode) {
                    throw new Error(data.message);
                }
                setTeachers(teachers.map(teacher => teacher.id === editTeacher.id ? editTeacher : teacher));
                setIsEditing(false);
                setEditTeacher({ id: "", user: {}, firstName: "", lastName: "", phoneNumber: "" });
            });

        toast.promise(fetchPromise, {
            pending: "Updating teacher...",
            success: "Teacher updated successfully!",
            error: {
                render({ data }) {
                    return data.message || "An error occurred. Please try again later.";
                }
            }
        });
    }

    const handleEditClick = teacher => {
        setIsEditing(true);
        setEditTeacher(teacher);
    }

    const handleAddClick = () => {
        setIsAdding(true);
    }

    return (
        <>
            <div className="section-users">
                {!isAdding && !isEditing && (
                    <button className="button-users" onClick={handleAddClick}>
                        Add Teacher
                    </button>
                )}

                {isAdding && (
                    <div className="form-container">
                        <h3>Add New Teacher</h3>
                        <input
                            type="text"
                            placeholder="Username"
                            value={newTeacher.user.username}
                            onChange={e =>
                                setNewTeacher(prevState => ({
                                    ...prevState,
                                    user: { ...prevState.user, username: e.target.value }
                                }))
                            }
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            value={newTeacher.user.password}
                            onChange={e =>
                                setNewTeacher(prevState => ({
                                    ...prevState,
                                    user: { ...prevState.user, password: e.target.value }
                                }))
                            }
                        />
                        <input
                            type="text"
                            placeholder="First Name"
                            value={newTeacher.firstName}
                            onChange={e => setNewTeacher({ ...newTeacher, firstName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={newTeacher.lastName}
                            onChange={e => setNewTeacher({ ...newTeacher, lastName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Phone Number"
                            value={newTeacher.phoneNumber}
                            onChange={e => setNewTeacher({ ...newTeacher, phoneNumber: e.target.value })}
                        />
                        <button className="button-users" onClick={handleAdd}>
                            Add Teacher
                        </button>
                    </div>
                )}

                {isEditing && (
                    <div className="form-container">
                        <h3>Edit Teacher</h3>
                        <input
                            type="text"
                            placeholder="Teacher ID"
                            value={editTeacher.id}
                            onChange={e => setEditTeacher({ ...editTeacher, id: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="First Name"
                            value={editTeacher.firstName}
                            onChange={e => setEditTeacher({ ...editTeacher, firstName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={editTeacher.lastName}
                            onChange={e => setEditTeacher({ ...editTeacher, lastName: e.target.value })}
                        />
                        <input
                            type="text"
                            placeholder="Phone Number"
                            value={editTeacher.phoneNumber}
                            onChange={e => setEditTeacher({ ...editTeacher, phoneNumber: e.target.value })}
                        />
                        <button className="button-users" onClick={handleEdit}>
                            Save Changes
                        </button>
                    </div>
                )}

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Username</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone Number</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        {teachers && teachers.map(teacher => (
                            <tr key={teacher.id}>
                                <td>{teacher.id}</td>
                                <td>{teacher.user.username}</td>
                                <td>{teacher.firstName}</td>
                                <td>{teacher.lastName}</td>
                                <td>{teacher.phoneNumber}</td>
                                <td>
                                    <button className="button-users" onClick={() => handleEditClick(teacher)}>
                                        Edit
                                    </button>
                                    <button className="button-users" onClick={() => handleDelete(teacher.id)}>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        {teachers.length === 0 && (
                            <tr>
                                <td colSpan="6">No teachers found</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </>
    );
}
