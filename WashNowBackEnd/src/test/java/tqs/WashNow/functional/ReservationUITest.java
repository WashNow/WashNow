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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
        driver.get("http://localhost:5173/");

        // Criar conta de dono 1 de estacao de lavagem
        WebElement createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();
        Thread.sleep(500);
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("dono1");
        WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys("dono1@gmail.com");
        WebElement accountTypeSelect = driver.findElement(By.name("type"));
        Select select = new Select(accountTypeSelect);
        select.selectByVisibleText("Dono de Estação");
        WebElement nameEstInput = driver.findElement(By.xpath("//input[@placeholder='Nome da Estação']"));
        nameEstInput.sendKeys("e1");
        WebElement addressInput = driver.findElement(By.name("address"));
        addressInput.sendKeys("e1 address");
        WebElement latitudeInput = driver.findElement(By.name("latitude"));
        latitudeInput.sendKeys("40.640063");
        WebElement longitudeInput = driver.findElement(By.name("longitude"));
        longitudeInput.sendKeys("-8.653754");
        WebElement createAccountButton = driver.findElement(By.name("criarBtn"));
        createAccountButton.click();

        // Verificar redirecionamento
        Thread.sleep(500);
        assertEquals("http://localhost:5173/", driver.getCurrentUrl());
        
        // Criar conta de dono 2 de estacao de lavagem
        createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();
        Thread.sleep(500);
        nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("dono2");
        emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys("dono2@gmail.com");
        accountTypeSelect = driver.findElement(By.name("type"));
        select = new Select(accountTypeSelect);
        select.selectByVisibleText("Dono de Estação");
        nameEstInput = driver.findElement(By.xpath("//input[@placeholder='Nome da Estação']"));
        nameEstInput.sendKeys("e2");
        addressInput = driver.findElement(By.name("address"));
        addressInput.sendKeys("e2 address");
        latitudeInput = driver.findElement(By.name("latitude"));
        latitudeInput.sendKeys("40.740063");
        longitudeInput = driver.findElement(By.name("longitude"));
        longitudeInput.sendKeys("-8.553754");
        createAccountButton = driver.findElement(By.name("criarBtn"));
        createAccountButton.click();

        // Verificar redirecionamento
        Thread.sleep(500);
        assertEquals("http://localhost:5173/", driver.getCurrentUrl());
        
        // Criar conta do cliente
        createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();
        Thread.sleep(500);
        nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("c");
        emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys("c@ua.pt");
        accountTypeSelect = driver.findElement(By.name("type"));
        select = new Select(accountTypeSelect);
        select.selectByVisibleText("Cliente");
        createAccountButton = driver.findElement(By.name("criarBtn"));
        createAccountButton.click();

        // Verificar redirecionamento
        Thread.sleep(500);
        assertEquals("http://localhost:5173/", driver.getCurrentUrl());

        // Fazer login na conta do cliente
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys("c@ua.pt");
        WebElement entrarButton = driver.findElement(By.xpath("//button[text()='Entrar']"));
        entrarButton.click();

        // Verificar redirecionamento
        Thread.sleep(500);
        assertEquals("http://localhost:5173/Mapa", driver.getCurrentUrl());

        // Reservar uma lavagem
        WebElement reservarButton = driver.findElement(By.xpath("//button[text()='Reservar']"));
        reservarButton.click();
        WebElement dateInput = driver.findElement(By.name("date"));
        dateInput.sendKeys("04-01-2025");
        WebElement startTimeSelect = driver.findElement(By.name("startTime"));
        startTimeSelect.sendKeys("10:00");
        WebElement endTimeSelect = driver.findElement(By.name("endTime"));
        endTimeSelect.sendKeys("10:20");
        WebElement submitReservationButton = driver.findElement(By.xpath("//button[text()='Continuar para Pagamento']"));
        submitReservationButton.click();
        WebElement mbWayInput = driver.findElement(By.id("mbway"));
        mbWayInput.click();
        WebElement confirmButton = driver.findElement(By.xpath("//button[text()='Confirmar Pagamento']"));
        confirmButton.click();
        
        // Verificar redirecionamento
        Thread.sleep(2000);
        assertEquals("http://localhost:5173/Mapa", driver.getCurrentUrl());

        // Aceder o perfil do cliente
        WebElement profileLink = driver.findElement(By.xpath("//span[text()='Perfil']"));
        profileLink.click();

        // Verificar redirecionamento
        Thread.sleep(500);
        assertEquals("http://localhost:5173/Perfil", driver.getCurrentUrl());

        // Verificar se a reserva foi criada
        List<WebElement> reservas = new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver1 -> {
            List<WebElement> elems =
                driver1.findElements(By.cssSelector("li[data-testid='reserva']"));
            return elems.size() > 0 ? elems : null;
        });
        String textoReserva = reservas.get(0).getText();
        String dataReserva = reservas.get(0).findElement(By.tagName("p")).getText();
        assertTrue(textoReserva.contains("e1"), "Reserva com estação não encontrada, real: " + textoReserva);
        assertTrue(dataReserva.contains("4/1/2025"), "Reserva com data não encontrada, real: " + dataReserva);
    }
}