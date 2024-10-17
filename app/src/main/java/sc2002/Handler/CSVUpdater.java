import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUpdater {
    private String filePath;
    private String delimiter;

    public CSVUpdater(String filePath) {
        this(filePath, ",");
    }

    public CSVUpdater(String filePath, String delimiter) {
        this.filePath = filePath;
        this.delimiter = delimiter;
    }

    // Read the CSV file into a List of String arrays (rows)
    public List<String[]> readAll() throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(delimiter));
            }
        }
        return data;
    }

    // Update a specific row in the CSV file
    public void updateRow(int rowIndex, String[] newRow) throws IOException {
        List<String[]> data = readAll();  // Read the current data
        if (rowIndex < 0 || rowIndex >= data.size()) {
            throw new IndexOutOfBoundsException("Invalid row index.");
        }
        data.set(rowIndex, newRow);  // Replace the row with the updated row
        writeAll(data);  // Write the updated data back to the file
    }

    // Update a specific field in the CSV file
    public void updateField(int rowIndex, int columnIndex, String newValue) throws IOException {
        List<String[]> data = readAll();  // Read the current data
        if (rowIndex < 0 || rowIndex >= data.size()) {
            throw new IndexOutOfBoundsException("Invalid row index.");
        }
        String[] row = data.get(rowIndex);
        if (columnIndex < 0 || columnIndex >= row.length) {
            throw new IndexOutOfBoundsException("Invalid column index.");
        }
        row[columnIndex] = newValue;  // Update the specific field
        writeAll(data);  // Write the updated data back to the file
    }

    // Write the updated data back to the CSV file
    private void writeAll(List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                writer.write(String.join(delimiter, row));
                writer.newLine();
            }
        }
    }

    // Add a new row to the CSV file
    public void addRow(String[] newRow) throws IOException {
        List<String[]> data = readAll();  // Read the current data
        data.add(newRow);  // Add the new row
        writeAll(data);  // Write the updated data back to the file
    }

    // Add a new column to all rows in the CSV file
    public void addColumn(String newValue) throws IOException {
        List<String[]> data = readAll();  // Read the current data
        for (String[] row : data) {
            String[] newRow = new String[row.length + 1];
            System.arraycopy(row, 0, newRow, 0, row.length);  // Copy the old row
            newRow[newRow.length - 1] = newValue;  // Set the new column value
            data.set(data.indexOf(row), newRow);  // Replace the old row with the new one
        }
        writeAll(data);  // Write the updated data back to the file
    }
}
