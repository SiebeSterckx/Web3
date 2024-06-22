package domain.service;

import domain.model.Project;
import domain.model.Team;
import domain.model.User;
import domain.model.WorkOrder;
import util.DbConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WorkOrderServiceSQL implements WorkOrderService {

    private final Connection connection;
    private final String schema;

    public WorkOrderServiceSQL() {
        this.connection = DbConnectionService.getDbConnection();
        this.schema = DbConnectionService.getSchema();
    }
    @Override
    public WorkOrder get(int workorderid) {
        String sql = String.format("SELECT * from %s.workorders where workorderid=%s", schema, workorderid);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                int id = result.getInt("workorderid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate date = result.getDate("date").toLocalDate();
                LocalTime startTime = result.getTime("starttime").toLocalTime();
                LocalTime endTime = result.getTime("endtime").toLocalTime();
                String description = result.getString("description");
                WorkOrder workOrder = new WorkOrder(id, name, team, date, startTime, endTime, description);
                workOrder.setWorkorderid(id);
                return workOrder;
            }
        } catch(SQLException e){
            throw new DbException("No workorder with id " + workorderid);
        }
        throw new DbException("No workorder with id " + workorderid);
    }

    @Override
    public List<WorkOrder> getAll() {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        String sql = String.format("SELECT * from %s.workorders order by workorderid", schema);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("workorderid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate date = result.getDate("date").toLocalDate();
                LocalTime startTime = result.getTime("starttime").toLocalTime();
                LocalTime endTime = result.getTime("endtime").toLocalTime();
                String description = result.getString("description");
                WorkOrder workorder = new WorkOrder(id, name, team, date, startTime, endTime, description);
                workorder.setWorkorderid(id);
                workOrders.add(workorder);
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return workOrders;
    }

    @Override
    public List<WorkOrder> getAll(String sort) {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        String sql = String.format("SELECT * from %s.workorders order by %s", schema, sort);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("workorderid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate date = result.getDate("date").toLocalDate();
                LocalTime startTime = result.getTime("starttime").toLocalTime();
                LocalTime endTime = result.getTime("endtime").toLocalTime();
                String description = result.getString("description");
                WorkOrder workorder = new WorkOrder(id, name, team, date, startTime, endTime, description);
                workorder.setWorkorderid(id);
                workOrders.add(workorder);
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return workOrders;
    }

    @Override
    public List<WorkOrder> getAll(String sort, Team inTeam) {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        String sql = String.format("SELECT * from %s.workorders WHERE team ilike '%s' order by %s", schema, inTeam, sort);
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("workorderid");
                String name = result.getString("name");
                String teamStr = result.getString("team");
                Team team = Team.valueOf(teamStr.toUpperCase());
                LocalDate date = result.getDate("date").toLocalDate();
                LocalTime startTime = result.getTime("starttime").toLocalTime();
                LocalTime endTime = result.getTime("endtime").toLocalTime();
                String description = result.getString("description");
                WorkOrder workorder = new WorkOrder(id, name, team, date, startTime, endTime, description);
                workorder.setWorkorderid(id);
                workOrders.add(workorder);
            }
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        return workOrders;
    }

    @Override
    public void add(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        for (WorkOrder w : getAll()) {
            if (workOrder.getWorkorderid() == w.getWorkorderid()) {
                throw new DbException("Workorder already exists");
            }
        }
        if (equals(workOrder)) {
            throw new DbException("Workorder isn't unique");
        }
        if (overlaps(workOrder)) {
            throw new DbException("Workorder overlaps with another workorder");
        }
        try {
            String sql = String.format("insert into %s.workorders (name, team, date, starttime, endtime, description) " +
                    "values (?, ?, to_date(?, 'yyyy-mm-dd'),to_timestamp(?, 'HH24:MI'),to_timestamp(?, 'HH24:MI'),?)", schema);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, workOrder.getName());
            preparedStatement.setString(2, workOrder.getTeam().toString());
            preparedStatement.setString(3, workOrder.getDate().toString());
            preparedStatement.setString(4, workOrder.getStarttime().toString());
            preparedStatement.setString(5, workOrder.getEndtime().toString());
            preparedStatement.setString(6, workOrder.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Boolean equals(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        for (WorkOrder w : getAll()) {
            if (w.equals(workOrder)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        try {
            String sql = String.format("UPDATE %s.workorders SET date=to_date(?, 'yyyy-mm-dd'), starttime=to_timestamp(?, 'HH24:MI'), endtime=to_timestamp(?, 'HH24:MI'), description=? WHERE workorderid=%s", schema, workOrder.getWorkorderid());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, workOrder.getDate().toString());
            preparedStatement.setString(2, workOrder.getStarttime().toString());
            preparedStatement.setString(3, workOrder.getEndtime().toString());
            preparedStatement.setString(4, workOrder.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void delete(int workorderid) {
        if (workorderid < 0) {
            throw new DbException("No workorderid given");
        }
        for (WorkOrder w : getAll()) {
            if (workorderid == w.getWorkorderid()) {
                try {
                    String sql = String.format("delete from %s.workorders where workorderid = ?", schema);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, workorderid);
                    preparedStatement.executeUpdate();

                } catch (SQLException e) {
                    throw new DbException(e.getMessage());
                }
            }
        }

    }

    @Override
    public Boolean overlaps(WorkOrder workorder) {
        if (workorder == null) {
            throw new DbException("No workorder given");
        }
        for (WorkOrder w : getAll()) {
            if (w.getName().equals(workorder.getName())) {
                if (w.getDate().equals(workorder.getDate())) {
                    if (isDuring(w, workorder)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isDuring(WorkOrder w, WorkOrder workorder) {
        if (workorder == null || w == null) {
            throw new DbException("No workorder given");
        }
        if (w.getStarttime().equals(workorder.getStarttime()) || w.getEndtime().equals(workorder.getEndtime())) {
            return true;
        }
        if (workorder.getStarttime().isBefore(w.getStarttime())) {
            if (workorder.getEndtime().isBefore(w.getStarttime())) {
                return false;
            }
            return true;
        }
        if (workorder.getStarttime().isAfter(w.getEndtime())) {
            return false;
        }
        return true;
    }

    @Override
    public List<WorkOrder> getWorkOrdersWithDate(String date) {
        List<WorkOrder> workorderList = new ArrayList<>();
        if (date == null) {
            throw new DbException("No date given");
        }
        for (WorkOrder w : getAll()) {
            if (w.getDate().toString().equals(date)) {
                workorderList.add(w);
            }
        }
        return workorderList;
    }

    private Connection getConnection() {
        return this.connection;
    }

}
