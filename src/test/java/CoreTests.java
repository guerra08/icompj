import com.guerra08.Core;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Objects;
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
        String originalPath = Objects.requireNonNull(getClass().getClassLoader().getResource("sample.jpg")).getPath();

        assertDoesNotThrow(() -> Core.compress(originalPath, 0.8f));
    }

}
