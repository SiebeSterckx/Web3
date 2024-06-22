package domain.model;

import domain.service.DbException;

import java.time.LocalDate;

public class Project {

    private int projectid;
    private String name;
    private Team team;
    private LocalDate startdate;
    private LocalDate enddate;

    public Project(String name, Team team, LocalDate startdate) {
        setName(name);
        setTeam(team);
        setStartdate(startdate);
    }

    public Project(int projectid, String name, Team team, LocalDate startdate) {
        this(name, team, startdate);
        setProjectid(projectid);
    }
    public Project(String name, Team team,  LocalDate startdate, LocalDate enddate) {
        this(name, team, startdate);
        setEnddate(enddate);
    }

    public Project(int projectid, String name, Team team, LocalDate startdate, LocalDate enddate) {
        this(projectid, name, team, startdate);
        setEnddate(enddate);
    }

    public Project() {
    }

    public void setProjectid(int projectid) {
        if (projectid < 0) {
            throw new DbException("No projectid given");
        }
        this.projectid = projectid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new DbException("No name given");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTeam(Team team) {
        if (team == null) {
            throw new DbException("No team given");
        }
        this.team = team;
    }
    public void setTeam(String team) {
        try {
            this.team = Team.valueOf(team.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DbException("There is no team with value " + team);
        }
    }


    public Team getTeam() {
        return team;
    }

    public void setStartdate(LocalDate startdate) {
        if (startdate == null) {
            throw new DbException("No startdate given");
        }
        this.startdate = startdate;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setEnddate(LocalDate enddate) {
        if (enddate == null) {
            this.enddate = null;
        } else {
            if (this.startdate != null) {
                if (enddate.isBefore(this.startdate)) {
                    throw new DbException("Enddate is before startdate");
                }
                this.enddate = enddate;
            }
        }
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public String toString() {
        return projectid + " " + name + " " + startdate + " " + enddate;
    }


}
