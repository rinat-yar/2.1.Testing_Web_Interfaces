package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderingCardTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
       // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    void correctTest() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ринат Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualText = driver.findElement((By.cssSelector("[data-test-id=order-success]"))).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }

    @Test
    void notCorrectTest() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ри5ат Ярмухамедов");
                driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notCorrectTestLatin() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Rinat Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notCorrectTestPhone() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ринат Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+7901222334455");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void notCorrectTest_SpecialСharacter() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ри@нат Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void notNameTest() {
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void notPhoneTest() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ринат Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=agreement]"))).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement errorNotDisplayed = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        assertTrue(errorNotDisplayed.isDisplayed(), "Ошибка не отображается");
        String text = errorNotDisplayed.getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void аgreementTest() {
        driver.findElement((By.cssSelector("[data-test-id=name] input"))).sendKeys("Ринат Ярмухамедов");
        driver.findElement((By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79012223344");

        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());

    }
}