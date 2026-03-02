import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router";
import { Box, Button, Typography, Chip, Paper, IconButton } from "@mui/material";
import { ArrowBack as ArrowBackIcon, Edit as EditIcon } from "@mui/icons-material";
import { getTodoDetail, updateTodo } from "../services/api";

const TodoDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [todo, setTodo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const [editForm, setEditForm] = useState({});

  const fetchTodoDetail = async () => {
    try {
      const response = await getTodoDetail(id);
      setTodo(response.data);
      setEditForm(response.data);
    } catch (error) {
      console.error("Error fetching todo detail:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdate = async () => {
    try {
      const response = await updateTodo(id, editForm);
      setTodo(response.data);
      setEditing(false);
    } catch (error) {
      console.error("Error updating todo:", error);
    }
  };

  const getPriorityColor = (priority) => {
    switch (priority) {
      case 'HIGH': return 'error';
      case 'MEDIUM': return 'warning';
      case 'LOW': return 'success';
      default: return 'default';
    }
  };

  useEffect(() => {
    fetchTodoDetail();
  }, [id]);

  if (loading) {
    return <Typography>Loading...</Typography>;
  }

  if (!todo) {
    return <Typography>Todo not found</Typography>;
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', alignItems: 'center', mb: 3 }}>
        <IconButton onClick={() => navigate('/todos')} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h4">
          Todo Details
        </Typography>
      </Box>

      <Paper sx={{ p: 3 }}>
        {editing ? (
          <Box>
            <Typography variant="h6" gutterBottom>
              Edit Todo
            </Typography>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              <input
                type="text"
                value={editForm.title}
                onChange={(e) => setEditForm({...editForm, title: e.target.value})}
                style={{ padding: '8px', fontSize: '16px' }}
              />
              <textarea
                value={editForm.description}
                onChange={(e) => setEditForm({...editForm, description: e.target.value})}
                rows={4}
                style={{ padding: '8px', fontSize: '16px', resize: 'vertical' }}
              />
              <select
                value={editForm.priority}
                onChange={(e) => setEditForm({...editForm, priority: e.target.value})}
                style={{ padding: '8px', fontSize: '16px' }}
              >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
              </select>
              <select
                value={editForm.completed.toString()}
                onChange={(e) => setEditForm({...editForm, completed: e.target.value === 'true'})}
                style={{ padding: '8px', fontSize: '16px' }}
              >
                <option value="false">Pending</option>
                <option value="true">Completed</option>
              </select>
              <Box sx={{ display: 'flex', gap: 2 }}>
                <Button variant="contained" onClick={handleUpdate}>
                  Save Changes
                </Button>
                <Button variant="outlined" onClick={() => setEditing(false)}>
                  Cancel
                </Button>
              </Box>
            </Box>
          </Box>
        ) : (
          <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
              <Typography variant="h5" sx={{ flexGrow: 1 }}>
                {todo.title}
              </Typography>
              <IconButton color="primary" onClick={() => setEditing(true)}>
                <EditIcon />
              </IconButton>
            </Box>
            
            <Typography variant="body1" sx={{ mb: 2 }}>
              {todo.description}
            </Typography>

            <Box sx={{ display: 'flex', gap: 1, mb: 2, flexWrap: 'wrap' }}>
              <Chip
                label={`Priority: ${todo.priority}`}
                color={getPriorityColor(todo.priority)}
                size="medium"
              />
              <Chip
                label={todo.completed ? "Completed" : "Pending"}
                color={todo.completed ? "success" : "warning"}
                size="medium"
              />
            </Box>

            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1, color: 'text.secondary' }}>
              <Typography variant="body2">
                Created: {new Date(todo.createdAt).toLocaleString()}
              </Typography>
              <Typography variant="body2">
                Updated: {new Date(todo.updatedAt).toLocaleString()}
              </Typography>
            </Box>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default TodoDetail;
