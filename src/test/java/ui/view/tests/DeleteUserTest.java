package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.Config;
import ui.view.DriverHelper;
import ui.view.pages.DeleteUser;
import ui.view.pages.Login;
import ui.view.pages.OverviewUser;
import ui.view.pages.AddUser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteUserTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.deleteIfExists("jan.janssens2@hotmail.com");

        AddUser addUser = PageFactory.initElements(driver, AddUser.class);
        addUser.setFirstName("Jan");
        addUser.setLastName("Janssens");
        addUser.setEmail("jan.janssens2@hotmail.com");
        addUser.setPassword("1234");
        addUser.add();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Delete_DeleteUser_SuccesfullyDeleted() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.delete();

        DeleteUser deleteUser = PageFactory.initElements(driver, DeleteUser.class);
        assertEquals("Delete User", deleteUser.getTitle());
        deleteUser.confirmDelete();

        OverviewUser overviewUser2 = PageFactory.initElements(driver, OverviewUser.class);
        assertEquals("Overview Users", overviewUser2.getTitle());
        assertTrue(overviewUser.notContainsUserWithEmail("jan.janssens2@hotmail.com"));
    }

    @Test
    public void test_Delete_CancelDeleteUser_DeleteCanceled() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.delete();

        DeleteUser deleteUser = PageFactory.initElements(driver, DeleteUser.class);
        assertEquals("Delete User", deleteUser.getTitle());
        deleteUser.declineDelete();

        OverviewUser overviewUser2 = PageFactory.initElements(driver, OverviewUser.class);
        assertEquals("Overview Users", overviewUser2.getTitle());
        assertTrue(overviewUser.containsUserWithEmail("jan.janssens2@hotmail.com"));
    }

    @Test
    public void test_Delete_UnvalidIdGiven_ErrorMessageGivenForId() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.delete();

        this.driver.get(Config.BASE_URL + "DeleteUserPage&id=1000");
        DeleteUser deleteUser = PageFactory.initElements(driver, DeleteUser.class);
        assertEquals("Overview Users", deleteUser.getTitle());
        assertTrue(deleteUser.hasErrorMessageInP("No user with id 1000"));
    }
}
