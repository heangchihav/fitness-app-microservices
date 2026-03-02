import { useState } from "react";
import { Box, Button, TextField, Typography, MenuItem, FormControl, InputLabel, Select } from "@mui/material";
import { addTodo } from "../services/api";

const TodoForm = ({ onTodosAdded }) => {
  const [todo, setTodo] = useState({
    title: "",
    description: "",
    priority: "MEDIUM",
    completed: false
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTodo(prev => ({
      ...prev,
      [name]: name === 'completed' ? value === 'true' : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!todo.title.trim()) return;

    try {
      await addTodo(todo);
      setTodo({ title: "", description: "", priority: "MEDIUM", completed: false });
      if (onTodosAdded) onTodosAdded();
    } catch (error) {
      console.error("Error adding todo:", error);
    }
  };

  return (
    <Box sx={{ mb: 3, p: 2, border: '1px solid #ddd', borderRadius: 2 }}>
      <Typography variant="h6" gutterBottom>
        Add New Todo
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          label="Title"
          name="title"
          value={todo.title}
          onChange={handleChange}
          required
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          label="Description"
          name="description"
          value={todo.description}
          onChange={handleChange}
          multiline
          rows={3}
          sx={{ mb: 2 }}
        />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel>Priority</InputLabel>
          <Select
            name="priority"
            value={todo.priority}
            onChange={handleChange}
            label="Priority"
          >
            <MenuItem value="LOW">Low</MenuItem>
            <MenuItem value="MEDIUM">Medium</MenuItem>
            <MenuItem value="HIGH">High</MenuItem>
          </Select>
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel>Status</InputLabel>
          <Select
            name="completed"
            value={todo.completed.toString()}
            onChange={handleChange}
            label="Status"
          >
            <MenuItem value="false">Pending</MenuItem>
            <MenuItem value="true">Completed</MenuItem>
          </Select>
        </FormControl>
        <Button type="submit" variant="contained" color="primary">
          Add Todo
        </Button>
      </form>
    </Box>
  );
};

export default TodoForm;
