package ui.view.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import ui.view.DriverHelper;
import ui.view.pages.Login;
import ui.view.pages.OverviewUser;
import ui.view.pages.AddUser;


public class RegisterUserTest {
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
	public void test_Register_AllFieldsFilledInCorrectly_UserIsRegistered_and_RoleIsEmployee() {

		OverviewUser overviewUser = PageFactory.initElements(driver, OverviewUser.class);
		overviewUser.deleteIfExists("jan.janssens@hotmail.com");

		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("Jan");
		addUser.setLastName("Janssens");
		addUser.setEmail("jan.janssens@hotmail.com");
		addUser.setPassword("1234");
		addUser.add();

		OverviewUser overviewUser2 = PageFactory.initElements(driver, OverviewUser.class);
		assertEquals("Overview Users", overviewUser2.getTitle());
		assertTrue(overviewUser2.containsUserWithEmail("jan.janssens@hotmail.com"));
	}

	
	@Test
	public void test_Register_FirstNameNotFilledIn_ErrorMessageGivenForFirstNameAndOtherFieldsValueKept(){
		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("");
		addUser.setLastName("Janssens");
		addUser.setEmail("jan.janssens@hotmail.com");
		addUser.setPassword("1234");
		addUser.add();

		assertEquals("Register User", addUser.getTitle());
		assertTrue(addUser.hasEmptyFirstName());
		assertTrue(addUser.hasErrorMessage("No firstname given"));
		assertTrue(addUser.hasStickyName("Janssens"));
		assertTrue(addUser.hasStickyEmail("jan.janssens@hotmail.com"));
		assertTrue(addUser.hasStickyPassword("1234"));
	}

	@Test
	public void test_Register_LastNameNotFilledIn_ErrorMessageGivenForLastNameAndOtherFieldsValueKept(){
		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("Jan");
		addUser.setLastName("");
		addUser.setEmail("jan.janssens@hotmail.com");
		addUser.setPassword("1234");
		addUser.add();

		assertEquals("Register User", addUser.getTitle());
		assertTrue(addUser.hasEmptyName());
		assertTrue(addUser.hasErrorMessage("No last name given"));
		assertTrue(addUser.hasStickyFirstName("Jan"));
		assertTrue(addUser.hasStickyEmail("jan.janssens@hotmail.com"));
		assertTrue(addUser.hasStickyPassword("1234"));
	}

	@Test
	public void test_Register_EmailNotFilledIn_ErrorMessageGivenForEmailAndOtherFieldsValueKept(){
		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("Jan");
		addUser.setLastName("Janssens");
		addUser.setEmail("");
		addUser.setPassword("1234");
		addUser.add();

		assertEquals("Register User", addUser.getTitle());
		assertTrue(addUser.hasEmptyEmail());
		assertTrue(addUser.hasErrorMessage("No email given"));
		assertTrue(addUser.hasStickyFirstName("Jan"));
		assertTrue(addUser.hasStickyName("Janssens"));
		assertTrue(addUser.hasStickyPassword("1234"));
	}


	@Test
	public void test_Register_PasswordNotFilledIn_ErrorMessageGivenForEmailAndOtherFieldsValueKept(){
		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("Jan");
		addUser.setLastName("Janssens");
		addUser.setEmail("jan.janssens@hotmail.com");
		addUser.setPassword("");
		addUser.add();

		assertEquals("Register User", addUser.getTitle());
		assertTrue(addUser.hasEmptyPassword());
		assertTrue(addUser.hasErrorMessage("No password given"));
		assertTrue(addUser.hasStickyFirstName("Jan"));
		assertTrue(addUser.hasStickyName("Janssens"));
		assertTrue(addUser.hasStickyEmail("jan.janssens@hotmail.com"));
	}
	
	@Test
	public void test_Register_UserAlreadyExists_ErrorMessageGiven(){
		AddUser addUser = PageFactory.initElements(driver, AddUser.class);
		addUser.setFirstName("Jan");
		addUser.setLastName("Janssens");
		addUser.setEmail("jan.janssens@hotmail.com");
		addUser.setPassword("1234");
		addUser.add();

		AddUser addUser2 = PageFactory.initElements(driver, AddUser.class);
		addUser2.setFirstName("Jan");
		addUser2.setLastName("Janssens");
		addUser2.setEmail("jan.janssens@hotmail.com");
		addUser2.setPassword("1234");
		addUser2.add();

		assertEquals("Register User", addUser.getTitle());
		assertTrue(addUser.hasErrorMessage("Email isn't unique"));
		assertTrue(addUser.hasStickyFirstName("Jan"));
		assertTrue(addUser.hasStickyName("Janssens"));
		assertTrue(addUser.hasStickyEmail("jan.janssens@hotmail.com"));
		assertTrue(addUser.hasStickyPassword("1234"));
	}
}
