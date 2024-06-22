package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

import java.time.LocalDate;

public class AddProject extends Page {

    @FindBy(id="name")
    private WebElement nameField;

    @FindBy(id="startDate")
    private WebElement startDateField;

    @FindBy(id="endDate")
    private WebElement endDateField;

    @FindBy(id="signUp")
    private WebElement submitButton;

    public AddProject(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "AddProjectPage");
    }

    public void setName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void setStartDate(String startDate) {
        startDateField.clear();
        startDateField.sendKeys(startDate);
    }

    public void setEndDate(String endDate) {
        endDateField.clear();
        endDateField.sendKeys(endDate);
    }

    public void add() {
        submitButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasStickyName (String name) {
        return name.equals(nameField.getAttribute("value"));
    }

    public boolean hasEmptyStartDate () {
        return startDateField.getAttribute("value").isEmpty();
    }

}
