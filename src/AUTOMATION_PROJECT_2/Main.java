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
import java.util.Arrays;
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

    public static String getDate(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return myDateObj.format(myFormatObj);
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
                data[4],Keys.TAB);

        int cardType = (int)(Math.random()*3);
        String cardNumber = generateCardNumber(cardType);
        String expirationDate = generateExpirationDate();

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_"+cardType)).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(cardNumber,Keys.TAB,expirationDate);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
        driver.findElement(By.xpath("//a[text()='View all orders']")).click();

        List<String> expectedResult = new ArrayList<>(Arrays.asList(
                data[0],
                "MyMoney",
                randomQt,
                getDate(),
                data[1],
                data[2],
                data[3],
                data[4],
                (cardType==0 ?"Visa": cardType==1 ?"MasterCard":"American Express"),//card type
                cardNumber,
                expirationDate));


         List<String> actualResult = new ArrayList<>();
         List<WebElement> row = driver.findElements(By.xpath("//tbody//tr//tr[2]//td"));

        for (WebElement column:row) {
            actualResult.add(column.getText());
        }

        //deleting unnecessary parts from that row to obtain the same size with the list of expectedResult.
        actualResult.remove(0);
        actualResult.remove(actualResult.size()-1);

        Assert.assertEquals(actualResult,expectedResult);



        driver.findElement(By.id("ctl00_logout")).click();

        System.out.println("ALL STEPS ARE PASSED!!!");

        driver.quit();

    }
}
