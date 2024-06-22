package domain.service;


import domain.model.Team;
import domain.model.WorkOrder;

import java.util.List;

public interface WorkOrderService {

    WorkOrder get(int workorderid);

    List<WorkOrder> getAll();

    List<WorkOrder> getAll(String sort);

    List<WorkOrder> getAll(String sort, Team team);

    void add(WorkOrder workOrder);

    Boolean equals(WorkOrder workOrder);

    void update(WorkOrder workOrder);

    void delete(int workorderid);

    Boolean overlaps(WorkOrder workOrder);

    Boolean isDuring(WorkOrder w, WorkOrder workOrder);

    List<WorkOrder> getWorkOrdersWithDate(String date);

}
