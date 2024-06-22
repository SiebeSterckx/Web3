package domain.service;

import domain.model.Project;
import domain.model.Team;
import domain.model.WorkOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkOrderServiceInMemory implements WorkOrderService {

    private final Map<Integer, WorkOrder> workorders = new HashMap<Integer, WorkOrder>();
    private int workorderid = 1;

    public WorkOrderServiceInMemory() {
    }
    @Override
    public WorkOrder get(int workorderid) {
        if (workorders.get(workorderid) == null) {
            throw new DbException("No workorder with id " + workorderid);
        }
        return workorders.get(workorderid);
    }

    @Override
    public List<WorkOrder> getAll() {
        return new ArrayList<WorkOrder>(workorders.values());
    }
    @Override
    public List<WorkOrder> getAll(String sort) {
        return new ArrayList<WorkOrder>(workorders.values());
    }
    @Override
    public List<WorkOrder> getAll(String sort, Team team) {
        ArrayList<WorkOrder> workOrders = new ArrayList<>();
        for (WorkOrder w : getAll()) {
            if (w.getTeam().equals(team)) {
                workOrders.add(w);
            }
        }
        return workOrders;
    }

    @Override
    public void add(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        if (workorders.containsKey(workOrder.getWorkorderid())) {
            throw new DbException("Workorder already exists");
        }
        if (overlaps(workOrder)) {
            throw new DbException("Workorder overlaps with another workorder");
        }
        workOrder.setWorkorderid(workorderid);
        workorders.put(workOrder.getWorkorderid(), workOrder);
        workorderid++;
    }

    @Override
    public Boolean equals(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        for (WorkOrder w : workorders.values()) {
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
        if (!workorders.containsKey(workOrder.getWorkorderid())) {
            throw new DbException("Workorder doesn't exist");
        }
        workorders.put(workOrder.getWorkorderid(), workOrder);
    }

    @Override
    public void delete(int workorderid) {
        if (!workorders.containsKey(workorderid)) {
            throw new DbException("No workorder with id " + workorderid);
        }
        workorders.remove(workorderid);
    }

    @Override
    public Boolean overlaps(WorkOrder workOrder) {
        if (workOrder == null) {
            throw new DbException("No workorder given");
        }
        for (WorkOrder w : getAll()) {
            if (w.getName().equals(workOrder.getName())) {
                if (w.getDate().equals(workOrder.getDate())) {
                    if (isDuring(w, workOrder)) {
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
        for (WorkOrder w : workorders.values()) {
            if (w.getDate().toString().equals(date)) {
                workorderList.add(w);
            }
        }
        return workorderList;
    }
}

