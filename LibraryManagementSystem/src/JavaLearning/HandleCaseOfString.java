package JavaLearning;

public class HandleCaseOfString {
    public static void main(String[] args) {
         String str = "ayush    goyal";
        String result = str.replaceAll("[^a-zA-Z0-9]", "");
        System.out.println(result);
    }
}
