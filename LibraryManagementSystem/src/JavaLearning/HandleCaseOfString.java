package JavaLearning;

import java.util.Arrays;

public class HandleCaseOfString {
    public static void main(String[] args) {
        String str = "ayush Goyal";
        String[] words = str.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            words[i] = capitalizedWord;
        }
        str = String.join(" ", words);
        System.out.println(str);
    }
}
