package Exercise2;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;

public class Exercise2 {

    public static void main(String[] args) throws IOException {

        File text = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/test1.txt");
        File textChanged = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/test1Changed.txt");
        File textToPlaintext = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/test1Decrypt.txt");

        Scanner stext = new Scanner(text);
        Scanner sTextChanged = new Scanner(textChanged);
        Scanner sTextChanged2 = new Scanner(textChanged);
        Scanner sTextChanged3 = new Scanner(textChanged);

        PrintWriter writeTextChanged = new PrintWriter(new FileWriter(textChanged));
        PrintWriter writeTextChanged2 = new PrintWriter(new FileWriter(textChanged));
        PrintWriter writeTextDrecrypt = new PrintWriter(new FileWriter(textToPlaintext));

        changeCase(stext, writeTextChanged);
        encrypt(sTextChanged, "ncl", writeTextChanged2);
        decrypt(sTextChanged3, "ncl", writeTextDrecrypt);

        stext.close();
        sTextChanged.close();
        sTextChanged2.close();
        sTextChanged3.close();

        writeTextChanged.close();
        writeTextChanged2.close();
        writeTextDrecrypt.close();


/*
        File ciphertext = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/Exercise2Ciphertext.txt");
        File ciphertextChanged = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/Exercise2CiphertextChanged.txt");
        File ciphertextToPlaintext = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/ciphertextToPlaintext.txt");

        File plaintext = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/plaintext.txt");
        File plaintextChanged = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/plaintextChanged.txt");
        File plaintextDecrypt = new File("C:/Users/nhale/Documents/Cryptography/Exercise2Files/plaintextDecrypt.txt");

        Scanner in = new Scanner(plaintext);

        Scanner inChanged = new Scanner(plaintextChanged);
        Scanner inChanged2 = new Scanner(plaintextChanged);
        Scanner inChanged3 = new Scanner(plaintextChanged);

        Scanner ctext = new Scanner(ciphertext);
        Scanner cTextChanged = new Scanner(ciphertextChanged);

        PrintWriter writePlaintextChanged = new PrintWriter(new FileWriter(plaintextChanged));
        PrintWriter writePlaintextChanged2 = new PrintWriter(new FileWriter(plaintextChanged));
        PrintWriter writePlaintextDrecrypt = new PrintWriter(new FileWriter(plaintextDecrypt));

        PrintWriter out = new PrintWriter(new FileWriter(plaintextChanged));
        PrintWriter cTextChangedWriter = new PrintWriter(new FileWriter(ciphertextChanged));
        PrintWriter writeToPlaintext = new PrintWriter(new FileWriter(ciphertextToPlaintext));

        changeCase(in, writePlaintextChanged);
        changeCase(ctext, cTextChangedWriter);

        System.out.println("Frequencies of provided plaintext");
        System.out.println(calcFreq(inChanged));

        System.out.println("Frequencies of ciphertext");
        System.out.println(calcFreq(cTextChanged));

        in.close();
        encrypt(inChanged2, "ncl", writePlaintextChanged2);
        decrypt(inChanged3, "ncl", writePlaintextDrecrypt);
        writePlaintextChanged.close();

        in.close();
        inChanged.close();
        out.close();

        ctext.close();
        cTextChanged.close();
        cTextChangedWriter.close();
        writeToPlaintext.close();*/

    }

    private static void changeCase(Scanner scanner, PrintWriter printWriter) {

        while (scanner.hasNext()) {
            String line = scanner.nextLine().replaceAll("\\s", "").replaceAll("\\p{P}", "");
            if (line.length() == 0) {
                continue;
            } else {
                printWriter.println(line.toLowerCase());
            }
        }
        //scanner.close();
        printWriter.close();
    }

    private static Map calcFreq(Scanner scanner) {

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String averageFreq = "";
        char[] alphabetArray = alphabet.toCharArray();
        int searchStringArrayLength = 0;

        Map<Character, Integer> letterCount = new HashMap<>();

        for (int j = 0; j < alphabetArray.length; j++) {
            letterCount.put(alphabetArray[j], 0);
        }

        DecimalFormat formatter = new DecimalFormat("#0.0000");

        while (scanner.hasNext()) {
            char[] searchStringArray = scanner.nextLine().toCharArray();
            Arrays.sort(searchStringArray);

            int arrayLocation = 0;

            for (int i = 0; i < alphabetArray.length; i++) {
                int count = searchForChar(alphabetArray[i], searchStringArray, arrayLocation);
                arrayLocation += count;

                if (letterCount.containsKey(alphabetArray[i])) {
                    letterCount.replace(alphabetArray[i], letterCount.get(alphabetArray[i]) + count);
                }
            }
        }

        Map<Character, String> letterFrequency = new HashMap<>();

        for (int q = 0; q < letterCount.size(); q++) {
            searchStringArrayLength += letterCount.get(alphabetArray[q]);
        }

        for (int k = 0; k < letterCount.size(); k++) {
            double countDouble = (double) letterCount.get(alphabetArray[k]);
            double ssaDouble = (double) searchStringArrayLength;
            double frequency = countDouble / ssaDouble;
            letterFrequency.put(alphabetArray[k], formatter.format(frequency));
        }
        return letterFrequency;
    }

    private static int searchForChar(char search, char[] searchArray, int arrayLocation) {
        int count = 0;

        while (arrayLocation < searchArray.length && searchArray[arrayLocation] == search) {
            count++;
            arrayLocation++;
        }
        return count;
    }

    public static void encrypt(Scanner scanner, final String key, PrintWriter printWriter) {

        char[] keyArray = key.toCharArray();
        Map<Character, Integer> keyValues = new LinkedHashMap<>();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] alphabetArray = alphabet.toCharArray();
        Map<Character, Integer> alphabetValues = new LinkedHashMap<>();

        for (int q = 0; q < alphabetArray.length; q++) {
            alphabetValues.put(alphabetArray[q], q);
        }

        for (int i = 0; i < keyArray.length; i++) {
            for (int j = 0; j < alphabetArray.length; j++) {
                if (keyArray[i] == alphabetArray[j]) {
                    keyValues.put(keyArray[i], j);
                }
            }
        }
        String encrypted_msg = "";

        while (scanner.hasNext()) {
            char[] searchStringArray = scanner.nextLine().toLowerCase().toCharArray();

            for (int h = 0, g = 0; h < searchStringArray.length; h++) {
                char c = searchStringArray[h];
                if (c >= 'a' && c <= 'z') {
                    int changeValue = (alphabetValues.get(searchStringArray[h]) + keyValues.get(keyArray[g])); // Math.abs();
                    if (changeValue > 25) {
                        changeValue = Math.abs(changeValue - 25);
                    }
                    encrypted_msg = encrypted_msg + Character.toString(alphabetArray[changeValue]);
                    g = ++g % key.length();
                    printWriter.print(encrypted_msg);
                }else {
                    printWriter.print(c);
                }
                encrypted_msg = "";
            }
            printWriter.println("\n");
        }
        scanner.close();
        printWriter.close();
    }


    static void decrypt(Scanner scanner, final String key, PrintWriter printWriter) {

        char[] keyArray = key.toCharArray();
        Map<Character, Integer> keyValues = new LinkedHashMap<>();

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] alphabetArray = alphabet.toCharArray();
        Map<Character, Integer> alphabetValues = new LinkedHashMap<>();

        for (int i = 0; i < alphabetArray.length; i++) {
            alphabetValues.put(alphabetArray[i], i);
        }

        for (int j = 0; j < keyArray.length; j++) {
            for (int k = 0; k < alphabetArray.length; k++) {
                if (keyArray[j] == alphabetArray[k]) {
                    keyValues.put(keyArray[j], k);
                }
            }
        }
        String decrypted_msg = "";

        while (scanner.hasNext()) {
            char[] searchStringArray = scanner.nextLine().toLowerCase().toCharArray();

            for (int h = 0, g = 0; h < searchStringArray.length; h++) {
                char c = searchStringArray[h];
                if (c >= 'a' && c <= 'z') {
                    int changeValue = (alphabetValues.get(searchStringArray[h]) - keyValues.get(keyArray[g])); // Math.abs();
                    if (changeValue > 25 ) {
                        changeValue = Math.abs(changeValue - 25);
                    }
                    if(changeValue < 0){
                        changeValue = Math.abs(25 + changeValue);
                    }
                    decrypted_msg = decrypted_msg + Character.toString(alphabetArray[changeValue]);
                    g = ++g % key.length();
                    printWriter.print(decrypted_msg);
                } else {
                    printWriter.print(c);
                }
                decrypted_msg = "";
            }
            printWriter.println("\n");
        }
        scanner.close();
        printWriter.close();
    }

    public int keyLength(Scanner scanner) {
        int keyLength = 0;

        while (scanner.hasNext()) {
            char[] searchStringArray = scanner.nextLine().toCharArray();


        }
        return keyLength;
    }

    public String findKey(Scanner scanner, Map map, int keyLength) {
        String key = "";

        while (scanner.hasNext()) {
            char[] searchStringArray = scanner.nextLine().toCharArray();


        }

        return key;
    }
}