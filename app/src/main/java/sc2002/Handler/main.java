import java.util.*;
import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String[] args) {
        try {
            csv_reader reader = new csv_reader("patient_list.csv");
            List<String[]> data = reader.readAll();

            for (String[] row : data) {
                for (String field : row) {
                    System.out.print(field + " | ");
                }
                System.out.println();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
