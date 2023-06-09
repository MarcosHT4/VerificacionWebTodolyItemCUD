package cudItemTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CUDItemTest {

    ChromeDriver chrome;

    @BeforeEach
    public void openBroweser() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        chrome = new ChromeDriver();
        chrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        chrome.manage().window().maximize();
        chrome.get("https://todo.ly");

    }

    @AfterEach
    public void closeBrowser() {
        chrome.quit();
    }

    @Test
    public void verifyCUDItem() throws InterruptedException {

        String nameProject = "Marcos CRUD" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String nameItem = "Marcos Item" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String updatedNameItem = "Updated Marcos Item" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        chrome.findElement(By.xpath("//img[@src=\"/Images/design/pagelogin.png\"]")).click();

        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxEmail")).sendKeys("marcos@vpsi.com");

        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_TextBoxPassword")).sendKeys("12345");

        chrome.findElement(By.id("ctl00_MainContent_LoginControl1_ButtonLogin")).click();

        chrome.findElement(By.xpath("//td[text()='Add New Project']")).click();

        chrome.findElement(By.id("NewProjNameInput")).sendKeys(nameProject);

        chrome.findElement(By.id("NewProjNameButton")).click();
        Thread.sleep(3000);

        //POST ITEM

        chrome.findElement(By.id("NewItemContentInput")).sendKeys(nameItem);
        chrome.findElement(By.id("NewItemAddButton")).click();
        Thread.sleep(3000);

        Assertions.assertTrue(chrome.findElements(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+nameItem+"']")).size() == 1, "ERROR! El item no fue creado!");

        //UPDATE ITEM

        Actions actions = new Actions(chrome);
        actions.moveToElement(chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+nameItem+"']"))).perform();
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+nameItem+"']/../following-sibling::td//img[@src='/Images/dropdown.png']")).click();
        chrome.findElement(By.xpath("//ul[@id='itemContextMenu']//a[@href='#edit']")).click();
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv UnderEditingItem']//textarea[@id='ItemEditTextbox']")).clear();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv UnderEditingItem']//textarea[@id='ItemEditTextbox']")).sendKeys(updatedNameItem);
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv UnderEditingItem']//textarea[@id='ItemEditTextbox']")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+updatedNameItem+"']/../preceding-sibling::td//input")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[@id='mainDoneItemList']//li[last()]//div[@class='ItemContentDiv DoneItem' and text()='"+updatedNameItem+"']/../preceding-sibling::td//input")).click();
        Thread.sleep(1000);

        Assertions.assertTrue(chrome.findElements(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+updatedNameItem+"']")).size() == 1, "ERROR! El item no fue actualizado!");

        //DELETE ITEM

        actions.moveToElement(chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+updatedNameItem+"']"))).perform();
        chrome.findElement(By.xpath("//ul[@id='mainItemList']//li[last()]//div[@class='ItemContentDiv' and text()='"+updatedNameItem+"']/../following-sibling::td//img[@src='/Images/dropdown.png']")).click();
        Thread.sleep(1000);
        chrome.findElement(By.xpath("//ul[@id='itemContextMenu']//a[@href='#delete']")).click();
        Thread.sleep(1000);
        Assertions.assertTrue(chrome.findElements(By.xpath("//div[@id='HeaderMessageInfo']//span[@id='InfoMessageText' and contains(text(), 'Deleted')]")).size() == 1, "ERROR! El item no fue eliminado!");






    }

}
