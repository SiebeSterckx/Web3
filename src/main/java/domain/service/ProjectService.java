package domain.service;

import domain.model.Project;
import domain.model.Team;
import domain.model.WorkOrder;

import java.util.List;

public interface ProjectService {
    Project get(int projectid);

    List<Project> getAll();

    List<Project> getAll(String sort);

    List<Project> getAll(String sort, Team team);

    void add(Project project);

    Boolean equals(Project project);

    void update(Project project);

    void delete(int projectid);

    Project getProjectWithName(String name);
}
