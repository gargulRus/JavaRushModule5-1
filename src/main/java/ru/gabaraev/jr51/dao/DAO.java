package ru.gabaraev.jr51.dao;

import ru.gabaraev.jr51.models.Status;
import ru.gabaraev.jr51.models.Task;

import java.util.List;

public interface DAO {
    List<Task> taskLoad();

    Task getTaskById(Long taskId);

    void save(String taskDescription, Status taskStatus);

    void update(Long id, String taskDescription, Status taskStatus);

    void delete(Long id);

}
