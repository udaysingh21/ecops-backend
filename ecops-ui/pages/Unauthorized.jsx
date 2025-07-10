import React from "react";
import { Typography, Box } from "@mui/material";

const Unauthorized = () => (
  <Box p={5}>
    <Typography variant="h3">403 - Unauthorized</Typography>
    <Typography>You do not have permission to view this page.</Typography>
  </Box>
);

export default Unauthorized;
