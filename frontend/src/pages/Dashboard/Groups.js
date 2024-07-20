import Cookies from "js-cookie";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";

export const Groups = () => {
    const [groups, setGroups] = useState([]);
    const [newGroup, setNewGroup] = useState({ name: '' });
    const [editGroup, setEditGroup] = useState({ id: '', name: '' });
    const [isEditing, setIsEditing] = useState(false);
    const [isAdding, setIsAdding] = useState(false);

    useEffect(() => {
        fetch('http://localhost:8080/api/groups', {
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
            setGroups(data);
        })
        .catch(error => {
            console.error('Error fetching groups:', error);
        });

    }, []);

    const handleDelete = (id) => {
        const fetchPromise = fetch(`http://localhost:8080/api/groups/${id}`, {
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
            setGroups(groups.filter(group => group.id !== id));
        })

        toast.promise(
            fetchPromise,
            {
                pending: 'Deleting group...',
                success: 'Group deleted successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }

    const handleEdit = () => {
        const fetchPromise = fetch(`http://localhost:8080/api/groups/${editGroup.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(editGroup)
        })
        .then(response => {
            if (response.status === 500) {
                setIsEditing(false);
                throw new Error('An error occurred. Please try again later.');
            }
            setGroups(groups.map(group => group.id === editGroup.id ? editGroup : group));
            setIsEditing(false);
        })

        toast.promise(
            fetchPromise,
            {
                pending: 'Updating group...',
                success: 'Group updated successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }

    const handleAdd = () => {
        const fetchPromise = fetch('http://localhost:8080/api/groups', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${Cookies.get('token')}`
            },
            body: JSON.stringify(newGroup)
        })
        .then(response => response.json())
        .then(data => {
            if (data.errorCode) {
                setIsAdding(false);
                toast.error(data.message);
                return;
            }
            setGroups([...groups, data]);
            setIsAdding(false);
        })
        .catch(error => {
            console.error('Error adding group:', error);
        });

        toast.promise(
            fetchPromise,
            {
                pending: 'Adding group...',
                success: 'Group added successfully!',
                error: 'An error occurred. Please try again later.'
            }
        );
    }

    const handleEditClick = (group) => {
        setIsEditing(true);
        setEditGroup(group);
    }

    const handleAddClick = () => {
        setIsAdding(true);
    }

    return (
        <>
            <div className='section-users'>
                {!isAdding && !isEditing && <button className='button-users' onClick={handleAddClick}>Add Group</button>}

                {isAdding && (
                    <div className="form-container">
                        <h3>Add New Group</h3>
                        <input
                            type="text"
                            placeholder="Name"
                            value={newGroup.name}
                            onChange={(e) => setNewGroup({ ...newGroup, name: e.target.value })}
                        />
                    <button className="button-users" onClick={handleAdd}>Add</button>
                    </div>
                )}

                {isEditing && (
                    <div className="form-container">
                        <h3>Edit Group</h3>
                        <input
                            type="text"
                            placeholder="Name"
                            value={editGroup.name}
                            onChange={(e) => setEditGroup({ ...editGroup, name: e.target.value })}
                        />
                        <button className="button-users" onClick={handleEdit}>Save</button>
                    </div>
                )}


                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        {
                            groups && groups.map(group => (
                                <tr key={group.id}>
                                    <td>{group.id}</td>
                                    <td>{group.name}</td>
                                    <td>
                                        <button className="button-users" onClick={() => handleEditClick(group)}>Edit</button>
                                        <button className="button-users" onClick={() => handleDelete(group.id)}>Delete</button>
                                    </td>
                                </tr>
                            ))
                        }
                        {
                            groups.length === 0 && (
                                <tr>
                                    <td colSpan='5'>No groups found.</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </>
    )

}