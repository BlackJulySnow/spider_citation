package cn.edu.xtu;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.entity.OutputArticle;
import org.openqa.selenium.edge.EdgeDriver;
import cn.edu.xtu.service.impl.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App_Google {
    public static void main( String[] args ) throws IOException {
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        EdgeDriver driver = new EdgeDriver();
        GoogleImpl googleImpl = new GoogleImpl(driver);
        WOSImpl wosImpl = new WOSImpl(driver);
        ExcelFile excelFile = new ExcelFile("citation.xlsx");
        List<Article> articles = excelFile.read();
        try {
//            wosImpl.getArticle("Deep learning for drug repurposing: Methods, databases, and applications");
            List<OutputArticle> outputArticles = new ArrayList<>();

            for (Article article : articles) {
                List<Article> googleArticles;
                try {
                    googleArticles =  new ArrayList<>(googleImpl.search(article.getTitle()));
                }catch (Exception e){
                    System.out.println(article.getTitle());
                    continue;
                }
                String title = article.getTitle();
                OutputArticle outputArticle = new OutputArticle();
                outputArticle.setTitle(title);
                outputArticle.setGoogleArticles(googleArticles);
                outputArticles.add(outputArticle);
                googleImpl.clear();
//                List<Article> wosArticles = wosImpl.search(article.getTitle());
//                List<Article> results = ComparisonUtil.compareByTitle(googleArticles, wosArticles);
//
//                for (Article result : results) {
//                    Article found = wosImpl.getArticle(result.getTitle());
//                    if (found != null) {
//                        result.setAccession(found.getAccession());
//                    }
//                }
//
//
//                String originalWOSID = wosImpl.getArticle(article.getTitle()).getTitle();
//                article.setAccession(originalWOSID);
//                OutputArticle outputArticle = new OutputArticle(results);
//                outputArticle.setTitle(article.getTitle());
//                outputArticle.setAccession(article.getAccession());
//                excelFile.write(outputArticle);
//                break;
            }
            excelFile.writeGoogle(outputArticles);
//
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
        } finally {
            // 关闭浏览器
            driver.quit();
            googleImpl.close();
            wosImpl.close();
            excelFile.close();
        }
    }

}
