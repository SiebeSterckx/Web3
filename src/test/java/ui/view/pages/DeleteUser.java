package ui.view.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Page;

public class DeleteUser extends Page {

    @FindBy(id="verwijderen")
    private WebElement confirmDeleteButton;

    @FindBy(id="nietVerwijderen")
    private WebElement declineDeleteButton;

    public DeleteUser(WebDriver driver) {
        super(driver);
    }

    public void confirmDelete() {
        confirmDeleteButton.click();
    }

    public void declineDelete() {
        declineDeleteButton.click();
    }

    public boolean hasErrorMessage (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        return (message.equals(errorMsg.getText()));
    }

    public boolean hasErrorMessageInP (String message) {
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger p"));
        return (message.equals(errorMsg.getText()));
    }
}
