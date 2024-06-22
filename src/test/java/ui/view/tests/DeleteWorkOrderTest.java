package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.Config;
import ui.view.DriverHelper;
import ui.view.pages.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteWorkOrderTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.deleteIfExists("10:00", "WorkOrderTestDelete");

        AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
        addWorkOrder.setDate("06-06-2021");
        addWorkOrder.setStartTime("10:00");
        addWorkOrder.setEndTime("12:00");
        addWorkOrder.setDescription("WorkOrderTestDelete");
        addWorkOrder.add();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Delete_DeleteWorkOrder_SuccesfullyDeleted() {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.sortOnId();
        overviewWorkOrder.delete();

        DeleteWorkOrder deleteWorkOrder = PageFactory.initElements(driver, DeleteWorkOrder.class);
        assertEquals("Delete WorkOrder", deleteWorkOrder.getTitle());
        deleteWorkOrder.confirmDelete();

        OverviewWorkOrder overviewWorkOrder2 = PageFactory.initElements(driver, OverviewWorkOrder.class);
        assertEquals("Overview WorkOrders", overviewWorkOrder2.getTitle());
        assertTrue(overviewWorkOrder2.notContainsWorkOrderWithDateAndDescription("2021-06-06","WorkOrderTestDelete"));
    }

    @Test
    public void test_Delete_UnvalidIdGiven_ErrorMessageGivenForId() {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.sortOnId();
        overviewWorkOrder.delete();

        this.driver.get(Config.BASE_URL + "DeleteWorkOrderPage&id=1000");
        DeleteWorkOrder deleteWorkOrder = PageFactory.initElements(driver, DeleteWorkOrder.class);
        assertEquals("Overview WorkOrders", deleteWorkOrder.getTitle());
        assertTrue(deleteWorkOrder.hasErrorMessageInP("No workorder with id 1000"));
    }
}
