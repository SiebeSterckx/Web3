package domain.service;

import domain.model.*;

import java.util.List;

public class AppService {
    private UserService users = new UserServiceSQL();
    private ProjectService projects = new ProjectServiceSQL();
    private WorkOrderService workorders = new WorkOrderServiceSQL();

    // User methods

    public void add(User user) {
        users.add(user);
    }

    public User getUser(int userid) {
        return users.get(userid);
    }

    public List<User> getAllUsers() {
        return users.getAll();
    }

    public Boolean equals(User user) {
        return users.equals(user);
    }

    public void update(User user) {
        users.update(user);
    }

    public void deleteUser(int userid) {
        users.delete(userid);
    }

    public User getUserWithEmail(String email) {
        return users.getUserByEmail(email);
    }

    // Project methods

    public void add(Project project) {
        projects.add(project);
    }

    public Project getProject(int projectid) {
        return projects.get(projectid);
    }

    public List<Project> getAllProjects(String sort) {
        return projects.getAll(sort);
    }
    public List<Project> getAllProjects(String sort, Team team) {
        return projects.getAll(sort, team);
    }

    public Boolean equals(Project project) {
        return projects.equals(project);
    }

    public void update(Project project) {
        projects.update(project);
    }

    public void deleteProject(int projectid) {
        projects.delete(projectid);
    }

    public Project getProjectWithName(String name) {
        return projects.getProjectWithName(name);
    }

    // WorkOrder methods

    public void add(WorkOrder workOrder) {
        workorders.add(workOrder);
    }

    public WorkOrder getWorkOrder(int workorderid) {
        return workorders.get(workorderid);
    }

    public List<WorkOrder> getAllWorkOrders(String sort) {
        return workorders.getAll(sort);
    }
    public List<WorkOrder> getAllWorkOrders(String sort, Team team) {
        return workorders.getAll(sort, team);
    }

    public Boolean equals(WorkOrder workOrder) {
        return workorders.equals(workOrder);
    }

    public void update(WorkOrder workOrder) {
        workorders.update(workOrder);
    }

    public void deleteWorkOrder(int workorderid) {
        workorders.delete(workorderid);
    }

    public List<WorkOrder> getWorkOrderWithDate(String date) {
        return workorders.getWorkOrdersWithDate(date);
    }

}