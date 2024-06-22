package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddWorkOrder extends Page {

    @FindBy(id="date")
    private WebElement dateField;

    @FindBy(id="startTime")
    private WebElement startTimeField;

    @FindBy(id="endTime")
    private WebElement endTimeFiled;

    @FindBy(id="description")
    private WebElement descriptionField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public AddWorkOrder(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "AddWorkOrderPage");
    }

    public void setDate(String date) {
        dateField.clear();
        dateField.sendKeys(date);
    }

    public void setStartTime(String startTime) {
        startTimeField.clear();
        startTimeField.sendKeys(startTime);
    }

    public void setEndTime(String endTime) {
        endTimeFiled.clear();
        endTimeFiled.sendKeys(endTime);
    }

    public void setDescription(String description) {
        descriptionField.clear();
        descriptionField.sendKeys(description);
    }

    public void add() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasStickyDate (String date) {
        return date.equals(dateField.getAttribute("value"));
    }

    public boolean hasStickyEndTime (String endTime) {
        return endTime.equals(endTimeFiled.getAttribute("value"));
    }

    public boolean hasStickyDescription (String description) {
        return description.equals(descriptionField.getAttribute("value"));
    }

    public boolean hasEmptyStartTime () {
        return startTimeField.getAttribute("value").isEmpty();
    }
}
