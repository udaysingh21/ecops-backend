import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ProtectedRoute from "./routes/ProtectedRoute";

// Import your role-based components
import AdminDashboard from "../pages/admin/AdminDashboard";
import OfficerDashboard from "../pages/officer/OfficerDashboard";
import CitizenDashboard from "../pages/citizen/CitizenDashboard";

// Import system pages
import Unauthorized from "../pages/Unauthorized";
import NotFound from "../pages/NotFound";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/officer"
          element={
            <ProtectedRoute allowedRoles={["POLICE_OFFICER"]}>
              <OfficerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/citizen"
          element={
            <ProtectedRoute allowedRoles={["CITIZEN"]}>
              <CitizenDashboard />
            </ProtectedRoute>
          }
        />

        <Route path="/unauthorized" element={<Unauthorized />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
