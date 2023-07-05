import com.guerra08.Core;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CoreTests {

    @Test
    void hasValidFormatPredicate_returns_default_predicate() {
        var predicate = Core.hasValidFormatPredicate();

        var validPath = Paths.get("test.png");
        var invalidPath = Paths.get("test.txt");

        assertTrue(predicate.test(validPath));
        assertFalse(predicate.test(invalidPath));
    }

    @Test
    void hasValidFormatPredicate_returns_custom_predicate() {
        var predicate = Core.hasValidFormatPredicate(Set.of("gif"));

        var validPath = Paths.get("test.gif");
        var invalidPath = Paths.get("test.png");

        assertTrue(predicate.test(validPath));
        assertFalse(predicate.test(invalidPath));
    }

    @Test
    void compress_shouldCompressImage() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        String originalFilePath = absolutePath + File.separator + "sample.jpg";

        Core.compress(originalFilePath, 0.8f);

        File original = Paths.get(originalFilePath).toFile();
        File compressed = Paths.get(absolutePath, File.separator, "sample.comp.jpg").toFile();

        assertTrue(original.length() > compressed.length());

        cleanup(compressed);
    }

    private void cleanup(File compressed) {
        if (compressed.exists()) {
            compressed.delete();
        }
    }

}
