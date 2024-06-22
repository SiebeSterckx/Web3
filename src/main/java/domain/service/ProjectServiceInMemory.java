package domain.service;

import domain.model.Project;
import domain.model.Team;

import java.util.*;

public class ProjectServiceInMemory implements ProjectService {

    private final Map<Integer, Project> projects = new HashMap<Integer, Project>();
    private int projectid = 1;

    public ProjectServiceInMemory() {
    }

    @Override
    public Project get(int projectid) {
        if (projects.get(projectid) == null) {
            throw new DbException("No project with id " + projectid);
        }
        return projects.get(projectid);
    }

    @Override
    public List<Project> getAll() {
        return new ArrayList<Project>(projects.values());
    }

    @Override
    public List<Project> getAll(String sort) {
        return new ArrayList<Project>(projects.values());
    }

    @Override
    public List<Project> getAll(String sort, Team team) {
        ArrayList<Project> projects = new ArrayList<>();
        for (Project p : getAll()) {
            if (p.getTeam().equals(team)) {
                projects.add(p);
            }
        }
        return projects;
    }

    @Override
    public void add(Project project) {
        if (project == null) {
            throw new DbException("No project given");
        }
        if (projects.containsKey(project.getProjectid())) {
            throw new DbException("Project already exists");
        }
        project.setProjectid(projectid);
        projects.put(project.getProjectid(), project);
        projectid++;
    }

    @Override
    public Boolean equals(Project project) {
        if (project == null) {
            throw new DbException("No project given");
        }
        for (Project p : projects.values()) {
            if (p.getTeam().equals(project.getTeam())) {
                if (p.getStartdate().equals(project.getStartdate())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(Project project) {
        if (project == null) {
            throw new DbException("No project given");
        }
        if (!projects.containsKey(project.getProjectid())) {
            throw new DbException("Project doesn't exist");
        }
        projects.put(project.getProjectid(), project);
    }

    @Override
    public void delete(int projectid) {
        if (!projects.containsKey(projectid)) {
            throw new DbException("No project with id " + projectid);
        }
        projects.remove(projectid);
    }

    @Override
    public Project getProjectWithName(String name) {
        if (name == null) {
            throw new DbException("No name given");
        }
        for (Project p : projects.values()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
