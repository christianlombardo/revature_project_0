package com.company.CMLibraries;

import java.util.Random;


public class RandomStringCML {

    private int size = 0;

    private static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z'};
    private static char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'Z'};
    private static int[] numbers = {0, 1, 2, 3, 4, 5, 6 ,7, 8, 9};

    public RandomStringCML() {
    }

    public RandomStringCML(int size) {
        this.size = size;
    }

    public String getRandomNumber(int size) {
        Random r = new Random();
        StringBuilder word = new StringBuilder();
        int num;
        for (int i = 0; i < size; i++) {
            num = r.nextInt(9);
            word.append(numbers[num]);
        }

        return word.toString();
    }



    public String getRandomString(int size) {
        Random r = new Random();
        StringBuilder word = new StringBuilder();
        int num;
        for (int i = 0; i < size; i++) {
            num = r.nextInt(2);
            //System.out.println("num = " + num);
            switch (num) {
                case 0:
                    num = r.nextInt(25);
                    word.append(alphabet[num]);
                    break;
                case 1:
                    num = r.nextInt(25);
                    word.append(ALPHABET[num]);
                    break;
            }

        }

        return word.toString();
    }

    public int getSize () {
        return this.size;
    }

    public void setSize (int size) {
        this.size = size;
    }

}
