package ru.gabaraev.jr51.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gabaraev.jr51.dao.DAO;
import ru.gabaraev.jr51.models.Status;
import ru.gabaraev.jr51.models.Task;

import java.util.Collections;
import java.util.List;

@Service
public class TaskService {
    private final DAO taskDao;

    @Autowired
    public TaskService(DAO taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> taskLoad() {
        return taskDao.taskLoad();
    }

    public Page<Task> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Task> original = taskLoad();
        List<Task> list;

        if (original.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, original.size());
            list = original.subList(startItem, toIndex);
        }

        Page<Task> taskPage
                = new PageImpl<Task>(list, PageRequest.of(currentPage, pageSize), original.size());

        return taskPage;
    }


    public Task getTaskById(Long taskId) {
        return taskDao.getTaskById(taskId);
    }

    public void save(String taskDescription, String taskStatus) {
        taskDao.save(taskDescription, getStatus(taskStatus));
    }

    public void update(Long id, String taskDescription, String taskStatus) {
        taskDao.update(id, taskDescription, getStatus(taskStatus));
    }

    public void delete(Long id) {
        taskDao.delete(id);
    }

    private Status getStatus(String taskStatus) {

        Status status;

        switch (taskStatus){
            case "0":
                status = Status.IN_PROGRESS;
                break;
            case "1":
                status = Status.DONE;
                break;
            case "2":
                status = Status.PAUSED;
                break;
            default:
                status = null;
                break;
        }

        return status;
    }
}
