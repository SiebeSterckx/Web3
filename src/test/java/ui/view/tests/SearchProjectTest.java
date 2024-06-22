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

public class SearchProjectTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewProject overviewProject = PageFactory.initElements(driver, OverviewProject.class);
        overviewProject.deleteIfExists("ProjectTestSearch", "2023-04-05");
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Search_NameFieldFilledInCorrectly_ProjectIsFound(){
        AddProject addProject = PageFactory.initElements(driver, AddProject.class);
        addProject.setName("ProjectTestSearch");
        addProject.setStartDate("05-04-2023");
        addProject.setEndDate("06-04-2023");
        addProject.add();

        SearchProject searchProject = PageFactory.initElements(driver, SearchProject.class);
        assertEquals("Search Project", searchProject.getTitle());
        searchProject.fillInName("ProjectTestSearch");
        searchProject.search();

        FoundProject foundProject = PageFactory.initElements(driver, FoundProject.class);
        assertEquals("Search Project Result", foundProject.getTitle());
        assertTrue(foundProject.containsProjectWithName("ProjectTestSearch"));
    }

    @Test
    public void test_Search_SearchForProjectThatNotExists_ProjectIsNotFound(){
        SearchProject searchProject = PageFactory.initElements(driver, SearchProject.class);
        assertEquals("Search Project", searchProject.getTitle());
        searchProject.fillInName("testennnnnnn");
        searchProject.search();

        FoundProject foundProject = PageFactory.initElements(driver, FoundProject.class);
        assertEquals("Search Project Result", foundProject.getTitle());
        assertTrue(foundProject.didNotFindProject("There is no project with this name"));
        assertTrue(foundProject.didNotFindProject2("Click here to try again and search by another name"));

    }
}
