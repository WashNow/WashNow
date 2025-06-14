package tqs.WashNow.functional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationUITestRemote {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    @Test @Order(1)
    void testCreateOwners() throws InterruptedException {
        driver.get("http://deti-tqs-11.ua.pt/");

        // Esperar a página carregar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Criar conta")));

        // Criar conta de dono 1 de estacao de lavagem
        WebElement createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();

        // Esperar o formulário ficar visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));

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
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/"));
        
        // Criar conta de dono 2 de estacao de lavagem
        createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();

        // Esperar o formulário ficar visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        
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
    }

    @Test @Order(2)
    void testCreateClient() throws InterruptedException {
        // Verificar redirecionamento
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/"));
        
        // Criar conta do cliente
        WebElement createButton = driver.findElement(By.linkText("Criar conta"));
        createButton.click();

        // Esperar o formulário ficar visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys("c");
        WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.sendKeys("c@ua.pt");
        WebElement accountTypeSelect = driver.findElement(By.name("type"));
        Select select = new Select(accountTypeSelect);
        select.selectByVisibleText("Cliente");
        WebElement createAccountButton = driver.findElement(By.name("criarBtn"));
        createAccountButton.click();
    }

    @Test @Order(3)
    void testLoginClient() throws InterruptedException {
        // Verificar redirecionamento
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/"));

        // Fazer login na conta do cliente
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys("c@ua.pt");
        WebElement entrarButton = driver.findElement(By.xpath("//button[text()='Entrar']"));
        entrarButton.click();
    }

    @Test @Order(4)
    void testReserveWash() throws InterruptedException {
        // Verificar redirecionamento
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/Mapa"));

        // Eseperar as reservas carregarem
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Reservar']")));

        // Reservar uma lavagem
        WebElement reservarButton = driver.findElement(By.xpath("//button[text()='Reservar']"));
        reservarButton.click();
        WebElement dateInput = driver.findElement(By.name("date"));
        dateInput.sendKeys("04-01-2026");
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
    }

    @Test @Order(5)
    void testOpenClientProfile() throws InterruptedException {
        // Verificar redirecionamento
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/Mapa"));

        // Aceder o perfil do cliente
        WebElement profileLink = driver.findElement(By.xpath("//span[text()='Perfil']"));
        profileLink.click();
    }

    @Test @Order(6)
    void testVerifyReservation() throws InterruptedException {
        // Verificar redirecionamento
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-11.ua.pt/Perfil"));

        // Verificar se a reserva foi criada
        List<WebElement> reservas = new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver1 -> {
            List<WebElement> elems =
                driver1.findElements(By.cssSelector("li[data-testid='reserva']"));
            return elems.size() > 0 ? elems : null;
        });
        String textoReserva = reservas.get(0).getText();
        String dataReserva = reservas.get(0).findElement(By.tagName("p")).getText();
        assertTrue(textoReserva.contains("e1"), "Reserva com estação não encontrada, real: " + textoReserva);
        assertTrue(dataReserva.contains("4/1/2026"), "Reserva com data não encontrada, real: " + dataReserva);
    }
}