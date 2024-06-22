package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

public class AddUser extends Page {

    @FindBy(id="firstName")
    private WebElement firstNameField;

    @FindBy(id="name")
    private WebElement nameField;

    @FindBy(id="email")
    private WebElement emailField;

    @FindBy(id="password")
    private WebElement passwordField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public AddUser(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "AddUserPage");
    }

    public void setFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        nameField.clear();
        nameField.sendKeys(lastName);
    }

    public void setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void add() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasStickyFirstName (String firstName) {
        return firstName.equals(firstNameField.getAttribute("value"));
    }

    public boolean hasStickyName (String name) {
        return name.equals(nameField.getAttribute("value"));
    }

    public boolean hasStickyEmail (String email) {
        return email.equals(emailField.getAttribute("value"));
    }

    public boolean hasStickyPassword (String password) {
        return password.equals(passwordField.getAttribute("value"));
    }
    public boolean hasEmptyName () {
        return nameField.getAttribute("value").isEmpty();
    }

    public boolean hasEmptyFirstName () {
        return firstNameField.getAttribute("value").isEmpty();
    }

    public boolean hasEmptyEmail () {
        return emailField.getAttribute("value").isEmpty();
    }

    public boolean hasEmptyPassword () {
        return passwordField.getAttribute("value").isEmpty();
    }
}

