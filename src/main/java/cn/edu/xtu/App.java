package cn.edu.xtu;

public class App {
    public static void main( String[] args ) {
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        SpiderGoogle spiderGoogle = new SpiderGoogle();
        try {
            spiderGoogle.search("Deep learning for drug repurposing: Methods, databases, and applications");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭浏览器
            spiderGoogle.close();
        }
    }




}
