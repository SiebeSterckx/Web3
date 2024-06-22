package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.AddWorkOrder;
import ui.view.pages.DeleteWorkOrder;
import ui.view.pages.Login;
import ui.view.pages.OverviewWorkOrder;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortWorkOrderTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Sort_ChoosenValidSortOrder_OverviewWorkOrderIsSuccessfullySorted() throws ParseException {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);

        if(!overviewWorkOrder.deleteAllIfExists()){
            AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
            addWorkOrder.setDate("05-05-2021");
            addWorkOrder.setStartTime("14:00");
            addWorkOrder.setEndTime("16:00");
            addWorkOrder.setDescription("WorkOrderTestSort1");
            addWorkOrder.add();

            AddWorkOrder addWorkOrder2 = PageFactory.initElements(driver, AddWorkOrder.class);
            addWorkOrder2.setDate("04-04-2021");
            addWorkOrder2.setStartTime("13:00");
            addWorkOrder2.setEndTime("15:00");
            addWorkOrder2.setDescription("WorkOrderTestSort2");
            addWorkOrder2.add();
        }

        OverviewWorkOrder overviewWorkOrder2 = PageFactory.initElements(driver, OverviewWorkOrder.class);
        assertEquals("Overview WorkOrders", overviewWorkOrder2.getTitle());
        overviewWorkOrder2.sortOnDate();
        overviewWorkOrder2.isSortedOnDate();
    }

    @Test
    public void test_Sort_ChoosenValidSortOrderButEmptyDatabase_OverviewIsShownEmpty() {
        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);

        while (overviewWorkOrder.deleteAllIfExists()){
            assertEquals("Overview WorkOrders", overviewWorkOrder.getTitle());
            overviewWorkOrder.sortOnId();
            overviewWorkOrder.delete();
            DeleteWorkOrder deleteWorkOrder = PageFactory.initElements(driver, DeleteWorkOrder.class);
            assertEquals("Delete WorkOrder", deleteWorkOrder.getTitle());
            deleteWorkOrder.confirmDelete();
        }

        overviewWorkOrder.sortOnId();
        assertTrue(overviewWorkOrder.hasErrorMessage("There aren't any work orders yet!"));
        assertTrue(overviewWorkOrder.hasErrorMessage2("Click here to add a work order"));

        overviewWorkOrder.sortOnDate();
        assertTrue(overviewWorkOrder.hasErrorMessage("There aren't any work orders yet!"));
        assertTrue(overviewWorkOrder.hasErrorMessage2("Click here to add a work order"));

    }
}
