package objectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public abstract class ILabCareers {

    @FindBy(xpath = "//a[text()='CAREERS']")

    public WebElement careersButton;

    @FindBy(xpath = "//div/h3[text()='CURRENT OPENINGS']/div/div/div/div/div/div/div/a[1]")

    public WebElement firstpost;

    @FindBys(@FindBy(xpath = "//h3[text()='CURRENT OPENINGS']/following-sibling::div/div/div/div/div/div/div/a"))

    public List<WebElement> currentOpenings;

    @FindBy(xpath = "//a[contains(text(),'Apply Online')]")

    public WebElement applyonline;

    @FindBy(name = "applicant_name")

    public WebElement applicantName;

    @FindBy(name = "email")

    public WebElement email;

    @FindBy(name = "phone")

    public WebElement phone;

    @FindBy(name = "message")

    public WebElement message;

    @FindBy(xpath = "//input[@value='Send Application']")

    public WebElement sendApplicationButton;

    @FindBys(@FindBy(xpath = "//ul[contains(@class,'errors')]/li"))

    public List<WebElement> errorMessage;
}
