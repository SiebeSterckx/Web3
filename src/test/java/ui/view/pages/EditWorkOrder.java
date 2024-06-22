package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Page;

import java.time.LocalDate;
import java.time.LocalTime;

public class EditWorkOrder extends Page {

    @FindBy(id="startTime")
    private WebElement startTimeField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public EditWorkOrder(WebDriver driver) {
        super(driver);
    }

    public void setStartTime(String startTime) {
        startTimeField.clear();
        startTimeField.sendKeys(startTime);
    }

    public void acceptEdit() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#addContainer div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessage2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li:nth-child(2)"));
        return (message.equals(errorMsg.getText()));
    }
}
