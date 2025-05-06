import java.io.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicTaToe {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameConfig config = ConfigManager.loadConfig();

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Нова гра");
            System.out.println("2. Налаштування");
            System.out.println("3. Перегляд статистики");
            System.out.println("0. Вихід");
            System.out.print("Оберіть опцію: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                playGame(config);
            } else if (choice.equals("2")) {
                config = configureGame();
                ConfigManager.saveConfig(config);
            } else if (choice.equals("3")) {
                StatsManager.showStats();
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Невірна опція.");
            }
        }
    }

    public static void playGame(GameConfig config) {
        int size = config.fieldSize;
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = ' ';

        char currentPlayer = 'X';
        String currentName = config.player1Name;
        int moves = 0;
        boolean gameEnded = false;

        while (!gameEnded) {
            printBoard(board);
            System.out.println(currentName + " (" + currentPlayer + "), ваш хід (рядок та стовпець): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            scanner.nextLine(); // очистка

            if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                moves++;

                if (checkWin(board, currentPlayer)) {
                    printBoard(board);
                    System.out.println("Переміг " + currentName + "!");
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    StatsManager.saveStat(new GameStat(currentName, date, String.valueOf(currentPlayer), size));
                    gameEnded = true;
                } else if (moves == size * size) {
                    printBoard(board);
                    System.out.println("Нічия!");
                    gameEnded = true;
                } else {
                    // Зміна гравця
                    if (currentPlayer == 'X') {
                        currentPlayer = 'O';
                        currentName = config.player2Name;
                    } else {
                        currentPlayer = 'X';
                        currentName = config.player1Name;
                    }
                }
            } else {
                System.out.println("Неправильний хід. Спробуйте ще раз.");
            }
        }
    }

    public static void printBoard(char[][] board) {
        int size = board.length;
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(" " + board[i][j]);
                if (j < size - 1)
                    System.out.print(" |");
            }
            System.out.println();
            if (i < size - 1) {
                for (int j = 0; j < size; j++) {
                    System.out.print("---");
                    if (j < size - 1)
                        System.out.print("+");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static boolean checkWin(char[][] board, char player) {
        int size = board.length;
        boolean win;

        // Перевірка рядків та стовпців
        for (int i = 0; i < size; i++) {
            win = true;
            for (int j = 0; j < size; j++)
                if (board[i][j] != player)
                    win = false;
            if (win)
                return true;

            win = true;
            for (int j = 0; j < size; j++)
                if (board[j][i] != player)
                    win = false;
            if (win)
                return true;
        }

        // Діагоналі
        win = true;
        for (int i = 0; i < size; i++)
            if (board[i][i] != player)
                win = false;
        if (win)
            return true;

        win = true;
        for (int i = 0; i < size; i++)
            if (board[i][size - i - 1] != player)
                win = false;
        return win;
    }

    public static GameConfig configureGame() {
        System.out.print("Введіть ім'я першого гравця: ");
        String p1 = scanner.nextLine();
        System.out.print("Введіть ім'я другого гравця: ");
        String p2 = scanner.nextLine();
        System.out.print("Введіть розмір поля (наприклад 3): ");
        int size = Integer.parseInt(scanner.nextLine());
        return new GameConfig(size, p1, p2);
    }
}
