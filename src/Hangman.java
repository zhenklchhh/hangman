import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman{
    public static final File wordFile = new File("src/words.txt");
    public static List<String> words = new ArrayList<>();
    public static void loadWords(){
        try {
            Scanner scanWords = new Scanner(wordFile);
            while(scanWords.hasNextLine()){
                words.add(scanWords.next());
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File with words not found");
        }
    }
    public static void startGame() {
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));
        int wordSize = word.length();
        String hidenWord = "-".repeat(wordSize);

        int countMistakes = 0;
    }
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру Виселица.\n" +
                "(Чтобы начать игру введи команду start, выход из приложения - exit)");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        if (command.equals("start")){
            loadWords();
            startGame();
        }
        else if (command.equals("exit")){
            System.exit(0);
        }
    }
}