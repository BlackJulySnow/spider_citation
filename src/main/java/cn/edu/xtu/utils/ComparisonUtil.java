package cn.edu.xtu.utils;

import cn.edu.xtu.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class ComparisonUtil {
    public static List<Article> compareByTitle(List<Article> list1, List<Article> list2) {
        List<Article> result = new ArrayList<>();
        for (Article googleArticle : list1){
            boolean flag = true;
            for (Article wosArticle : list2){
                if (googleArticle.getTitle().equalsIgnoreCase(wosArticle.getTitle())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                result.add(googleArticle);
            }
        }
        return result;
    }
}
