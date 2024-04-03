import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman{
    public static final Scanner scanner = new Scanner(System.in);
    public static final File wordFile = new File("src/words.txt");
    public static List<String> words = new ArrayList<>();
    public static final String[][] gallowStages = new String[7][6];
    public static void loadGallowStages(){
        gallowStages[0] = new String[]{
                "  +---_+",
                "  |    |",
                "       |",
                "       |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[1] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                "       |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[2] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                "  |    |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[3] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                "  |\\  |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[4] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                " /|\\  |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[5] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                " /|\\  |",
                "   \\  |",
                "       |",
                "========="
        };
        gallowStages[6] = new String[]{
                "  +---_+",
                "  |    |",
                "  O    |",
                " /|\\  |",
                " / \\  |",
                "       |",
                "========="
        };
    }
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
    public static void printStage(int mistakes, String hidenWord){
        for(int i = 0; i < 6; i++){
            System.out.println(gallowStages[mistakes - 1][i]);
        }
        System.out.println("Количество ошибок: " + mistakes);
        System.out.println(hidenWord);
    }
    public static void startGame() {
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));
        int wordSize = word.length();
        String hidenWord = "-".repeat(wordSize);
        int countMistakes = 0;
        String letter;
        printStage(countMistakes, hidenWord);
        do{
            letter = scanner.next();

        }while(true);
    }
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру Виселица.\n" +
                "(Чтобы начать игру введи команду start, выход из приложения - exit)");
        loadGallowStages();
        loadWords();
        String command;
        do{
            command = scanner.next();
            if(command.equals("start")){
                startGame();
            }
            else if (command.equals("exit")){
                System.exit(0);
            }
            else{
                System.out.println("Неправильная команда." +
                        "(Чтобы начать игру введи команду start, выход из приложения - exit)");
            }
        }
        while(true);
    }
}