package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.view.Config;
import ui.view.Page;

import java.util.ArrayList;

public class FoundProject extends Page {

    public FoundProject(WebDriver driver) {
        super(driver);
    }

    public boolean containsProjectWithName(String name) {
        WebElement listItem= this.driver.findElement(By.cssSelector("tbody tr td:nth-child(2)"));
        boolean found= listItem.getText().equals(name);

        return found;
    }

    public boolean didNotFindProject (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewContainer ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean didNotFindProject2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewContainer ul li:nth-child(2)"));
        return (message.equals(errorMsg.getText()));
    }
}
