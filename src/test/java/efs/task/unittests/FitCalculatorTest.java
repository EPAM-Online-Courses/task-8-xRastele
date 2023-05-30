package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        //given
        double weight = 0.0;
        double height = 1.72;

        //when, then
        assertThrows(IllegalArgumentException.class, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "weight={0}")
    @ValueSource(doubles = {88.8, 90.1, 93.7})
    void shouldReturnTrue_whenProvidedDifferentWeights(double weight) {
        //given
        double height = 1.85;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvSource(value = {"60.5, 1.72",
                        "63.3, 1.73",
                        "66.6, 1.74"})
    public void shouldReturnFalse_whenProvidedHeightsAndWeights(double weight, double height) {
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenProvidedValuesFromCsvFile(double weight, double height) {
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI_whenProvidedUsersListIsNotEmpty() {
        //given
        User user = new User(1.79, 97.3);

        //when
        User worstBMIUser = FitCalculator.findUserWithTheWorstBMI(TestConstants.TEST_USERS_LIST);

        //then
        assertAll(
                () -> assertEquals(user.getWeight(), worstBMIUser.getWeight()),
                () -> assertEquals(user.getHeight(), worstBMIUser.getHeight())
        );
    }

    @Test
    public void shouldReturnNull_whenUsersListIsEmpty() {
        //given
        List<User> emptyUsersList = new ArrayList<>();

        //when
        User worstBMIUser = FitCalculator.findUserWithTheWorstBMI(emptyUsersList);

        //then
        assertNull(worstBMIUser);
    }

    @Test
    void shouldReturnCorrectBMIScore_whenProvidedTestUsersList() {
        //given
        List<User> usersList = TestConstants.TEST_USERS_LIST;
        double[] correctBMIScoreList = TestConstants.TEST_USERS_BMI_SCORE;

        //when
        double[] actualBMIScoreList = FitCalculator.calculateBMIScore(usersList);

        //then
        assertArrayEquals(correctBMIScoreList, actualBMIScoreList, 0.01);
    }
}