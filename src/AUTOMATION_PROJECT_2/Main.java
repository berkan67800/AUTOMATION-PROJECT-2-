package AUTOMATION_PROJECT_2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static String generateRandomString(int length) {
        String str = "";
        for (int i = 0; i < length; i++) {
            str = str + (int) (Math.random() * 10);
        }
        return str;
    }

    public static String generateCardNumber(int type){
        if(type == 0){
            return "4"+ generateRandomString(15);
        }
        else if(type == 1){
            return "5"+ generateRandomString(14);
        }
        else {
            return "3"+ generateRandomString(14);
        }

    }
    public static String generateExpirationDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/YY");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixMonthsLater = now.plusMonths( (int)(Math.random()*72) ) ;
        return dtf.format(sixMonthsLater);
    }
    public static void main(String[] args) throws InterruptedException {


        System.setProperty("webdriver.chrome.driver","/Users/asdasd/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester", Keys.TAB,"test",Keys.ENTER);
        driver.findElement(By.xpath("//a[contains(text(),'Order')]")).click();
        String randomQt = String.valueOf((int) (1+Math.random()*99));
        Thread.sleep(1000);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(Keys.BACK_SPACE,randomQt);
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();
        Thread.sleep(1000);

        Integer total = Integer.valueOf(driver.findElement(By.xpath("//input[@id='ctl00_MainContent_fmwOrder_txtTotal']")).getAttribute("value"));

        if(Integer.valueOf(randomQt)<10){
            Assert.assertEquals(total, Integer.valueOf(randomQt) * 100 );

        }else{
            Assert.assertEquals(total, (int)((Integer.valueOf(randomQt) * 100) * 0.92) );
        }

        List<String[]> info = Utility.readFromCSV();

        String [] data= info.get((int)(1+Math.random()*1000));
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(
                data[0],Keys.TAB,
                data[1],Keys.TAB,
                data[2],Keys.TAB,
                data[3],Keys.TAB,
                data[4],Keys.TAB
        );
        int cardType = (int)(Math.random()*3);
        String cardNumber = generateCardNumber(cardType);
        String expirationDate = generateExpirationDate();

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_"+cardType)).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(cardNumber,Keys.TAB,expirationDate);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
        driver.findElement(By.xpath("//a[text()='View all orders']")).click();

        List<String> webSiteEntries= new ArrayList<>();
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[2]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[6]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[7]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[8]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[9]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[10]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[11]")).getText());
        webSiteEntries.add(driver.findElement(By.xpath("//tbody//tbody//td[12]")).getText());



         Assert.assertEquals(webSiteEntries.get(0), data[0]); //full name
         Assert.assertEquals(webSiteEntries.get(1), data[1]); //street
         Assert.assertEquals(webSiteEntries.get(2), data[2]); //city
         Assert.assertEquals(webSiteEntries.get(3), data[3]); //state
         Assert.assertEquals(webSiteEntries.get(4), data[4]); //zip
         Assert.assertEquals(webSiteEntries.get(5), (cardType==0 ?"Visa": cardType==1 ?"MasterCard":"American Express")); //card type
         Assert.assertEquals(webSiteEntries.get(6), cardNumber);//card number
         Assert.assertEquals(webSiteEntries.get(7), expirationDate);//expiration date

         driver.findElement(By.id("ctl00_logout")).click();

        System.out.println("ALL STEPS ARE PASSED!!!");

        driver.quit();

    }
}
