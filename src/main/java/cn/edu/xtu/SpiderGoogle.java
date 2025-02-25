package cn.edu.xtu;

import cn.edu.xtu.entity.GoogleArticle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpiderGoogle {
    private final WebDriver driver;
    private final Scanner scanner;
    private List<GoogleArticle> articles = new ArrayList<>();
    public SpiderGoogle(){
        scanner = new Scanner(System.in);
        driver = new EdgeDriver();
    }

    public List<GoogleArticle> search(String paperTitle) throws InterruptedException {
        // 访问 Google Scholar 页面
        driver.get("https://scholar.google.com");
        // 在搜索框中输入文章标题并搜索
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(paperTitle);
        searchBox.submit();

        authenticate();

        // 找到该文章的引用链接（如果文章在第一页）
        WebElement citationsLink = driver.findElement(By.partialLinkText("被引用次数"));
        citationsLink.click();

        authenticate();


        getPage();

        for (GoogleArticle article : articles) {
            System.out.println(article.getTitle());
        }
        return articles;
    }

    public void getPage() throws InterruptedException {
        // 获取所有引用文章的标题
        List<WebElement> citationTitles = driver.findElements(By.className("gs_rt"));
        // 输出所有标题
        for (WebElement title : citationTitles) {
            try {
                WebElement a = title.findElement(By.tagName("a"));
                System.out.println(a.getText());
                GoogleArticle googleArticle = new GoogleArticle();
                googleArticle.setTitle(a.getText());
                articles.add(googleArticle);
            }catch (Exception e){
                System.out.println(title.getText());
                GoogleArticle googleArticle = new GoogleArticle();
                googleArticle.setTitle(title.getText());
                articles.add(googleArticle);
            }

        }

        try {
            WebElement nextPageLink = driver.findElement(By.partialLinkText("下一页"));
            if (nextPageLink.isDisplayed()) {
                nextPageLink.click();
                // 休眠1秒，防止监测
                Thread.sleep(1500);
                getPage();
            }
        }catch (Exception e){
            System.out.println("查找完毕");
        }
    }

    public void authenticate(){
        System.out.println("认证后，请在控制台输入任意字符以继续...");
        scanner.nextLine();
    }

    public void close(){
        driver.quit();
        scanner.close();
    }
}
