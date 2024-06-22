package domain.service;

import domain.model.Project;
import domain.model.Team;
import util.DbConnectionService;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ProjectServiceSQL implements ProjectService {

    private final Connection connection;
    private final String schema;

    public ProjectServiceSQL() {
        this.connection = DbConnectionService.getDbConnection();
        this.schema = DbConnectionService.getSchema();
    }


    @Override
    public Project get(int projectid) {
        String sql = String.format("SELECT * from %s.projects WHERE projectid = %s", schema, projectid);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("projectid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate startDate = result.getDate("startdate").toLocalDate();
                if (result.getDate("enddate") != null) {
                    LocalDate endDate = result.getDate("enddate").toLocalDate();
                    Project project = new Project(id, name, team, startDate, endDate);
                    return project;
                } else {
                    Project project = new Project(id, name, team, startDate, null);
                    return project;
                }
            }
        } catch(SQLException e){
            throw new DbException("No project with id " + projectid);
        }
        throw new DbException("No project with id " + projectid);
    }

    @Override
    public List<Project> getAll() {
        ArrayList<Project> projects = new ArrayList<>();
        String sql = String.format("SELECT * from %s.projects order by projectid", schema);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("projectid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate startDate = result.getDate("startdate").toLocalDate();
                if (result.getDate("enddate") != null) {
                    LocalDate endDate = result.getDate("enddate").toLocalDate();
                    Project project = new Project(id, name, team, startDate, endDate);
                    project.setProjectid(id);
                    projects.add(project);
                } else {
                    Project project = new Project(id, name, team, startDate, null);
                    project.setProjectid(id);
                    projects.add(project);
                }
    }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return projects;
    }

    @Override
    public List<Project> getAll(String sort) {
        ArrayList<Project> projects = new ArrayList<>();
        String sql = String.format("SELECT * from %s.projects order by %s", schema, sort);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("projectid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate startDate = result.getDate("startdate").toLocalDate();
                if (result.getDate("enddate") != null) {
                    LocalDate endDate = result.getDate("enddate").toLocalDate();
                    Project project = new Project(id, name, team, startDate, endDate);
                    project.setProjectid(id);
                    projects.add(project);
                } else {
                    Project project = new Project(id, name, team, startDate, null);
                    project.setProjectid(id);
                    projects.add(project);
                }
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return projects;
    }

    @Override
    public List<Project> getAll(String sort, Team inTeam) {
        ArrayList<Project> projects = new ArrayList<>();
        String sql = String.format("SELECT * from %s.projects WHERE team ilike '%s' order by %s", schema, inTeam, sort);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("projectid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate startDate = result.getDate("startdate").toLocalDate();
                if (result.getDate("enddate") != null) {
                    LocalDate endDate = result.getDate("enddate").toLocalDate();
                    Project project = new Project(id, name, team, startDate, endDate);
                    project.setProjectid(id);
                    projects.add(project);
                } else {
                    Project project = new Project(id, name, team, startDate, null);
                    project.setProjectid(id);
                    projects.add(project);
                }
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return projects;
    }

    @Override
    public void add(Project project) {
        if (project == null) {
            throw new DbException("No project given");
        }
        for (Project p : getAll()) {
            if (project.getProjectid() == p.getProjectid()) {
                throw new DbException("Project already exists");
            }
        }
        if (equals(project)) {
            throw new DbException("project isn't unique");
        }
        try {
            String sql = String.format("insert into %s.projects (name, team, startdate, enddate) values (?,?, to_timestamp(?, 'yyyy-mm-dd'), to_timestamp " +
                    "(?, 'yyyy-mm-dd'))", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getTeam().toString());
            preparedStatement.setString(3, project.getStartdate().toString());
            if (project.getEnddate() != null)
                preparedStatement.setString(4, project.getEnddate().toString());
            else preparedStatement.setString(4, null);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Boolean equals(Project project) {
        if (project == null) {
            throw new DbException("No project given");
        }
        for (Project p : getAll()) {
            if (project.getName().equals(p.getName())) {
                if (project.getTeam().equals(p.getTeam())) {
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
        for (Project p : getAll()) {
            if (project.getProjectid() == p.getProjectid()) {
                try {
                    String sql = String.format("UPDATE %s.projects SET name=?, team=?, startdate=to_timestamp(?, 'yyyy-mm-dd'), enddate=to_timestamp(?, " +
                                    "'yyyy-mm-dd') WHERE projectid=%s", schema,
                            project.getProjectid());
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, project.getName());
                    preparedStatement.setString(2, project.getTeam().toString());
                    preparedStatement.setString(3, project.getStartdate().toString());
                    if (project.getEnddate() != null)
                        preparedStatement.setString(4, project.getEnddate().toString());
                    else preparedStatement.setString(4, null);
                    preparedStatement.execute();

                } catch (SQLException e) {
                    throw new DbException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void delete(int projectid) {
        if (projectid < 0) {
            throw new DbException("No projectid given");
        }
        for (Project p : getAll()) {
            if (projectid == p.getProjectid()) {
                try {
                    String sql = String.format("delete from %s.projects where projectid = ?", schema);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, projectid);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new DbException(e.getMessage());
                }
            }
        }
    }

    @Override
    public Project getProjectWithName(String name) {
        if (name == null) {
            throw new DbException("No name given");
        }
        for (Project p : getAll()) {
            if (name.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

    private Connection getConnection() {
        return this.connection;
    }
}
