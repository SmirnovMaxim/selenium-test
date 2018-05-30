package testng;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class WebTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://igorakintev.ru/admin/");
    }

    @Test
    public void login() {
        WebElement fieldUsername = driver.findElement(By.id("id_username"));
        WebElement fieldPassword = driver.findElement(By.id("id_password"));
        WebElement buttonLogin = driver.findElement(By.xpath("//input[@type='submit']"));

        fieldUsername.sendKeys("silenium");
        fieldPassword.sendKeys("super_password");
        buttonLogin.click();

        WebElement title = driver.findElement(By.cssSelector(".dashboard-title"));

        Assert.assertEquals("Панель управления", title.getText());

        WebElement addLink = driver
                .findElement(By.id("module_2"))
                .findElement(By.cssSelector(".dashboard-module-content"))
                .findElement(By.cssSelector(".addlink"));
        addLink.click();

        title = driver.findElement(By.id("content")).findElement(By.tagName("h1"));
        Assert.assertEquals("Добавить entry", title.getText());

        WebElement fieldTitle = driver.findElement(By.id("id_title"));
        WebElement fieldSlug = driver.findElement(By.id("id_slug"));
        WebElement fieldMarkdown = driver.findElement(By.id("id_text_markdown"));
        WebElement fieldText = driver.findElement(By.id("id_text"));
        WebElement buttonSave = driver.findElement(By.cssSelector(".default"));

        fieldTitle.sendKeys("Title");
        fieldSlug.sendKeys("-slug");
        fieldMarkdown.sendKeys("Markdown markdown");
        fieldText.sendKeys("Text text");
        buttonSave.click();

        driver.get("http://igorakintev.ru/blog/");
        title = driver
                .findElement(By.id("entries"))
                .findElement(By.tagName("h2"))
                .findElement(By.tagName("a"));
        Assert.assertEquals("Title", title.getText());

        WebElement description = driver
                .findElement(By.id("entries"))
                .findElement(By.cssSelector(".entry"))
                .findElement(By.tagName("p"));
        Assert.assertEquals("Markdown markdown", description.getText());

        driver.get("http://igorakintev.ru/admin/blog/entry/");
        WebElement row = driver.findElement(By.xpath("//td[text()='title-slug']"));
        WebElement checkbox = row
                .findElement(By.xpath(".."))
                .findElement(By.tagName("td"))
                .findElement(By.tagName("input"));
        checkbox.click();

        Select select = new Select(driver.findElement(By.name("action")));
        select.selectByValue("delete_selected");

        WebElement buttonRun = driver.findElement(By.name("index"));
        buttonRun.click();

        WebElement form = driver.findElement(By.tagName("form"));
        WebElement buttonConfirm = form.findElement(By.xpath("//input[@type='submit']"));
        buttonConfirm.click();
    }
}
