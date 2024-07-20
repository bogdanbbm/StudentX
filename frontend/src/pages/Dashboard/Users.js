import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

export const Users = () => {
    const [users, setUsers] = useState([]);
    const [newUser, setNewUser] = useState({ role: '', username: '', password: '' });
    const [editUser, setEditUser] = useState({ id: '', role: '', username: '', password: '' });
    const [isEditing, setIsEditing] = useState(false);
    const [isAdding, setIsAdding] = useState(false);

    useEffect(() => {
        fetch('http://localhost:8080/api/users', {
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
            setUsers(data);
        })
        .catch(error => {
            console.error('Error fetching users:', error);
        });

    }, []);

    const handleDelete = (id) => {
        const fetchPromise = fetch(`http://localhost:8080/api/users/${id}`, {
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
            setUsers(users.filter(user => user.id !== id));
        })

        toast.promise(
            fetchPromise,
            {
                pending: 'Deleting user...',
                success: 'User deleted successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
        
    }

    const handleAdd = () => {
        const fetchPromise = fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(newUser)
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorCode) {
                setIsAdding(false);
                throw new Error(data.message);
            }
            setUsers([...users, data]);
            setNewUser({ role: '', username: '', password: '' });  // Clear form after submission
            setIsAdding(false);  // Hide add form after submission
        })   

        toast.promise(
            fetchPromise,
            {
                pending: 'Adding user...',
                success: 'User added successfully!',
                error: {
                    render({ data }) {
                        return data.message || 'An error occurred. Please try again later';
                    }
                }
            }
        );
    }

    const handleEdit = () => {
        const fetchPromise = fetch(`http://localhost:8080/api/users/${editUser.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(editUser)
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorCode) {
                throw new Error(data.message);
            }
            setUsers(users.map(user => user.id === editUser.id ? data : user));
            setIsEditing(false);
            setEditUser({ id: '', role: '', username: '', password: '' });  // Clear form after submission
        })

        toast.promise(
            fetchPromise,
            {
                pending: 'Editing user...',
                success: 'User edited successfully!',
                error: {
                    render({ data }) {
                        return data.message || 'An error occurred. Please try again later';
                    }
                }
            }
        );
    }

    const handleEditClick = (user) => {
        setIsEditing(true);
        setEditUser(user);
    }

    const handleAddClick = () => {
        setIsAdding(true);
    }

    return (
        <>
            <div className='section-users'>
                {!isAdding && !isEditing && <button className='button-users' onClick={handleAddClick}>Add User</button>}

                {isAdding && (
                    <div className="form-container">
                        <h3>Add New User</h3>
                        <input 
                            type="text" 
                            placeholder="Role" 
                            value={newUser.role} 
                            onChange={(e) => setNewUser({ ...newUser, role: e.target.value })} 
                        />
                        <input 
                            type="text" 
                            placeholder="Username" 
                            value={newUser.username} 
                            onChange={(e) => setNewUser({ ...newUser, username: e.target.value })} 
                        />
                        <input 
                            type="password" 
                            placeholder="Password" 
                            value={newUser.password} 
                            onChange={(e) => setNewUser({ ...newUser, password: e.target.value })} 
                        />
                        <button className='button-users' onClick={handleAdd}>Add User</button>
                    </div>
                )}

                {isEditing && (
                    <div className="form-container">
                        <h3>Edit User</h3>
                        <input 
                            type="text" 
                            placeholder="Role" 
                            value={editUser.role} 
                            onChange={(e) => setEditUser({ ...editUser, role: e.target.value })} 
                        />
                        <input 
                            type="text" 
                            placeholder="Username" 
                            value={editUser.username} 
                            onChange={(e) => setEditUser({ ...editUser, username: e.target.value })} 
                        />
                        <input 
                            type="password" 
                            placeholder="Password" 
                            value={editUser.password} 
                            onChange={(e) => setEditUser({ ...editUser, password: e.target.value })} 
                        />
                        <button className='button-users' onClick={handleEdit}>Save Changes</button>
                    </div>
                )}

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Role</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            users && users.map(user => (
                                <tr key={user.id}>
                                    <td>{user.id}</td>
                                    <td>{user.role}</td>
                                    <td>{user.username}</td>
                                    <td>{user.password}</td>
                                    <td>
                                        <button className='button-users' onClick={() => handleEditClick(user)}>Edit</button>
                                        {user.role !== 'ADMIN' && <button className='button-users' onClick={() => handleDelete(user.id)}>Delete</button>}
                                    </td>
                                </tr>
                            ))
                        }
                        {
                            users.length === 0 &&
                            <tr>
                                <td colSpan="5">No users found</td>
                            </tr>
                        }
                    </tbody>
                </table>
            </div>
        </>
    )
}