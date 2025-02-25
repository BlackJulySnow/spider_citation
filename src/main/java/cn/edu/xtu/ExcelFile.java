package cn.edu.xtu;

import cn.edu.xtu.entity.Article;
import cn.edu.xtu.entity.ArticleCitation;
import cn.edu.xtu.entity.OutputArticle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelFile {
    private final String fileName;
    private final Workbook workbook;
    private Integer currentRow = 0;

    public ExcelFile(String fileName) throws IOException {
        this.fileName = fileName;
        FileInputStream fis = new FileInputStream(fileName);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }


    public List<Article> read() {
        Sheet sheet = workbook.getSheetAt(0);
        if (workbook.getSheet("result") == null)
            workbook.createSheet("result");
        if (workbook.getSheet("google") == null)
            workbook.createSheet("google");
        if (workbook.getSheet("wos") == null)
            workbook.createSheet("wos");

        List<Article> articles = new ArrayList<>();
        for (Row row : sheet) {
            String title = row.getCell(0).getStringCellValue();
            articles.add(new Article(title, ""));
        }
        return articles;
    }


    public List<ArticleCitation> readGoogle() {
        Sheet sheet = workbook.getSheet("google");
        List<ArticleCitation> articles = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        ArticleCitation articleCitation = null;
        for (Row row : sheet) {
            String title = row.getCell(0).getStringCellValue();
            String citation = row.getCell(1).getStringCellValue();
            if (!visited.containsKey(title)) {
                articleCitation = new ArticleCitation(new HashSet<>());
                articleCitation.setTitle(title);
                articleCitation.getCitations().add(citation);
                visited.put(title, true);
                articles.add(articleCitation);
            } else {
                articleCitation.getCitations().add(citation);
            }
            articleCitation.getCitations().remove(title);
        }
        return articles;
    }

    public List<ArticleCitation> readWOS() {
        Sheet sheet = workbook.getSheet("wos");
        List<ArticleCitation> articles = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        ArticleCitation articleCitation = null;
        for (Row row : sheet) {
            String title = row.getCell(0).getStringCellValue();
            String citation = row.getCell(2).getStringCellValue();
            if (!visited.containsKey(title)) {
                articleCitation = new ArticleCitation(new HashSet<>());
                articleCitation.setTitle(title);
                articleCitation.setAccession(row.getCell(1).getStringCellValue());
                articleCitation.getCitations().add(citation);
                visited.put(title, true);
                articles.add(articleCitation);
            } else {
                articleCitation.getCitations().add(citation);
            }
            articleCitation.getCitations().remove(title);
        }
        return articles;
    }
    public void close() {

        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(OutputArticle outputArticle) {
        // 获取当前工作表
        Sheet sheet = workbook.getSheet("result");

        for (Article article : outputArticle.getGoogleArticles()) {
            if (article.getAccession() == null || article.getAccession().isEmpty())
                continue;
            // 创建新行
            Row row = sheet.createRow(currentRow);

            // 填充 OutputArticle 的信息，仅填充一次
            row.createCell(0).setCellValue(outputArticle.getTitle());
            row.createCell(1).setCellValue(outputArticle.getAccession());
            // 填充 GoogleArticle 的信息
            row.createCell(2).setCellValue(article.getTitle());  // GoogleArticle 的标题
            row.createCell(3).setCellValue(article.getAccession());  // GoogleArticle 的 Accession

            // 更新当前行数
            currentRow++;
        }
    }

    public void writeGoogle(List<OutputArticle> outputArticles) {
        Sheet sheet = workbook.getSheet("google");
        int row = 0;
        for (OutputArticle outputArticle : outputArticles) {
            for (Article cite : outputArticle.getGoogleArticles()) {
                Row sheetRow = sheet.createRow(row);
                sheetRow.createCell(0).setCellValue(outputArticle.getTitle());
                sheetRow.createCell(1).setCellValue(cite.getTitle());
                row++;
            }
        }
    }

    public void writeWOS(List<OutputArticle> outputArticles) {
        Sheet sheet = workbook.getSheet("wos");
        int row = 0;
        for (OutputArticle outputArticle : outputArticles) {
            for (Article cite : outputArticle.getGoogleArticles()) {
                Row sheetRow = sheet.createRow(row);
                sheetRow.createCell(0).setCellValue(outputArticle.getTitle());
                sheetRow.createCell(1).setCellValue(outputArticle.getAccession());
                sheetRow.createCell(2).setCellValue(cite.getTitle());
                sheetRow.createCell(3).setCellValue(cite.getAccession());
                row++;
            }
        }
    }

    public void writeCompareResults(List<OutputArticle> outputArticles) {
        Sheet sheet = workbook.getSheet("result");
        int row = 0;
        for (OutputArticle outputArticle : outputArticles) {
            for (Article cite : outputArticle.getGoogleArticles()) {
                Row sheetRow = sheet.createRow(row);
                sheetRow.createCell(0).setCellValue(outputArticle.getTitle());
                sheetRow.createCell(1).setCellValue(outputArticle.getAccession());
                sheetRow.createCell(2).setCellValue(cite.getTitle());
                row++;
            }
        }
    }
}
