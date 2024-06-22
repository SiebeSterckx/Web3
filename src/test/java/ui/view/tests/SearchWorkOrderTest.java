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

public class SearchWorkOrderTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewWorkOrder overviewWorkOrder = PageFactory.initElements(driver, OverviewWorkOrder.class);
        overviewWorkOrder.deleteIfExists("13:00", "WorkOrderTestSearch");
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Search_DateFieldFilledInCorrectly_WorkOrdersAreFound(){
        AddWorkOrder addWorkOrder = PageFactory.initElements(driver, AddWorkOrder.class);
        addWorkOrder.setDate("09-09-2021");
        addWorkOrder.setStartTime("13:00");
        addWorkOrder.setEndTime("18:00");
        addWorkOrder.setDescription("WorkOrderTestSearch");
        addWorkOrder.add();

        SearchWorkOrder searchWorkOrder = PageFactory.initElements(driver, SearchWorkOrder.class);
        assertEquals("Search WorkOrder", searchWorkOrder.getTitle());
        searchWorkOrder.fillInDate("09-09-2021");
        searchWorkOrder.search();

        FoundWorkOrder foundWorkOrder = PageFactory.initElements(driver, FoundWorkOrder.class);
        assertEquals("Search WorkOrder Result", foundWorkOrder.getTitle());
        assertTrue(foundWorkOrder.containsWorkOrderWithDate("2021-09-09"));
    }

    @Test
    public void test_Search_SearchForDateWhereNoWorkOrdersExists_WorkOrdersAreNotFound(){
        SearchWorkOrder searchWorkOrder = PageFactory.initElements(driver, SearchWorkOrder.class);
        assertEquals("Search WorkOrder", searchWorkOrder.getTitle());
        searchWorkOrder.fillInDate("01-01-2100");
        searchWorkOrder.search();

        FoundWorkOrder foundWorkOrder = PageFactory.initElements(driver, FoundWorkOrder.class);
        assertEquals("Search WorkOrder Result", foundWorkOrder.getTitle());
        assertTrue(foundWorkOrder.didNotFindAnyWorkOrders("There aren't any work orders on this date!"));
        assertTrue(foundWorkOrder.didNotFindAnyWorkOrders2("Click here to try again and search by another date"));

    }
}
