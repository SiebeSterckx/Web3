package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Page;

import java.time.LocalDate;

public class EditProject extends Page {

    @FindBy(id="endDate")
    private WebElement endDateField;

    @FindBy(id="startDate")
    private WebElement startDateField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public EditProject(WebDriver driver) {
        super(driver);
    }

    public void setEndDate(String endDate) {
        endDateField.clear();
        endDateField.sendKeys(endDate);
    }

    public void setStartDate(String startDate) {
        startDateField.clear();
        startDateField.sendKeys(startDate);
    }

    public void acceptEdit() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#addContainer div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessage2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#addContainer div.alert-danger ul li:nth-child(2)"));
        return (message.equals(errorMsg.getText()));
    }
}
