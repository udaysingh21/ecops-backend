import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  Tabs,
  Tab,
  Grid,
  TextField,
  Button,
  Paper,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
} from "@mui/material";
import axios from "axios";
import Sidebar from "../../components/Sidebar";

const AdminDashboard = () => {
  const [tab, setTab] = useState(0);
  const token = localStorage.getItem("token");

  const [policeStations, setPoliceStations] = useState([]);
  const [form, setForm] = useState({
    stationName: "",
    zone: "",
    district: "",
    stationId: "",
    areas: [""],
    departmentName: "",
    departmentStationId: "",
    officerEmail: "",
    assignStationId: "",
  });

  const handleChange = (field) => (e) => {
    setForm({ ...form, [field]: e.target.value });
  };

  const handleAreaChange = (index, value) => {
    const updated = [...form.areas];
    updated[index] = value;
    setForm({ ...form, areas: updated });
  };

  const addAreaField = () => {
    setForm({ ...form, areas: [...form.areas, ""] });
  };

  const fetchStations = async () => {
    try {
      const res = await axios.get("/api/admin/police-stations", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setPoliceStations(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const createStation = async () => {
    try {
      await axios.post(
        "/api/admin/police-stations",
        {
          name: form.stationName,
          zone: form.zone,
          district: form.district,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Police station created");
      fetchStations();
    } catch (err) {
      alert(err, "Error creating station");
    }
  };

  const assignAreas = async () => {
    try {
      await axios.post(
        `/api/admin/police-stations/${form.stationId}/areas`,
        { areas: form.areas },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Areas assigned");
    } catch (err) {
      alert(err, "Error assigning areas");
    }
  };

  const createDepartment = async () => {
    try {
      await axios.post(
        "/api/admin/departments/create",
        {
          name: form.departmentName,
          policeStationId: form.departmentStationId,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Department created");
    } catch (err) {
      alert(err, "Error creating department");
    }
  };

  const assignOfficer = async () => {
    try {
      await axios.post(
        "/api/admin/assign-officer",
        {
          officerEmail: form.officerEmail,
          policeStationId: form.assignStationId,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert("Officer assigned");
    } catch (err) {
      alert(err,"Error assigning officer");
    }
  };

  useEffect(() => {
    fetchStations();
  }, []);

  return (
    <Box sx={{ display: "flex" }}>
      <Sidebar role="ADMIN" />
      <Box sx={{ ml: "220px", p: 4, flex: 1 }}>
        <Typography variant="h4" mb={2}>Admin Dashboard</Typography>
        <Tabs value={tab} onChange={(_, v) => setTab(v)}>
          <Tab label="Create Station" />
          <Tab label="Assign Areas" />
          <Tab label="Create Department" />
          <Tab label="Assign Officer" />
        </Tabs>

        {/* Create Police Station */}
        {tab === 0 && (
          <Box mt={3}>
            <Grid container spacing={2}>
              <Grid item xs={4}>
                <TextField
                  label="Station Name"
                  fullWidth
                  value={form.stationName}
                  onChange={handleChange("stationName")}
                />
              </Grid>
              <Grid item xs={4}>
                <TextField
                  label="Zone"
                  fullWidth
                  value={form.zone}
                  onChange={handleChange("zone")}
                />
              </Grid>
              <Grid item xs={4}>
                <TextField
                  label="District"
                  fullWidth
                  value={form.district}
                  onChange={handleChange("district")}
                />
              </Grid>
              <Grid item xs={12}>
                <Button variant="contained" onClick={createStation}>
                  Create Station
                </Button>
              </Grid>
            </Grid>
          </Box>
        )}

        {/* Assign Areas */}
        {tab === 1 && (
          <Box mt={3}>
            <FormControl fullWidth sx={{ mb: 2 }}>
              <InputLabel>Select Station</InputLabel>
              <Select
                value={form.stationId}
                onChange={handleChange("stationId")}
                label="Select Station"
              >
                {policeStations.map((s) => (
                  <MenuItem key={s.id} value={s.id}>
                    {s.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            {form.areas.map((area, idx) => (
              <TextField
                key={idx}
                fullWidth
                label={`Area ${idx + 1}`}
                sx={{ mb: 1 }}
                value={area}
                onChange={(e) => handleAreaChange(idx, e.target.value)}
              />
            ))}

            <Button onClick={addAreaField}>+ Add More</Button>
            <Button variant="contained" sx={{ ml: 2 }} onClick={assignAreas}>
              Assign Areas
            </Button>
          </Box>
        )}

        {/* Create Department */}
        {tab === 2 && (
          <Box mt={3}>
            <TextField
              fullWidth
              label="Department Name"
              value={form.departmentName}
              onChange={handleChange("departmentName")}
              sx={{ mb: 2 }}
            />
            <FormControl fullWidth>
              <InputLabel>Station</InputLabel>
              <Select
                value={form.departmentStationId}
                onChange={handleChange("departmentStationId")}
                label="Station"
              >
                {policeStations.map((s) => (
                  <MenuItem key={s.id} value={s.id}>
                    {s.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <Button variant="contained" sx={{ mt: 2 }} onClick={createDepartment}>
              Create Department
            </Button>
          </Box>
        )}

        {/* Assign Officer */}
        {tab === 3 && (
          <Box mt={3}>
            <TextField
              fullWidth
              label="Officer Email"
              value={form.officerEmail}
              onChange={handleChange("officerEmail")}
              sx={{ mb: 2 }}
            />
            <FormControl fullWidth>
              <InputLabel>Assign to Station</InputLabel>
              <Select
                value={form.assignStationId}
                onChange={handleChange("assignStationId")}
                label="Assign to Station"
              >
                {policeStations.map((s) => (
                  <MenuItem key={s.id} value={s.id}>
                    {s.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <Button variant="contained" sx={{ mt: 2 }} onClick={assignOfficer}>
              Assign Officer
            </Button>
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default AdminDashboard;
