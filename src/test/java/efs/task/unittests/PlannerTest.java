package efs.task.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlannerTest {
    private Planner planner;

    @BeforeEach
    void init() {
        this.planner = new Planner();
    }

    @ParameterizedTest(name="activityLevel={0}")
    @EnumSource(ActivityLevel.class)
    void shouldReturnCorrectDailyCaloriesDemand_whenProvidedDifferentActivityLevels(ActivityLevel activityLevel) {
        //given
        User user = TestConstants.TEST_USER;

        //when
        int caloriesDemand = planner.calculateDailyCaloriesDemand(user, activityLevel);

        //then
        assertEquals(TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel), caloriesDemand);
    }

    @Test
    void shouldReturnCalculatedDailyIntake_whenProvidedUser() {
        //given
        User user = TestConstants.TEST_USER;
        DailyIntake expectedIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        //when
        DailyIntake calculatedIntake = planner.calculateDailyIntake(user);

        //then
        assertAll(
                () -> assertEquals(expectedIntake.getCalories(), calculatedIntake.getCalories()),
                () -> assertEquals(expectedIntake.getProtein(), calculatedIntake.getProtein()),
                () -> assertEquals(expectedIntake.getFat(), calculatedIntake.getFat()),
                () -> assertEquals(expectedIntake.getCarbohydrate(), calculatedIntake.getCarbohydrate())
        );
    }
}
