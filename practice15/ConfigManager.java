import java.io.*;

public class ConfigManager {
    public static void saveConfig(GameConfig config) {
        try {
            FileWriter writer = new FileWriter("config.txt");
            writer.write(config.fieldSize + "\n");
            writer.write(config.player1Name + "\n");
            writer.write(config.player2Name + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Помилка збереження конфігурації.");
        }
    }

    public static GameConfig loadConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            int fieldSize = Integer.parseInt(reader.readLine());
            String player1 = reader.readLine();
            String player2 = reader.readLine();
            reader.close();
            return new GameConfig(fieldSize, player1, player2);
        } catch (IOException | NumberFormatException e) {
            return new GameConfig(3, "Гравець 1", "Гравець 2");
        }
    }
}

