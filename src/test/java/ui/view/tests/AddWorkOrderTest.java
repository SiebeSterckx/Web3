package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.AddWorkOrder;
import ui.view.pages.Login;
import ui.view.pages.OverviewWorkOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddWorkOrderTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.deleteIfExists("14:00", "WorkOrderTestAdd");
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Add_AllFieldsFilledInCorrectly_WorkOrderIsAddedSuccessfully() {

        AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
        addWorkOrder.setDate("05-05-2021");
        addWorkOrder.setStartTime("14:00");
        addWorkOrder.setEndTime("16:00");
        addWorkOrder.setDescription("WorkOrderTestAdd");
        addWorkOrder.add();

        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        assertEquals("Overview WorkOrders", overviewWorkOrder.getTitle());

        assertTrue(overviewWorkOrder.containsWorkOrderWithDateAndDescription("14:00", "WorkOrderTestAdd"));

    }

    @Test
    public void test_Add_StartTimeNotFilledIn_ErrorMessageGivenForStartTimeAndOtherFieldsValueKept(){
        AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
        addWorkOrder.setDate("12-05-2021");
        addWorkOrder.setEndTime("17:00");
        addWorkOrder.setDescription("WorkOrderTestAdd2");
        addWorkOrder.add();

        assertEquals("Add WorkOrder", addWorkOrder.getTitle());
        assertTrue(addWorkOrder.hasEmptyStartTime());
        assertTrue(addWorkOrder.hasErrorMessage("No starttime given"));
        assertTrue(addWorkOrder.hasStickyEndTime("17:00"));
        assertTrue(addWorkOrder.hasStickyDate("2021-05-12"));
        assertTrue(addWorkOrder.hasStickyDescription("WorkOrderTestAdd2"));
    }
}
