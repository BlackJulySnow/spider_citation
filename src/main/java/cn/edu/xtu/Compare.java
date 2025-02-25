package cn.edu.xtu;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.entity.ArticleCitation;
import cn.edu.xtu.entity.OutputArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Compare {
    public static void main(String[] args) throws IOException {

        ExcelFile excelFile = new ExcelFile("citation.xlsx");
        List<OutputArticle> results = new ArrayList<>();
        List<ArticleCitation> googleArticles = excelFile.readGoogle();
        List<ArticleCitation> wosArticles = excelFile.readWOS();
        for (ArticleCitation wos : wosArticles){
            Optional<ArticleCitation> google = googleArticles.stream().filter(g -> g.getTitle().equalsIgnoreCase(wos.getTitle())).findFirst();
            if (google.isPresent()){
                Set<String> citations1 = google.get().getCitations();
                Set<String> citations2 = wos.getCitations();


                // 先去除前后空格并转换为小写
                Set<String> trimmedCitations1 = citations1.stream()
                        .map(c -> c.trim().toLowerCase())  // 去掉前后空格并转换为小写
                        .collect(Collectors.toSet());

                Set<String> trimmedCitations2 = citations2.stream()
                        .map(c -> c.trim().toLowerCase())  // 去掉前后空格并转换为小写
                        .collect(Collectors.toSet());

                // 计算 citaitons1 中有而 citations2 中没有的部分
                trimmedCitations1.removeAll(trimmedCitations2);  // 移除 citations2 中有的项

                // 输出结果
                if (!trimmedCitations1.isEmpty()) {
                    System.out.println("Title: " + wos.getTitle());
//                    System.out.println("Citations in google but not in WOS: ");
//                    trimmedCitations1.forEach(System.out::println);
                    OutputArticle outputArticle = new OutputArticle(trimmedCitations1.stream().map(s -> new Article(s, null)).toList());
                    outputArticle.setTitle(wos.getTitle());
                    outputArticle.setAccession(wos.getAccession());
                    results.add(outputArticle);
                }
            }
        }
        excelFile.writeCompareResults(results);
        excelFile.close();
    }

}
