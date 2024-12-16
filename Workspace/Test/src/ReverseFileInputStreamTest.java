
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


class ReverseFileInputStreamTest {

    @Test
    void testRead() throws IOException {
        // Create a temporary file with some content
        File tempFile = File.createTempFile("testFile", ".txt");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Test 1\nFile 2\nLine 3\n");
        }

        // Create an instancReverint bye of ReverseFileInputStream
        try (ReverseFileInputStream reverseStream = new ReverseFileInputStream(tempFile)) {
            // Read the file in reverse
            StringBuilder content = new StringBuilder();
            int byteRead;
            while ((byteRead = reverseStream.read()) != -1) {
                content.append((char) byteRead);
            }

            // Verify the content is read in reverse
            assertEquals("Line 3\nFile 2\nTest 1\n", content.toString());
        }
    }
}