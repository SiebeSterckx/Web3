package ui.view.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

public class SearchWorkOrder extends Page {

    @FindBy(id="date")
    private WebElement dateField;

    @FindBy(id="search")
    private WebElement searchButton;

    public SearchWorkOrder(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "SearchWorkOrderPage");
    }

    public void fillInDate(String date) {
        dateField.clear();
        dateField.sendKeys(date);
    }

    public void search() {
        searchButton.click();
    }
}
