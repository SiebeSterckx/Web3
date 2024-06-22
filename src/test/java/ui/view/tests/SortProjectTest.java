package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.*;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortProjectTest {

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
    public void test_Sort_ChoosenValidSortOrder_OverviewProjectIsSuccessfullySorted() throws ParseException {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);

        if(!overviewProject.deleteAllIfExists()){
            AddProject addProject = PageFactory.initElements(driver, AddProject.class);
            addProject.setName("ProjectTestSort1");
            addProject.setStartDate("28-04-2023");
            addProject.setEndDate("29-04-2023");
            addProject.add();

            AddProject addProject2 = PageFactory.initElements(driver, AddProject.class);
            addProject2.setName("ProjectTestSort2");
            addProject2.setStartDate("27-04-2023");
            addProject2.setEndDate("29-04-2023");
            addProject2.add();

        }

        OverviewProject overviewProject2 = PageFactory.initElements(driver, OverviewProject.class);
        assertEquals("Overview Projects", overviewProject2.getTitle());
        overviewProject2.sortOnStartDate();
        overviewProject2.isSortedOnStartDate();
    }

    @Test
    public void test_Sort_ChoosenValidSortOrderButEmptyDatabase_OverviewIsShownEmpty() {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);

        while (overviewProject.deleteAllIfExists()){
            assertEquals("Overview Projects", overviewProject.getTitle());
            overviewProject.sortOnId();
            overviewProject.delete();
            DeleteProject deleteProject = PageFactory.initElements(driver, DeleteProject.class);
            assertEquals("Delete Project", deleteProject.getTitle());
            deleteProject.confirmDelete();
        }

        overviewProject.sortOnId();
        assertTrue(overviewProject.hasErrorMessage("There aren't any projects yet!"));
        assertTrue(overviewProject.hasErrorMessage2("Click here to add a project"));

        overviewProject.sortOnStartDate();
        assertTrue(overviewProject.hasErrorMessage("There aren't any projects yet!"));
        assertTrue(overviewProject.hasErrorMessage2("Click here to add a project"));

    }
}
