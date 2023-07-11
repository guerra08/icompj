import com.guerra08.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilsTests {

    @Test
    void getPaths_return_list_with_single_path() {
        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("sample.jpg")).getPath();
        try {
            var result = FileUtils.getPaths(path);
            assertEquals(1, result.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getNameOfFile_returns_name_of_file() {
        var pathString = "custom/folder/file.txt";

        var result = FileUtils.getNameOfFile(pathString);

        assertEquals("custom/folder/file", result);
    }

    @Test
    void getExtensionOfFile_returns_extension_of_file() {
        var pathString = "custom/folder/file.tmp.txt";

        var result = FileUtils.getExtensionOfFile(pathString);

        assertEquals("txt", result);
    }

}
