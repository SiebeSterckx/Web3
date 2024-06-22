package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class OverviewWorkOrder extends Page {

    @FindBy(css = "table:last-child .verwijder a")
    private WebElement deleteButton;

    @FindBy(css = "table:last-child .edit a")
    private WebElement editButton;

    @FindBy(id="verwijderen")
    private WebElement confirmDeleteButton;

    public OverviewWorkOrder(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "OverviewWorkOrder");
    }

    public boolean containsWorkOrderWithDateAndDescription(String startTime, String description) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(startTime));
        boolean found=false;
        for (WebElement listItem:listItems) {
            if(listItem.findElement(By.className("descriptionClass")).getText().contains(description)) {
                found=true;
            }
        }
        return found;
    }

    public boolean notContainsWorkOrderWithDateAndDescription(String startTime, String description) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(startTime));
        boolean notFound=true;
        for (WebElement listItem:listItems) {
            if (listItem.findElement(By.className("descriptionClass")).getText().contains(description)) {
                notFound=false;
            }
        }
        return notFound;
    }

    public boolean isSortedOnDate() throws ParseException {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className("dateClass"));
        boolean sorted=true;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0; i<listItems.size()-1; i++) {
            Date date1 = formatter.parse(listItems.get(i).getText());
            Date date2 = formatter.parse(listItems.get(i+1).getText());
            if (date2.before(date1)) {
                sorted=false;
            }
        }
        return sorted;
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewWorkOrderBox ul li p"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessage2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewWorkOrderBox ul li:nth-child(2) p"));
        return (message.equals(errorMsg.getText()));
    }

    public void delete() {
        deleteButton.click();
    }

    public void edit() {
        editButton.click();
    }

    public void sortOnId(){
        driver.findElement(By.id("sort")).sendKeys("workorderid");
    }

    public void sortOnDate(){
        driver.findElement(By.id("sort")).sendKeys("date");
    }

    public boolean deleteAllIfExists(){
        return driver.findElements(By.cssSelector("table:last-child .verwijder a")).size() != 0;
    }

    public void confirmDelete() {
        confirmDeleteButton.click();
    }

    public void deleteIfExists(String startTime, String description){
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(startTime));
        for (WebElement listItem:listItems) {
            if(listItem.findElement(By.className("descriptionClass")).getText().contains(description)) {
                listItem.findElement(By.cssSelector(".verwijder a")).click();
                confirmDelete();
            }
        }
    }
}

