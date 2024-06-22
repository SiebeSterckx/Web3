package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.AddProject;
import ui.view.pages.Login;
import ui.view.pages.OverviewProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddProjectTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.deleteIfExists("ProjectTestAdd", "2023-04-28");
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Add_AllFieldsFilledInCorrectly_ProjectIsAddedSuccessfullyAndTeamIsAlpha() {
        AddProject addProject = PageFactory.initElements(driver, AddProject.class);
        addProject.setName("ProjectTestAdd");
        addProject.setStartDate("28-04-2023");
        addProject.setEndDate("29-04-2023");
        addProject.add();

        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        assertEquals("Overview Projects", overviewProject.getTitle());
        assertTrue(overviewProject.containsProjectWithNameAndStartDate("ProjectTestAdd", "2023-04-28"));

    }

    @Test
    public void test_Add_StartDateNotFilledIn_ErrorMessageGivenForStartDateAndOtherFieldsValueKept(){
        AddProject addProject = PageFactory.initElements(driver, AddProject.class);
        addProject.setName("ProjectTestAdd2");
        addProject.setStartDate("");
        addProject.setEndDate("30-04-2023");
        addProject.add();

        assertEquals("Add Project", addProject.getTitle());
        assertTrue(addProject.hasEmptyStartDate());
        assertTrue(addProject.hasErrorMessage("Start date can't be empty"));
        assertTrue(addProject.hasStickyName("ProjectTestAdd2"));
    }
}
