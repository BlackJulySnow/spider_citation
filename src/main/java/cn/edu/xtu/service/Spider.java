package cn.edu.xtu.service;

import cn.edu.xtu.entity.Article;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Spider{
    protected final WebDriver driver;
    protected final Scanner scanner;
    protected final JavascriptExecutor js;
    protected List<Article> articles;
    protected boolean authenticated = false;
    protected Article thisArticle;

    // 定义构造函数
    public Spider(EdgeDriver driver) {
        this.driver = driver;
        scanner = new Scanner(System.in);
        js = driver;
        articles = new ArrayList<>();
    }

    // 定义认证函数
    public void authenticate(){
        if (!authenticated || driver.getCurrentUrl().contains("sorry")) {
            System.out.print("认证后，请在控制台输入任意字符以继续...");
            scanner.nextLine();
        }
        authenticated = true;
    }

    // 定义析构函数
    public void close(){
        scanner.close();
    }

    public void clear(){
        articles.clear();
    }

    public abstract List<Article> search(String paperTitle) throws InterruptedException;
    public abstract void getPage() throws InterruptedException;
}
