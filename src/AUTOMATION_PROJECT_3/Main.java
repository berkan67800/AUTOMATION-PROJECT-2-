package AUTOMATION_PROJECT_3;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner wait = new Scanner(System.in);

        /*
        * PART -1- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        * */

        System.setProperty("webdriver.chrome.driver","/Users/berkansaridilek/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.carfax.com/");
        driver.findElement(By.linkText("Find a Used Car")).click();
        Assert.assertTrue(driver.getTitle().contains("Used Cars"));

        /*

        -Recaptcha sometimes causes long wait, and it's hard to control checking the code result everytime I add new feature.
        -This part is to allow user to continue to the next step at any time.

        STEPS;
        1-Execute the code.
        2-Recaptcha part will be shown up. Pass this part.(After you pass, IntelliJ will be waiting for you to enter any key.)
        3-Come back to IntelliJ and enter any key to continue.
        4-Come back to Chrome page to see what happens.


         */
        System.out.print("Enter any key to continue :");
        wait.next();

        new Select(driver.findElement(By.xpath("//select[contains(@id,'cfx-select')][@aria-label='Select Make']"))).selectByValue("Tesla");

        List<String> expectedResultModels = new ArrayList<>(Arrays.asList("Model 3", "Model S", "Model X", "Model Y"));
        List<String> actualResultModels = new ArrayList<>();

        List<WebElement>models = driver.findElements(By.xpath("//option[contains(@id,'model_Model')]"));
        for (WebElement model : models) {
            actualResultModels.add(model.getAttribute("value"));
        }
        Assert.assertEquals(actualResultModels,expectedResultModels);

        new Select(driver.findElement(By.xpath("//select[contains(@id,'cfx-select')][@aria-label='Select Model']"))).selectByValue("Model S");

        driver.findElement(By.xpath("//input[@aria-label='ZIP Code'][1]")).sendKeys("22182");
        driver.findElement(By.xpath("//div[@id='makeModelPanel']//button")).click();

        /*
         * PART -2- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
         * */

        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("Step 2 - Show me cars with"));
        List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@class='checkbox-input_box']"));
        for (WebElement checkbox : checkboxes) {
            checkbox.click();
        }
        Thread.sleep(1000);

        String showMeResult = driver.findElement(By.xpath("//button[@class='button ripple-container']")).getAccessibleName();
        Integer filterResults = Integer.valueOf(showMeResult.replaceAll("[^0-9]", ""));
        driver.findElement(By.xpath("//button[@class='button ripple-container']")).click();
        Thread.sleep(1000);

        /*
         * PART -3- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - --
         * */

        Assert.assertEquals(driver.findElements(By.xpath("//article[@class='srp-list-item show-cta']")).size(),filterResults);

        List<WebElement> headers = driver.findElements(By.xpath("//h4[@class='srp-list-item-basic-info-model']"));
        for (WebElement header : headers) {
            Assert.assertTrue(header.getText().contains("Tesla Model S"));
        }

        new Select(driver.findElement(By.xpath("//select[@id='srp-header-sort-select']"))).selectByValue("PRICE_DESC");
        Thread.sleep(1000);

        List<WebElement> pricesWebElement = driver.findElements(By.xpath("//article[@class='srp-list-item show-cta' and preceding-sibling::button[@class='screenreader-link sr-only jump-to-filters'] and following-sibling::div]//span[@class='srp-list-item-price']"));
        List<Integer> actualPriceList = new ArrayList<>();
        for (WebElement price : pricesWebElement) {
            if(!price.getText().contains("Call for price")){
                actualPriceList.add(Integer.valueOf(price.getText().replaceAll("[^0-9]","")));
            }
        }
        List<Integer> expectedPriceList = new ArrayList<>(actualPriceList);
        Collections.sort(expectedPriceList);
        Collections.reverse(expectedPriceList);

         Assert.assertEquals(actualPriceList,expectedPriceList);

        new Select(driver.findElement(By.xpath("//select[@id='srp-header-sort-select']"))).selectByValue("MILEAGE_ASC");
        Thread.sleep(1000);

        List<WebElement> milesWebElement = driver.findElements(By.xpath("//article[@class='srp-list-item show-cta' and preceding-sibling::button[@class='screenreader-link sr-only jump-to-filters'] and following-sibling::div]//span[@class='srp-list-item-basic-info-value'][contains(text(),'miles')]"));
        List<Integer> actualMileageList = new ArrayList<>();
        for (WebElement mileage : milesWebElement) {
            actualMileageList.add(Integer.valueOf(mileage.getText().replaceAll("[^0-9]","")));
        }
        List<Integer> expectedMileageList = new ArrayList<>(actualMileageList);
        Collections.sort(expectedMileageList);

        Assert.assertEquals(actualMileageList,expectedMileageList);

        new Select(driver.findElement(By.xpath("//select[@id='srp-header-sort-select']"))).selectByValue("YEAR_DESC");
        Thread.sleep(1000);
        List<WebElement> newToOldWebElement = driver.findElements(By.xpath("//article[@class='srp-list-item show-cta' and preceding-sibling::button[@class='screenreader-link sr-only jump-to-filters'] and following-sibling::div]//h4"));
        List<String> actualYearList = new ArrayList<>();
        for (WebElement year : newToOldWebElement) {
            actualYearList.add(year.getText().replaceAll("[^0-9]","").substring(0,4));
        }

        List<String> expectedYearList = new ArrayList<>(actualYearList);
        Collections.sort(expectedYearList);
        Collections.reverse(expectedYearList);

        Assert.assertEquals(actualYearList,expectedYearList);
        System.out.println("RESULT : PASSED");
        driver.quit();


    }
}
