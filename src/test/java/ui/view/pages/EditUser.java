package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Page;

public class EditUser extends Page {

    @FindBy(id="email")
    private WebElement emailField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public EditUser(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void acceptEdit() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessage2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li:nth-child(2)"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessageInP (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger p"));
        return (message.equals(errorMsg.getText()));
    }
}
