package cn.edu.xtu;

import cn.edu.xtu.entity.GoogleArticle;
import cn.edu.xtu.entity.WOSArticle;
import cn.edu.xtu.service.impl.GoogleImpl;
import cn.edu.xtu.service.impl.WOSImpl;
import cn.edu.xtu.utils.ComparisonUtil;
import cn.edu.xtu.utils.FileUtil;

import java.util.List;

public class App {
    public static void main( String[] args ) {
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        GoogleImpl googleImpl = new GoogleImpl();
        WOSImpl wosImpl = new WOSImpl();
        String paperTitle = "Deep learning for drug repurposing: Methods, databases, and applications";
        try {
            List<GoogleArticle> googleArticles = googleImpl.search(paperTitle);
            List<WOSArticle> wosArticles = wosImpl.search(paperTitle);
            List<WOSArticle> results = ComparisonUtil.compareByTitle(googleArticles, wosArticles);
            FileUtil.writeToExcel(results);
        } finally {
            // 关闭浏览器
            googleImpl.close();
            wosImpl.close();
        }
    }
}
