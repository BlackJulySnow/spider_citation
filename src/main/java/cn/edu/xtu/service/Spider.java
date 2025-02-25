package cn.edu.xtu.service;

import cn.edu.xtu.entity.Article;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Spider <T extends Article>{
    protected final WebDriver driver;
    protected final Scanner scanner;
    protected List<T> articles;

    // 定义构造函数
    public Spider() {
        driver = new EdgeDriver();
        scanner = new Scanner(System.in);
        articles = new ArrayList<>();
    }

    // 定义认证函数
    public void authenticate(){
        System.out.println("认证后，请在控制台输入任意字符以继续...");
        scanner.nextLine();
    }

    // 定义析构函数
    public void close(){
        driver.quit();
        scanner.close();
    }

    public abstract List<T> search(String paperTitle);
    public abstract void getPage();
}
