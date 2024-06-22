package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

import java.util.ArrayList;

public class OverviewUser extends Page {

    @FindBy(css = "tbody tr:last-child .verwijder a")
    private WebElement deleteButton;

    @FindBy(css = "tbody tr:last-child .edit a")
    private WebElement editButton;

    @FindBy(id="verwijderen")
    private WebElement confirmDeleteButton;

    public OverviewUser(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "OverviewUser");
    }

    public boolean containsUserWithEmail(String email) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(email));
        boolean found=false;
        for (WebElement listItem:listItems) {
            if (listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(email)) {
                found=true;
            }
        }
        return found;
    }

    public boolean notContainsUserWithEmail(String email) {
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(email));
        boolean notFound=true;
        for (WebElement listItem:listItems) {
            if (listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(email)) {
                notFound=false;
            }
        }
        return notFound;
    }

    public void delete() {
        deleteButton.click();
    }

    public void edit() {
        editButton.click();
    }

    public void confirmDelete() {
        confirmDeleteButton.click();
    }

    public void deleteIfExists(String email){
        ArrayList<WebElement> listItems=(ArrayList<WebElement>) this.driver.findElements(By.className(email));
        for (WebElement listItem:listItems) {
            if(listItem.findElement(By.cssSelector("td:nth-child(4)")).getText().contains(email)) {
                listItem.findElement(By.cssSelector(".verwijder a")).click();
                confirmDelete();
            }
        }
    }
}
