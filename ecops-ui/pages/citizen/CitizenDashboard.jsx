import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Tabs,
  Tab,
  TextField,
  Button,
  Paper,
  Grid,
} from "@mui/material";
import Sidebar from "../../components/Sidebar";
import axios from "axios";

const CitizenDashboard = () => {
  const [tab, setTab] = useState(0);
  const [form, setForm] = useState({
    title: "",
    description: "",
    area: "",
  });
  const [complaints, setComplaints] = useState([]);

  const token = localStorage.getItem("token");

  const handleTabChange = (_, newValue) => {
    setTab(newValue);
  };

  const handleSubmitComplaint = async () => {
    try {
      await axios.post("/api/complaints/submit", form, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      alert("Complaint submitted successfully");
      setForm({ title: "", description: "", area: "" });
      fetchComplaints();
    } catch (err) {
      console.error("Submission failed", err);
    }
  };

  const fetchComplaints = async () => {
    try {
      const res = await axios.get("/api/complaints/mine", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setComplaints(res.data);
    } catch (err) {
      console.error("Could not fetch complaints", err);
    }
  };

  useEffect(() => {
    if (tab === 1) fetchComplaints();
  }, [tab]);

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar role="CITIZEN" />
      <Box sx={{ ml: "220px", p: 4, flex: 1 }}>
        <Typography variant="h4" mb={2}>Citizen Dashboard</Typography>
        <Tabs value={tab} onChange={handleTabChange}>
          <Tab label="Submit Complaint" />
          <Tab label="My Complaints" />
        </Tabs>

        {/* Submit Complaint */}
        {tab === 0 && (
          <Box mt={3}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  label="Title"
                  fullWidth
                  value={form.title}
                  onChange={(e) => setForm({ ...form, title: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Description"
                  fullWidth
                  multiline
                  minRows={3}
                  value={form.description}
                  onChange={(e) => setForm({ ...form, description: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Area"
                  fullWidth
                  value={form.area}
                  onChange={(e) => setForm({ ...form, area: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <Button variant="contained" onClick={handleSubmitComplaint}>
                  Submit Complaint
                </Button>
              </Grid>
            </Grid>
          </Box>
        )}

        {/* My Complaints */}
        {tab === 1 && (
          <Box mt={3}>
            {complaints.length === 0 ? (
              <Typography>No complaints submitted yet.</Typography>
            ) : (
              complaints.map((c) => (
                <Paper key={c.complaint_id} sx={{ p: 2, mb: 2 }}>
                  <Typography><strong>Title:</strong> {c.title}</Typography>
                  <Typography><strong>Description:</strong> {c.description}</Typography>
                  <Typography><strong>Status:</strong> {c.status}</Typography>
                  <Typography><strong>Area:</strong> {c.area}</Typography>
                  <Typography><strong>Submitted At:</strong> {c.submittedAt}</Typography>
                </Paper>
              ))
            )}
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default CitizenDashboard;
