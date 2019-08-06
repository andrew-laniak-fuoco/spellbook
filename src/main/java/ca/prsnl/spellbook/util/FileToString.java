package ca.prsnl.spellbook.util;

import java.io.*;

public class FileToString {

    public static String readFile(InputStream file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        return read(reader);
    }

    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return read(reader);
    }

    private static String read(BufferedReader reader) throws IOException {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
