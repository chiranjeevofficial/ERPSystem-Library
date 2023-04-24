 package fms;
public class Main {
    public static void main(String[] args) {
        new HomePage();
    }

    static void passwordCombination() {
        int[] digits = {1, 2, 4, 5, 7, 8, 0};
        int count = 0;
        for (int digit : digits) {
            for (int i : digits) {
                for (int j : digits) {
                    for (int k : digits) {
                        int number = digit * 1000 + i * 100 + j * 10 + k;
                        System.out.print(number + " ");
                        System.out.println(number == 8000 ? count : "");
                        count++;
                    }
                }
            }
        }
        System.out.println("Total number of 4-digit numbers: " + count);
    }
}
