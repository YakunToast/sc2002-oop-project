import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class csv_reader {
    private String delimiter;
    private BufferedReader reader;

    public csv_reader(String filePath) throws FileNotFoundException {
        this(filePath, ",");
    }

    public csv_reader(String filePath, String delimiter) throws FileNotFoundException {
        this.delimiter = delimiter;
        this.reader = new BufferedReader(new FileReader(filePath));
    }

    public List<String[]> readAll() throws IOException {
        List<String[]> result = new ArrayList<>();
        String[] line;
        while ((line = readNext()) != null) {
            result.add(line);
        }
        return result;
    }

    public String[] readNext() throws IOException {
        String line = reader.readLine();
        if (line != null) {
            return parseLine(line);
        } else {
            return null;
        }
    }

    private String[] parseLine(String line) throws IOException {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        char[] chars = line.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '"') {
                if (inQuotes && i + 1 < chars.length && chars[i + 1] == '"') {
                    // Handle escaped double quote within quoted string
                    sb.append(c);
                    i++; // Skip the next quote
                } else {
                    inQuotes = !inQuotes; // Toggle inQuotes flag
                }
            } else if (c == delimiter.charAt(0) && !inQuotes) {
                tokens.add(sb.toString());
                sb.setLength(0); // Reset the StringBuilder
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString()); // Add the last token
        return tokens.toArray(new String[0]);
    }

    public void close() throws IOException {
        reader.close();
    }
}
