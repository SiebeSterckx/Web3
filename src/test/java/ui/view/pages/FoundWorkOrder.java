package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.view.Page;

public class FoundWorkOrder extends Page {

    public FoundWorkOrder(WebDriver driver) {
        super(driver);
    }

    public boolean containsWorkOrderWithDate(String date) {
        WebElement listItem= this.driver.findElement(By.cssSelector("table tr:nth-child(3) td"));
        boolean found= listItem.getText().equals(date);

        return found;
    }

    public boolean didNotFindAnyWorkOrders (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewWorkOrderContainer #OverviewWorkOrderBox ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean didNotFindAnyWorkOrders2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewWorkOrderContainer #OverviewWorkOrderBox ul li:nth-child(2)"));
        return (message.equals(errorMsg.getText()));
    }
}
