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

public class EditProjectTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.deleteIfExists("ProjectTestEdit", "2023-04-28");
        overviewProject.deleteIfExists("ProjectTestEdit", "2023-04-26");

        AddProject addProject = PageFactory.initElements(driver, AddProject.class);
        addProject.setName("ProjectTestEdit");
        addProject.setStartDate("28-04-2023");
        addProject.setEndDate("29-04-2023");
        addProject.add();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Edit_EditProject_SuccesfullyEdited() {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.edit();

        EditProject editProject = PageFactory.initElements(driver, EditProject.class);
        assertEquals("Edit Project", editProject.getTitle());
        editProject.setStartDate("26-04-2023");
        editProject.acceptEdit();


        OverviewProject overviewProject2 = PageFactory.initElements(driver, OverviewProject.class);
        assertEquals("Overview Projects", overviewProject2.getTitle());
        assertTrue(overviewProject2.containsProjectWithNameAndStartDate("ProjectTestEdit","2023-04-26"));
        assertTrue(overviewProject2.notContainsProjectWithNameAndStartDate("ProjectTestEdit","2023-04-28"));
    }

    @Test
    public void test_Edit_EditProjectWithEndDateBeforeStartDate_ErrorMessageGivenForEndDateAndOtherFieldsValueKept() {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.edit();

        EditProject editProject = PageFactory.initElements(driver, EditProject.class);
        assertEquals("Edit Project", editProject.getTitle());
        editProject.setEndDate("27-04-2023");
        editProject.acceptEdit();
        assertTrue(editProject.hasErrorMessage("! Original Project is restored !"));
        assertTrue(editProject.hasErrorMessage2("Enddate is before startdate"));
    }
}
