package utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility class to read files.
 */
public class FileReader {

  public static Stream<String> lines(String fileName) {
    try {
      return Files.lines(getFileFromResource(fileName));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public static List<String> readAlllines(String fileName) {
    try {
      return Files.readAllLines(getFileFromResource(fileName));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static Path getFileFromResource(String fileName) throws URISyntaxException {
    URL resource = ClassLoader.getSystemResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("File not found! " + fileName);
    } else {
      return Paths.get(resource.toURI());
    }

  }

}
