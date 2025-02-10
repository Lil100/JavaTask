package com.todo.task.service;

import com.todo.task.entity.Task;
import com.todo.task.exceptions.RecordNotFound;
import com.todo.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository; // Constructor injection via Lombok

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFound("Task does not exist with ID: " + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFound("Task does not exist with ID: " + id));

        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());

        return taskRepository.save(existingTask);
    }

    public boolean deleteTask(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFound("Task does not exist with ID: " + id));

        taskRepository.delete(task);
        return true;
    }
}
