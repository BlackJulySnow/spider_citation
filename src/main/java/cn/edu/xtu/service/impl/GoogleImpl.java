package cn.edu.xtu.service.impl;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.service.Spider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

public class GoogleImpl extends Spider{
    public GoogleImpl(EdgeDriver driver) {
        super(driver);
    }


    @Override
    public List<Article> search(String paperTitle) {
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

        System.out.println("共找到" + articles.size() + "篇文章。");
        return articles;
    }

    @Override
    public void getPage() {
        // 获取所有引用文章的标题
        List<WebElement> citationTitles = driver.findElements(By.className("gs_rt"));
        // 输出所有标题
        for (WebElement title : citationTitles) {
            // 获取最后一个标签
            List<WebElement> elements = title.findElements(By.xpath("./*"));
            if (!elements.isEmpty()) {
                WebElement last = elements.get(elements.size() - 1);
//                System.out.println(last.getText());
                Article googleArticle = new Article();
                googleArticle.setTitle(last.getText());
                articles.add(googleArticle);
            }
        }

        try {
            WebElement nextPageLink = driver.findElement(By.partialLinkText("下一页"));
            if (nextPageLink.isDisplayed()) {
                nextPageLink.click();
                authenticate();
                // 休眠1秒，防止监测
                Thread.sleep(1500);
                getPage();
            }
        }catch (Exception e){
            System.out.println("查找完毕");
        }
    }


}
