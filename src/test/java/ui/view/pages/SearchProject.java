package ui.view.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.view.Config;
import ui.view.Page;

public class SearchProject extends Page {

    @FindBy(id="name")
    private WebElement nameField;

    @FindBy(id="search")
    private WebElement searchButton;

    public SearchProject(WebDriver driver) {
        super(driver);
        this.driver.get(Config.BASE_URL + "SearchProjectPage");
    }

    public void fillInName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void search() {
        searchButton.click();
    }
}
