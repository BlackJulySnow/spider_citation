package cn.edu.xtu.service.impl;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.service.Spider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

public class WOSImpl extends Spider {
    public WOSImpl(EdgeDriver driver) {
        super(driver);
    }

    @Override
    public List<Article> search(String paperTitle) throws InterruptedException {
        driver.get("https://webofscience.clarivate.cn/wos/alldb/summary/a34d5d50-a912-4d5d-9606-794e159fde81-014cb5cebb/relevance/1");
        authenticate();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/app-wos/main/div/div/div[2]/div/div/div[2]/app-input-route/app-base-summary-component/app-search-friendly-display/div[1]/app-general-search-friendly-display/app-query-modifier/div[1]/div[1]/div/button[1]")).click();
        WebElement element = driver.findElement(By.xpath("//*[@id=\"search-option\"]"));
        element.clear();
        element.sendKeys(paperTitle);
        driver.findElement(By.xpath("//*[@id=\"snSearchType\"]/div[4]/button[2]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/app-wos/main/div/div/div[2]/div/div/div[2]/app-input-route/app-base-summary-component/div/div[2]/app-records-list/app-record[1]/div/div/div[4]/div/div[1]/div[1]/a")).click();
        Thread.sleep(2000);
        getPage();
//        authenticate();
        Thread.sleep(3000);
        System.out.println("共找到" + articles.size() + "篇文章。");
        return articles;
    }

    @Override
    public void getPage() throws InterruptedException {
        String script =
                "function smoothScroll() { " +
                        "   var scrollHeight = document.body.scrollHeight + 2000; " +
                        "   var currentScroll = window.scrollY; " +
                        "   var distance = scrollHeight - currentScroll; " +
                        "   var step = distance / 25; " +  // 控制滚动的步长（30次循环滚动到底部）
                        "   function scroll() { " +
                        "       window.scrollBy(0, step); " +
                        "       if (window.scrollY + window.innerHeight < scrollHeight) { " +
                        "           requestAnimationFrame(scroll); " +
                        "       } " +
                        "   } " +
                        "   requestAnimationFrame(scroll); " +
                        "} " +
                        "smoothScroll();";

        // 执行 JavaScript
        js.executeScript(script);
        Thread.sleep(3000);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);


        List<WebElement> elements = driver.findElements(By.cssSelector(".title.title-link.font-size-18.ng-star-inserted"));

        for (WebElement element : elements) {
            Article article = new Article();
            article.setTitle(element.getText());
            String href = element.getDomAttribute("href");
            article.setAccession(href.substring(href.lastIndexOf("/") + 1));

//            System.out.println(article.getAccession() + "-" + article.getTitle());
            articles.add(article);
        }
        WebElement button = driver.findElement(By.xpath("/html/body/app-wos/main/div/div/div[2]/div/div/div[2]/app-input-route/app-base-summary-component/div/div[2]/app-page-controls[2]/div/form/div/button[2]"));
        if (button.isEnabled()){
            button.click();
            Thread.sleep(1000);
            getPage();
        }else {
//            System.out.println("已经到达最后一页。");
        }
    }


    public Article getArticle(String title) throws InterruptedException {
        driver.get("https://webofscience.clarivate.cn/wos/alldb/summary/a34d5d50-a912-4d5d-9606-794e159fde81-014cb5cebb/relevance/1");
        authenticate();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/app-wos/main/div/div/div[2]/div/div/div[2]/app-input-route/app-base-summary-component/app-search-friendly-display/div[1]/app-general-search-friendly-display/app-query-modifier/div[1]/div[1]/div/button[1]")).click();
        WebElement element = driver.findElement(By.xpath("//*[@id=\"search-option\"]"));
        element.clear();
        element.sendKeys(title);
        driver.findElement(By.xpath("//*[@id=\"snSearchType\"]/div[4]/button[2]")).click();
        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.cssSelector(".title.title-link.font-size-18.ng-star-inserted"));
        Article article = new Article();
        String paperTitle = elements.get(0).getText();
        String href = elements.get(0).getDomAttribute("href");
        article.setTitle(paperTitle);
        article.setAccession(href.substring(href.lastIndexOf("/") + 1));
        authenticate();
        if (article.getTitle().equalsIgnoreCase(title))
            return article;
        else{
            System.out.println("没有找到该文章。");
            return null;
        }
    }
}
