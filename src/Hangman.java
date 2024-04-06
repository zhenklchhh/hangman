import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman{
    public static final Scanner scanner = new Scanner(System.in);
    public static final String[][] gallowStages = new String[7][6];
    public static Set<Character> usedLetters = new HashSet<>();
    public static String word;
    public static StringBuilder hidenWord;
    public static int countMistakes = 0;
    public static void loadGallowStages(){
        gallowStages[0] = new String[]{
                "  +----+",
                "  |    |",
                "       |",
                "       |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[1] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                "       |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[2] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                "  |    |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[3] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                "  |\\   |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[4] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                " /|\\   |",
                "       |",
                "       |",
                "========="
        };
        gallowStages[5] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                " /|\\   |",
                "   \\   |",
                "       |",
                "========="
        };
        gallowStages[6] = new String[]{
                "  +----+",
                "  |    |",
                "  O    |",
                " /|\\   |",
                " / \\   |",
                "       |",
                "========="
        };
    }
    public static void loadWords(List<String> words){
        try {
            File wordFile = new File("src/words.txt");
            Scanner scanWords = new Scanner(wordFile);
            while(scanWords.hasNextLine()){
                words.add(scanWords.next());
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File with words not found");
        }
    }
    public static void printStage(){
        for(int i = 0; i < 6; i++){
            System.out.println(gallowStages[countMistakes][i]);
        }
        System.out.println("Количество ошибок: " + countMistakes);
    }
    public static boolean checkWinLossConditions(){
        if(String.valueOf(hidenWord).equals(word)){
            System.out.println("Ты выйграл! :)");
            System.out.println("Загаданное слово: " + word);
            return true;
        }
        else if (countMistakes == 6){
            printStage();
            System.out.println("Тебя повесили :(");
            System.out.println("Загаданное слово: " + word);
            return true;
        }
        return false;
    }
    public static boolean isValidLetter(char letter){
        if(!(Character.isLetter(letter) && Character.UnicodeBlock.of(letter) == Character.UnicodeBlock.CYRILLIC)){
            System.out.println("Неправильный ввод. Вводите только маленькие буквы русского алфавита");
            return false;
        }else if (usedLetters.contains(letter)){
            System.out.println("Эта буква уже была использована");
            return false;
        }
        return true;
    }
    public static boolean checkLetter(char letter){
        boolean hasLetter = false;
        int index = -1;
        do {
            index = word.indexOf(letter, index + 1);
            if (index != -1) {
                hasLetter = true;
                hidenWord.deleteCharAt(index);
                hidenWord.insert(index, letter);
            }
        } while (index != -1);
        return hasLetter;
    }
    public static void gameRound(){
        printStage();
        System.out.println(hidenWord);
        System.out.println("Введите букву: ");
        char letter = scanner.next().charAt(0);
        if(!isValidLetter(letter))
            return;
        usedLetters.add(letter);
        countMistakes += checkLetter(letter) ? 0 : 1;
    }
    public static void startGame() {
        hidenWord = new StringBuilder("-".repeat(word.length()));
        boolean gameFinished = false;
        while(!gameFinished){
            gameRound();
            gameFinished = checkWinLossConditions();
        }
        usedLetters.clear();
        countMistakes = 0;
    }
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру Виселица.");
        List<String> words = new ArrayList<>();
        Random rand = new Random();
        loadGallowStages();
        loadWords(words);
        String command;
        while(true){
            System.out.println("Чтобы начать новую игру введи команду start, выход из приложения - exit");
            command = scanner.next();
            if(command.equals("start")){
                word = words.get(rand.nextInt(words.size()));
                startGame();
            }
            else if (command.equals("exit")){
                System.exit(0);
            }
            else{
                System.out.println("Неправильная команда.");
            }
        }
    }
}