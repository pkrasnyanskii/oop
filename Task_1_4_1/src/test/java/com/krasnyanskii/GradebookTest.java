package java.com.krasnyanskii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class GradebookTest {
    Gradebook myBook;

    @BeforeEach
    void init() {
        myBook = new Gradebook(
                "Краснянский",
                "Пётр",
                "Михайлович");
        addGrade_test();
    }

    @Test
    void person_toString_test() {
        Assertions.assertEquals(
                "Краснянский Пётр Михайлович",
                myBook.getStudent().toString());
    }

    @Test
    void avgGrades_test() {
        Assertions.assertEquals(
                (5 + 5 + 5 + 4) / 4d,
                myBook.avgGradesAll());

        Gradebook emptyBook = new Gradebook("К", "П", "М");
        Assertions.assertEquals(
                0.0,
                emptyBook.avgGradesAll());
    }

    @Test
    void currentSemester_test() {
        Assertions.assertEquals(
                1,
                myBook.currentSemester());

        addGrade_test1();
        Assertions.assertEquals(
                2,
                myBook.currentSemester()
        );
    }
}