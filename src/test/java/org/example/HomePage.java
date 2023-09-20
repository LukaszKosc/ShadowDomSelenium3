package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.Map;

import static org.example.TestBase.getDriver;

public class HomePage {

    public WebElement expandRootElement(WebElement shadowHost) {
        WebElement returnObj = null;
        Object shadowRoot = ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].shadowRoot", shadowHost);
        if (shadowRoot instanceof WebElement) {
            // ChromeDriver 95
            returnObj = (WebElement) shadowRoot;
        } else if (shadowRoot instanceof Map) {
            // ChromeDriver 96+
            Map<String, Object> shadowRootMap = (Map<String, Object>) shadowRoot;
            String shadowRootKey = (String) shadowRootMap.keySet().toArray()[0];
            String id = (String) shadowRootMap.get(shadowRootKey);
            RemoteWebElement remoteWebElement = new RemoteWebElement();
            remoteWebElement.setParent((RemoteWebDriver) getDriver());
            remoteWebElement.setId(id);
            returnObj = remoteWebElement;
        } else {
            System.out.println("Unexpected return type for shadowRoot in getShadowRootElement()");
        }
        return returnObj;
    }

    public String getShadowDomText() {
        WebElement shadowHost = getDriver().findElement(By.id("shadow_host"));
        WebElement shadowRootOne = expandRootElement(shadowHost);
        return shadowRootOne.findElement(By.cssSelector("#shadow_content > span")).getText();
    }

    public String getNestedShadowText() {
        WebElement shadowHost = getDriver().findElement(By.id("shadow_host"));
        WebElement shadowRootOne = expandRootElement(shadowHost);
        WebElement shadowContent = shadowRootOne.findElement(By.cssSelector("#nested_shadow_host"));
        SearchContext shadowRootTwo = expandRootElement(shadowContent);
        return shadowRootTwo.findElement(By.cssSelector("#nested_shadow_content > div")).getText();
    }

    public String getNestedTextViaJSExecutor() {
        WebElement shadowHost = getDriver().findElement(By.id("shadow_host"));
        WebElement shadowRootOne = expandRootElement(shadowHost);
        WebElement nestedShadowHost = shadowRootOne.findElement(By.cssSelector("#nested_shadow_host"));
        WebElement shadowRootTwo = expandRootElement(nestedShadowHost);
        return shadowRootTwo.findElement(By.cssSelector("#nested_shadow_content > div"))
                .getText();
    }
}