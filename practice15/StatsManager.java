import java.io.*;

public class StatsManager {
    public static void saveStat(GameStat stat) {
        try {
            FileWriter writer = new FileWriter("stats.txt", true);
            writer.write(stat.winner + ";" + stat.dateTime + ";" + stat.symbol + ";" + stat.fieldSize + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Помилка збереження статистики.");
        }
    }

    public static void showStats() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("stats.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    System.out.println("Переможець: " + parts[0]);
                    System.out.println("Дата: " + parts[1]);
                    System.out.println("Грав за: " + parts[2]);
                    System.out.println("Розмір поля: " + parts[3]);
                    System.out.println("-----------");
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Статистика відсутня.");
        }
    }
}
