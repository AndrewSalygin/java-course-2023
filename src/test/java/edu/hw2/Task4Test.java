package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.Task4.Utils.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Вывод последнего метода из stackTrace")
    void mainTest() {
        CallingInfo callingInfo = callingInfo();
        assertThat(callingInfo).isEqualTo(new CallingInfo("edu.hw2.Task4Test", "mainTest"));
    }
}
