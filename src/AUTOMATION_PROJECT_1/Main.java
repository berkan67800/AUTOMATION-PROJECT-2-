package AUTOMATION_PROJECT_1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","/Users/asdasd/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://qa-duotify.us-east-2.elasticbeanstalk.com/register.php");
        String title = driver.getTitle();

        Thread.sleep(1000);
        driver.findElement(By.id("hideLogin")).click();

        String  username = "johnSmith"+((int)(Math.random()*1000)),
                firstName = "John",
                lastName = "Smith",
                email = "robeh"+((int)(Math.random()*1000))+"@bongcs.com",
                password = "vTC7YTjBa96KknY";

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("email2")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password2")).sendKeys(password);
        driver.findElement(By.name("registerButton")).click();
        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        String firstAndLastName = driver.findElement(By.id("nameFirstAndLast")).getText();
        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("rafael")).click();
        Thread.sleep(1000);
        String urlAfterLogOut = driver.getCurrentUrl();

        driver.findElement(By.id("loginUsername")).sendKeys(username);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.name("loginButton")).click();
        Thread.sleep(1000);

        boolean loginSuccess = driver.getPageSource().contains("You Might Also Like");


        System.out.println("TITLE : " + (title.equals("Welcome to Duotify!")?" PASSED": "FAILED"));
        System.out.println("URL : " + (url.equals("http://qa-duotify.us-east-2.elasticbeanstalk.com/browse.php?")?"PASSED": "FAILED"));
        System.out.println("FIRST & LAST NAME : "+(firstAndLastName.equals(firstName+" "+lastName)?"PASSED": "FAILED"));
        System.out.println("URL AFTER LOGGING OUT : "+(urlAfterLogOut.equals("http://qa-duotify.us-east-2.elasticbeanstalk.com/register.php")?"PASSED": "FAILED"));
        System.out.println("SUCCESSFUL LOGIN : " + (loginSuccess?"PASSED":"FAILED"));

        driver.close();
    }

}
