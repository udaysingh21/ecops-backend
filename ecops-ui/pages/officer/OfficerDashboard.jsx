import React, { useEffect, useState } from "react";
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

const OfficerDashboard = () => {
  const [tab, setTab] = useState(0);
  const [criminals, setCriminals] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [form, setForm] = useState({
    name: "",
    age: "",
    crimeType: "",
    areaId: "",
    policeStationId: "",
  });

  const token = localStorage.getItem("token");

  const handleTabChange = (_, newValue) => {
    setTab(newValue);
  };

  const fetchCriminals = async () => {
    try {
      const res = await axios.get("/api/criminals/all", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCriminals(res.data);
    } catch (err) {
      console.error("Failed to fetch criminals", err);
    }
  };

  const fetchComplaints = async () => {
    try {
      // Station ID can be fetched from JWT, hardcoded here for example
      const stationId = 1;
      const res = await axios.get(`/api/admin/complaints?stationId=${stationId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setComplaints(res.data);
    } catch (err) {
      console.error("Failed to fetch complaints", err);
    }
  };

  const handleCreateCriminal = async () => {
    try {
      await axios.post("/api/criminals/create", form, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      alert("Criminal added successfully");
      fetchCriminals();
      setForm({ name: "", age: "", crimeType: "", areaId: "", policeStationId: "" });
    } catch (err) {
      console.error("Failed to add criminal", err);
    }
  };

  useEffect(() => {
    if (tab === 0) fetchCriminals();
    else if (tab === 2) fetchComplaints();
  }, [tab]);

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar role="OFFICER" />
      <Box sx={{ ml: "220px", p: 4, flex: 1 }}>
        <Typography variant="h4" mb={2}>Officer Dashboard</Typography>
        <Tabs value={tab} onChange={handleTabChange}>
          <Tab label="View Criminals" />
          <Tab label="Add Criminal" />
          <Tab label="View Complaints" />
        </Tabs>

        {/* Tab 0: View Criminals */}
        {tab === 0 && (
          <Box mt={3}>
            {criminals.map((c) => (
              <Paper key={c.id} sx={{ p: 2, mb: 2 }}>
                <Typography><strong>Name:</strong> {c.name}</Typography>
                <Typography><strong>Crime:</strong> {c.crimeType}</Typography>
                <Typography><strong>Area:</strong> {c.area}</Typography>
                <Typography><strong>Station:</strong> {c.policeStation}</Typography>
              </Paper>
            ))}
          </Box>
        )}

        {/* Tab 1: Add Criminal */}
        {tab === 1 && (
          <Box mt={3} component="form" noValidate autoComplete="off">
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  label="Name"
                  fullWidth
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  label="Age"
                  fullWidth
                  type="number"
                  value={form.age}
                  onChange={(e) => setForm({ ...form, age: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  label="Crime Type"
                  fullWidth
                  value={form.crimeType}
                  onChange={(e) => setForm({ ...form, crimeType: e.target.value })}
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  label="Area ID"
                  fullWidth
                  value={form.areaId}
                  onChange={(e) => setForm({ ...form, areaId: e.target.value })}
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  label="Police Station ID"
                  fullWidth
                  value={form.policeStationId}
                  onChange={(e) =>
                    setForm({ ...form, policeStationId: e.target.value })
                  }
                />
              </Grid>
              <Grid item xs={12}>
                <Button variant="contained" onClick={handleCreateCriminal}>
                  Submit
                </Button>
              </Grid>
            </Grid>
          </Box>
        )}

        {/* Tab 2: View Complaints */}
        {tab === 2 && (
          <Box mt={3}>
            {complaints.map((cmp) => (
              <Paper key={cmp.complaint_id} sx={{ p: 2, mb: 2 }}>
                <Typography><strong>Title:</strong> {cmp.title}</Typography>
                <Typography><strong>Description:</strong> {cmp.description}</Typography>
                <Typography><strong>Area:</strong> {cmp.area}</Typography>
                <Typography><strong>Submitted By:</strong> {cmp.citizen}</Typography>
                <Typography><strong>Status:</strong> {cmp.status}</Typography>
              </Paper>
            ))}
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default OfficerDashboard;
