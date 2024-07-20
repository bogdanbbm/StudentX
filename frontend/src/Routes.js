import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Login } from './pages/Login/Login';
import { Dashboard } from './pages/Dashboard/Dashboard';
import { Register } from './pages/Register/Register';

export const RoutePages = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} /> 
                <Route path='/dashboard' element={<Dashboard />} />
                <Route path='/register' element={<Register />} />
            </Routes>

        </Router>
    )
}