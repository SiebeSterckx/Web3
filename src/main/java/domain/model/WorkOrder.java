package domain.model;

import domain.service.DbException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class WorkOrder {
    private int workorderid;

    private LocalDate date;

    private LocalTime starttime;

    private LocalTime endtime;

    private String description;
    private String name;
    private Team team;


    public WorkOrder(String name, Team team, LocalDate date, LocalTime starttime, LocalTime endtime, String description) {
        setName(name);
        setTeam(team);
        setDate(date);
        setStarttime(starttime);
        setEndtime(endtime);
        setDescription(description);
    }

    public WorkOrder(int workorderid, String name, Team team, LocalDate date, LocalTime starttime, LocalTime endtime, String description) {
        this(name, team, date, starttime, endtime, description);
        setWorkorderid(workorderid);
    }

    public WorkOrder() {
    }

    public void setWorkorderid(int workorderid) {
        if (workorderid < 0) {
            throw new DbException("No workorderid given");
        }
        this.workorderid = workorderid;
    }

    public int getWorkorderid() {
        return workorderid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new DbException("No date given");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new DbException("Date must be in the past");
        }
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setStarttime(LocalTime starttime) {
        if (starttime == null) {
            throw new DbException("No starttime given");
        }
        if (this.date != null) {
            if (this.date.isEqual(LocalDate.now())) {
                if (starttime.isAfter(LocalTime.now())) {
                    throw new DbException("Starttime must be in the past");
                }
            }
            this.starttime = starttime;
        }
    }


    public LocalTime getStarttime() {
        return starttime;
    }

    public void setEndtime(LocalTime endtime) {
        if (endtime == null) {
            throw new DbException("No endtime given");
        }
        if (this.date != null) {
            if (this.date.isEqual(LocalDate.now())) {
                if (endtime.isAfter(LocalTime.now())) {
                    throw new DbException("Endtime must be in the past");
                }
            }
            if (this.starttime != null) {
                if (endtime.isBefore(this.starttime)) {
                    throw new DbException("Endtime is before starttime");
                }
            }
            this.endtime = endtime;
        }
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public LocalTime getDuration() {
        if (this.starttime == null || this.endtime == null) {
            return null;
        }
        return LocalTime.of((int) this.starttime.until(this.endtime, ChronoUnit.HOURS), (int) this.starttime.until(this.endtime, ChronoUnit.MINUTES) % 60);
    }

    public String toString() {
        return workorderid + " " + date + " " + starttime + " " + endtime + " " + description;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
