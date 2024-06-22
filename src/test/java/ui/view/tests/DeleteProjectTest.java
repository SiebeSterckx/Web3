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

public class DeleteProjectTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.deleteIfExists("ProjectTestDelete", "2023-04-01");

        AddProject addProject = PageFactory.initElements(driver, AddProject.class);
        addProject.setName("ProjectTestDelete");
        addProject.setStartDate("01-04-2023");
        addProject.setEndDate("02-04-2023");
        addProject.add();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Delete_DeleteProject_SuccesfullyDeleted() {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.delete();

        DeleteProject deleteProject = PageFactory.initElements(driver, DeleteProject.class);
        assertEquals("Delete Project", deleteProject.getTitle());
        deleteProject.confirmDelete();

        OverviewProject overviewProject2 = PageFactory.initElements(driver, OverviewProject.class);
        assertEquals("Overview Projects", overviewProject2.getTitle());
        assertTrue(overviewProject2.notContainsProjectWithNameAndStartDate("ProjectTestDelete","2023-04-01"));
    }

    @Test
    public void test_Delete_UnvalidIdGiven_ErrorMessageGivenForId() {
        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.delete();

        this.driver.get(Config.BASE_URL + "DeleteProjectPage&id=1000");
        DeleteProject deleteProject = PageFactory.initElements(driver, DeleteProject.class);
        assertEquals("Overview Projects", deleteProject.getTitle());
        assertTrue(deleteProject.hasErrorMessageInP("No project with id 1000"));
    }
}
