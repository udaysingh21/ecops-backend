// src/components/Sidebar.jsx
import React from "react";
import { Box, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Sidebar = ({ role }) => {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  return (
    <Box
      sx={{
        width: "200px",
        height: "100vh",
        bgcolor: "#f4f4f4",
        p: 2,
        position: "fixed",
      }}
    >
      <h3>{role} Panel</h3>
      <Button fullWidth onClick={() => navigate("/")}>
        Home
      </Button>

      {role === "CITIZEN" && (
        <>
          <Button fullWidth onClick={() => navigate("/complaint/submit")}>
            Submit Complaint
          </Button>
          <Button fullWidth onClick={() => navigate("/complaint/history")}>
            My Complaints
          </Button>
        </>
      )}

      {role === "OFFICER" && (
        <>
          <Button fullWidth onClick={() => navigate("/criminals")}>
            Criminals
          </Button>
          <Button fullWidth onClick={() => navigate("/officer/complaints")}>
            Complaints
          </Button>
        </>
      )}

      {role === "ADMIN" && (
        <>
          <Button fullWidth onClick={() => navigate("/admin/police-stations")}>
            Police Stations
          </Button>
          <Button fullWidth onClick={() => navigate("/admin/departments")}>
            Departments
          </Button>
          <Button fullWidth onClick={() => navigate("/admin/officers")}>
            Assign Officers
          </Button>
        </>
      )}

      <Button fullWidth color="error" onClick={logout} sx={{ mt: 2 }}>
        Logout
      </Button>
    </Box>
  );
};

export default Sidebar;
