package exercise1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Convert to one case
 * calculate the frequency of each letter
 * use frequency to cryptoanalyse cyphertext to reveal plain text
 */

public class Exercise1 {

    int arrayLocation = 0;
    int decryptArrayLocation = 0;

    public static void main(String[] args) throws IOException {

        File input = new File("C:/Users/nhale/Documents/Cryptography/Exercise1Files/thirdTest.txt");
        File inputCaseChanged = new File("C:/Users/nhale/Documents/Cryptography/Exercise1Files/thirdTest-changed.txt");
        File ciphertext = new File("C:/Users/nhale/Documents/Cryptography/Exercise1Files/Exercise1Ciphertext.txt");
        File cipherTextChanged = new File("C:/Users/nhale/Documents/Cryptography/Exercise1Files/Exercise1Ciphertext-changed.txt");
        File ciphertextToPlaintext = new File("C:/Users/nhale/Documents/Cryptography/Exercise1Files/ciphertextToPlaintext.txt");
        // C:/Users/nhale/OneDrive/Documents/Cryptography/Exercise1/
        Scanner in = new Scanner(input);
        Scanner inChanged = new Scanner(inputCaseChanged);
        Scanner ctext = new Scanner(ciphertext);
        Scanner cTextChanged = new Scanner(cipherTextChanged);

        PrintWriter out = new PrintWriter(new FileWriter(inputCaseChanged));
        PrintWriter cTextChangedWriter = new PrintWriter(new FileWriter(cipherTextChanged));
        PrintWriter writeToPlaintext = new PrintWriter(new FileWriter(ciphertextToPlaintext));

        changeCase(in, out);
        System.out.println(calcFreq(inChanged));
        //changeCase(ctext,cTextChangedWriter);
        decrypt(ctext, writeToPlaintext);

        in.close();
        inChanged.close();
        out.close();

        ctext.close();
        cTextChanged.close();
        cTextChangedWriter.close();
        writeToPlaintext.close();
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
        //  System.out.println(letterCount);

        Map<Character, String> letterFrequency = new HashMap<>();

        for (int q = 0; q < letterCount.size(); q++) {
            searchStringArrayLength += letterCount.get(alphabetArray[q]);
        }

        for (int k = 0; k < letterCount.size(); k++) {
            double countDouble = (double) letterCount.get(alphabetArray[k]);
            double ssaDouble = (double) searchStringArrayLength;
            double frequency = countDouble / ssaDouble;
            letterFrequency.put(alphabetArray[k], formatter.format(frequency)); //"Letter: " + alphabetArray[k] + " | Frequency: " +
            //System.out.println());
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

    public static void decrypt(Scanner scanner, PrintWriter printWriter) {
        Map<Character, Character> sub = new HashMap<Character, Character>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String cipherFreq = "ejahickvmuzpyrwfnlsxgotbqd";
        String cipherFreqFromWiki = "efgpitksmbohqrwjnlvxyzaucd";

        String tune = "efghijklmnopqrstuvwxyzabcd";

        char[] alphabetArray = alphabet.toCharArray();
        char[] cipherFreqArray = cipherFreq.toCharArray();
        char[] ciphertextFromWikiArray = cipherFreqFromWiki.toCharArray();
        char[] tuneArray = tune.toCharArray();

        for (int j = 0; j < alphabetArray.length; j++) {
            sub.put(tuneArray[j], alphabetArray[j]);
        }// fills sub map

        System.out.println(sub); // test map is full
        String line = "";

        while (scanner.hasNext()) {
            char[] scannerCharArray = scanner.nextLine().toCharArray();

            for (int i = 0; i < scannerCharArray.length; i++) {
                if (sub.containsKey(scannerCharArray[i])) {
                    String s = Character.toString(sub.get(scannerCharArray[i]));
                    System.out.print(s);
                    line = line + Character.toString(sub.get(scannerCharArray[i]));
                    printWriter.print(line);
                    line = "";
                } else {
                    printWriter.print(scannerCharArray[i]);
                }
            }
        }
        printWriter.close();
    }
}

