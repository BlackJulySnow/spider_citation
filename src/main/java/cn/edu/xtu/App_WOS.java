package cn.edu.xtu;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.entity.OutputArticle;
import cn.edu.xtu.service.impl.GoogleImpl;
import cn.edu.xtu.service.impl.WOSImpl;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App_WOS {
    public static void main( String[] args ) throws IOException {
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        EdgeDriver driver = new EdgeDriver();
        WOSImpl wosImpl = new WOSImpl(driver);
        ExcelFile excelFile = new ExcelFile("citation.xlsx");
        List<Article> articles = excelFile.read();
        try {
            List<OutputArticle> outputArticles = new ArrayList<>();

            for (Article article : articles) {
                List<Article> wosArticles;
                try {
                    wosArticles =  new ArrayList<>(wosImpl.search(article.getTitle()));
                }catch (Exception e){
                    wosImpl.reset();
                    System.out.println(article.getTitle());
                    continue;
                }
                OutputArticle outputArticle = new OutputArticle();
                outputArticle.setTitle(article.getTitle());
                outputArticle.setAccession(wosImpl.getArticle().getAccession());
                outputArticle.setGoogleArticles(wosArticles);
                outputArticles.add(outputArticle);
                wosImpl.clear();
//                break;
            }
            excelFile.writeWOS(outputArticles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭浏览器
            driver.quit();
            wosImpl.close();
            excelFile.close();
        }
    }

}
