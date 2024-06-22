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

public class OverviewProject extends Page {

    @FindBy(css = "tbody tr:last-child .verwijder a")
    private WebElement deleteButton;

    @FindBy(css = "tbody tr:last-child .edit a")
    private WebElement editButton;

    @FindBy(id="verwijderen")
    private WebElement confirmDeleteButton;

    public OverviewProject(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "OverviewProject");
    }

    public boolean containsProjectWithNameAndStartDate(String name, String startdate) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(name));
        boolean found=false;
        for (WebElement listItem:listItems) {
            if(listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(startdate)) {
                found=true;
            }
        }
        return found;
    }

    public boolean notContainsProjectWithNameAndStartDate(String name, String startdate) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(name));
        boolean notFound=true;
        for (WebElement listItem:listItems) {
            if (listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(startdate)) {
                notFound=false;
            }
        }
        return notFound;
    }

    public boolean isSortedOnStartDate() throws ParseException {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className("startDateClass"));
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
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewContainer ul li p"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessage2 (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("#OverviewContainer ul li:nth-child(2) p"));
        return (message.equals(errorMsg.getText()));
    }

    public void delete() {
        deleteButton.click();
    }

    public void edit() {
        editButton.click();
    }

    public void sortOnId(){
        driver.findElement(By.id("sort")).sendKeys("projectid");
    }

    public void sortOnStartDate(){
        driver.findElement(By.id("sort")).sendKeys("startdate");
    }

    public boolean deleteAllIfExists(){
        return driver.findElements(By.cssSelector("tbody:last-child .verwijder a")).size() != 0;
    }

    public void confirmDelete() {
        confirmDeleteButton.click();
    }

    public void deleteIfExists(String name, String startdate){
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(name));
        for (WebElement listItem:listItems) {
            if(listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(startdate)) {
                listItem.findElement(By.cssSelector(".verwijder a")).click();
                confirmDelete();
            }
        }
    }
}
