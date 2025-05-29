package tqs.WashNow.functional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testLoginAndReservation() throws InterruptedException {
        driver.get("http://localhost:5173");

        WebElement createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();

        Thread.sleep(500);

        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("c");

        WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys("c@ua.pt");

        WebElement accountTypeSelect = driver.findElement(By.name("type"));
        Select select = new Select(accountTypeSelect);
        select.selectByVisibleText("Cliente");

        WebElement createAccountButton = driver.findElement(By.name("criarBtn"));
        createAccountButton.click();

        Thread.sleep(1000);

        assertEquals("http://localhost:5173", driver.getCurrentUrl());

        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys("c@ua.pt");

        WebElement entrarButton = driver.findElement(By.xpath("//button[text()='Entrar']"));
        entrarButton.click();

        WebElement reservarButton = driver.findElement(By.xpath("//button[text()='Reservar']"));
        reservarButton.click();

        WebElement dateInput = driver.findElement(By.name("date"));
        dateInput.sendKeys("04-01-2025");

        WebElement startTimeSelect = driver.findElement(By.name("startTime"));
        startTimeSelect.sendKeys("10:00");

        WebElement endTimeSelect = driver.findElement(By.name("endTime"));
        endTimeSelect.sendKeys("10:20");

        WebElement submitReservationButton = driver.findElement(By.xpath("//button[text()='Continuar para o Pagamento']"));
        submitReservationButton.click();

        WebElement paymentButton = driver.findElement(By.xpath("//button[text()='MB Way']"));
        paymentButton.click();

        WebElement confirmButton = driver.findElement(By.xpath("//button[text()='Confirmar Pagamento']"));
        confirmButton.click();
        





        WebElement startDateInput = driver.findElement(By.id("startDate"));
        startDateInput.sendKeys("04-01-2025");

        WebElement endDateInput = driver.findElement(By.id("endDate"));
        endDateInput.sendKeys("04-05-2025");

        WebElement verRefeicoesButton = driver.findElement(By.xpath("//button[text()='Ver refeições']"));
        verRefeicoesButton.click();

        Thread.sleep(1000);

        assertTrue(driver.getCurrentUrl().contains("/meals?restaurantId=1"));
        WebElement table = driver.findElement(By.tagName("table"));
        assertNotNull(table);
    }
}