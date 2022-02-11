package dev.gregorius.library.json.reflect.setup;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import dev.gregorius.library.json.reflect.JsonReflect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    @LocalServerPort
    private int localServerPort;

    protected TestLogAppender testLogAppender;

    @BeforeAll
    void beforeAll() {
        JsonReflect.setBaseUrl(String.format("http://localhost:%d", localServerPort));
        setupLogtesting();
    }

    @BeforeEach
    void setUp() {
        testLogAppender.reset();
    }

    private void setupLogtesting() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        testLogAppender = new TestLogAppender();
        testLogAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.addAppender(testLogAppender);
        testLogAppender.start();
    }

    protected static class TestLogAppender extends ListAppender<ILoggingEvent> {

        public void reset() {
            this.list.clear();
        }

        public boolean contains(final Level logLevel, final String searchString) {
            return this.list.stream()
                .anyMatch(log -> log.getLevel().equals(logLevel) && log.toString().contains(searchString));
        }

        public boolean containsOnce(final Level logLevel, final String searchString) {
            return containsMultiple(logLevel, searchString, 1);
        }

        public boolean containsMultiple(final Level logLevel, final String searchString, final int matchCount) {
            return this.list.stream()
                .filter(log -> log.getLevel().equals(logLevel) && log.toString().contains(searchString))
                .count() == matchCount;
        }
    }
}
