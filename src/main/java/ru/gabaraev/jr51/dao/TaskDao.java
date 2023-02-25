package ru.gabaraev.jr51.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gabaraev.jr51.models.Status;
import ru.gabaraev.jr51.models.Task;

import java.util.List;

@Component
public class TaskDao implements DAO{

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Task> taskLoad() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select t from Task t", Task.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Task.class, taskId);
    }

    @Transactional
    public void save(String taskDescription, Status taskStatus) {

        Session session = sessionFactory.getCurrentSession();
        Task task = new Task();

        task.setDescription(taskDescription);
        task.setStatus(taskStatus);

        session.save(task);
    }

    @Transactional
    public void update(Long id, String taskDescription, Status taskStatus) {

        Session session = sessionFactory.getCurrentSession();
        Task toUpdateTask = session.get(Task.class, id);

        toUpdateTask.setDescription(taskDescription);
        toUpdateTask.setStatus(taskStatus);
    }

    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Task.class, id));
    }




}
