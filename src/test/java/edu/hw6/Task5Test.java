package edu.hw6;

import edu.hw6.Task5.HackerNews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task5Test {
    @Test
    @DisplayName("Тест из примера")
    void exampleTest() {
        HackerNews hackerNews = new HackerNews();
        assertEquals("JDK 21 Release Notes", hackerNews.news(37570037));
    }

    @Test
    @DisplayName("Статьи не существует")
    void articleNotExistTest() {
        HackerNews hackerNews = new HackerNews();
        Throwable thrown = catchThrowable(() -> hackerNews.news(199999999999L));
        assertThat(thrown).hasMessage("Вернулся некорректный ответ с сервера.");
    }
}
