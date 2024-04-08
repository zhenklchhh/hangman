import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman{
    public static Scanner scanner = new Scanner(System.in);
    public static Scaffold scaffold = new Scaffold();
    public static Set<Character> usedLetters = new HashSet<>();
    public static String word;
    public static StringBuilder hidenWord;
    public static int DEATH_STATE = 6;
    public static int countMistakes = 0;
    public static void loadWords(List<String> words){
        try {
            File wordFile = new File("resources/words.txt");
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
        scaffold.drawHangman(countMistakes);
        System.out.println("Количество ошибок: " + countMistakes);
    }
    public static boolean checkWin(){
        return String.valueOf(hidenWord).equals(word);
    }
    public static void printWinMessage(){
        System.out.println("Ты выйграл! :)");
        System.out.println("Загаданное слово: " + word);
    }
    public static boolean checkLose(){
        return countMistakes == DEATH_STATE;
    }
    public static void printLoseMessage(){
        System.out.println("Тебя повесили :(");
        System.out.println("Загаданное слово: " + word);
    }
    public static boolean isValidLetter(char letter){
        if(!(Character.isLetter(letter) && Character.UnicodeBlock.of(letter) == Character.UnicodeBlock.CYRILLIC)){
            System.out.println("Неправильный ввод. Вводите только маленькие буквы русского алфавита");
            return false;
        }
        return true;
    }
    public static boolean isGuessedLetter(char letter){
        if (usedLetters.contains(letter)){
            System.out.println("Эта буква уже была использована");
            return false;
        }
        return true;
    }
    public static boolean isLetterInWord(char letter){
        boolean hasLetter = false;
        for(int i = 0; i < word.length(); i++){
            if (word.charAt(i) == letter){
                hasLetter = true;
                hidenWord.replace(i, i + 1, String.valueOf(letter));
            }
        }
        return hasLetter;
    }
    public static void gameRound(){
        printStage();
        System.out.println(hidenWord);
        System.out.println("Введите букву: ");
        char letter = Character.toLowerCase(scanner.next().charAt(0));
        if(!isValidLetter(letter) || !isGuessedLetter(letter))
            return;
        usedLetters.add(letter);
        countMistakes += isLetterInWord(letter) ? 0 : 1;
    }
    public static void startGame() {
        hidenWord = new StringBuilder("-".repeat(word.length()));
        boolean gameFinished = false;
        while(!gameFinished){
            gameRound();
            if(checkWin()){
                printWinMessage();
                gameFinished = true;
            }
            if(checkLose()){
                printStage();
                printLoseMessage();
                gameFinished = true;
            }
        }
        usedLetters.clear();
        countMistakes = 0;
    }
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру Виселица.");
        List<String> words = new ArrayList<>();
        Random rand = new Random();
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