package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditWorkOrderTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.deleteIfExists("08:00", "WorkOrderTestEdit");
        overviewWorkOrder.deleteIfExists("09:00", "WorkOrderTestEdit");


        AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
        addWorkOrder.setDate("07-07-2021");
        addWorkOrder.setStartTime("08:00");
        addWorkOrder.setEndTime("11:00");
        addWorkOrder.setDescription("WorkOrderTestEdit");
        addWorkOrder.add();
    }

        @After
        public void clean() {
            driver.quit();
        }

    @Test
    public void test_Edit_EditWorkOrder_SuccesfullyEdited() {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.sortOnId();
        overviewWorkOrder.edit();

        EditWorkOrder editWorkOrder = PageFactory.initElements(driver, EditWorkOrder.class);
        assertEquals("Edit WorkOrder", editWorkOrder.getTitle());
        editWorkOrder.setStartTime("09:00");
        editWorkOrder.acceptEdit();


        OverviewWorkOrder overviewWorkOrder2 = PageFactory.initElements(driver, OverviewWorkOrder.class);
        assertEquals("Overview WorkOrders", overviewWorkOrder2.getTitle());
        assertTrue(overviewWorkOrder2.containsWorkOrderWithDateAndDescription("09:00","WorkOrderTestEdit"));
        assertTrue(overviewWorkOrder2.notContainsWorkOrderWithDateAndDescription("08:00","WorkOrderTestEdit"));
    }

    @Test
    public void test_Edit_EditWorkOrderWithEndTimeBeforeStartTime_ErrorMessageGivenForEndTimeAndOtherFieldsValueKept() {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.sortOnId();
        overviewWorkOrder.edit();

        EditWorkOrder editWorkOrder = PageFactory.initElements(driver, EditWorkOrder.class);
        assertEquals("Edit WorkOrder", editWorkOrder.getTitle());
        editWorkOrder.setStartTime("12:00");

        editWorkOrder.acceptEdit();
        assertTrue(editWorkOrder.hasErrorMessage("! Original Work order is restored !"));
        assertTrue(editWorkOrder.hasErrorMessage2("Endtime is before starttime"));
    }
}
