package ui.view.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.view.Config;
import ui.view.DriverHelper;
import ui.view.pages.EditUser;
import ui.view.pages.Login;
import ui.view.pages.OverviewUser;
import ui.view.pages.AddUser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditUserTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverHelper.getDriver();

        Login login = PageFactory.initElements(driver, Login.class);
        login.setEmail("director@ucll.be");
        login.setPassword("t");
        login.logIn();

        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.deleteIfExists("siebe2.sterckx@gmail.com");
        overviewUser.deleteIfExists("michiel.verhulst@gmail.com");

        AddUser addUser = PageFactory.initElements(driver, AddUser.class);
        addUser.setFirstName("Siebe");
        addUser.setLastName("Sterckx");
        addUser.setEmail("siebe2.sterckx@gmail.com");
        addUser.setPassword("1234");
        addUser.add();
    }

    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void test_Edit_EditUser_SuccesfullyEdited() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.edit();

        EditUser editUser = PageFactory.initElements(driver, EditUser.class);
        assertEquals("Edit User", editUser.getTitle());
        editUser.setEmail("michiel.verhulst@gmail.com");
        editUser.acceptEdit();


        OverviewUser overviewUser2 = PageFactory.initElements(driver, OverviewUser.class);
        assertEquals("Overview Users", overviewUser2.getTitle());
        assertTrue(overviewUser2.containsUserWithEmail("michiel.verhulst@gmail.com"));
        assertTrue(overviewUser2.notContainsUserWithEmail("siebe2.sterckx@gmail.com"));
    }

    @Test
    public void test_Edit_EditUserWithUnvalidEmail_ErrorMessageGivenForEmailAndOtherFieldsValueKept() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.edit();

        EditUser editUser = PageFactory.initElements(driver, EditUser.class);
        assertEquals("Edit User", editUser.getTitle());
        editUser.setEmail("randomEmail");

        editUser.acceptEdit();
        assertTrue(editUser.hasErrorMessage("! Original User is restored !"));
        assertTrue(editUser.hasErrorMessage2("Email not valid"));
    }

    @Test
    public void test_Edit_UnvalidIdGiven_ErrorMessageGivenForId() {
        OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
        overviewUser.edit();

        this.driver.get(Config.BASE_URL + "EditUserPage&id=100");
        EditUser editUser = PageFactory.initElements(driver, EditUser.class);
        assertEquals("Overview Users", editUser.getTitle());
        assertTrue(editUser.hasErrorMessageInP("No user with id 100"));
    }
}
