import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Summary {
    enum Status { PASSED, FAILED, SKIPPED }

    record TestResult(String name, Status status, long durationMs) { }

    public static void printSummary(List<TestResult> run) {
//T1
        Map<Status, List<String>> byStatus = run.stream()
                .collect(Collectors.groupingBy(
                        TestResult::status,
                        Collectors.mapping(TestResult::name, Collectors.toList())
                ));//  группируем по статусу и собираем мапу ключ статус, значение имена
//T2
        List<String> top3 = run.stream()
                .sorted(Comparator.comparingLong(TestResult::durationMs).reversed())// сортируем по времени, разворачиваем чтобы долгие были превыми
                .limit(3)// ограничиваем до первых трех
                .map(TestResult::name)// достаем имена
                .collect(Collectors.toList());// собрием в лист
//T3
        double avgPassed = run.stream()// среднее время
                .filter(r -> r.status() == Status.PASSED)// фильтруем по статусу, где прошел
                .collect(Collectors.averagingLong(TestResult::durationMs));// собираем, сам считает среднее по времени




        System.out.println("=== Сводка прогона ===");
        Arrays.stream(Status.values())
                .filter(byStatus::containsKey)
                .forEach(status -> {
                    List<String> names = byStatus.get(status);
                    System.out.println(status + " (" + names.size() + "): " + String.join(", ", names));
                });
        System.out.println("Топ-3 долгих: " + String.join(", ", top3));
        System.out.println("Среднее время прошедших: " + avgPassed + " ms");
    }
    public static void main(String[] args) {
        List<TestResult> run = List.of(
                new TestResult("LoginTest", Status.PASSED, 120),
                new TestResult("CreateTaskTest", Status.PASSED, 940),
                new TestResult("UpdateStatusTest", Status.FAILED, 2100),
                new TestResult("CommentTest", Status.PASSED, 380),
                new TestResult("DeleteTaskTest", Status.FAILED, 760),
                new TestResult("StatsTest", Status.SKIPPED, 0),
                new TestResult("LogoutTest", Status.PASSED, 90)
        );
        printSummary(run);


    }


//    === Сводка прогона ===
//    PASSED (4): LoginTest, CreateTaskTest, CommentTest, LogoutTest
//    FAILED (2): UpdateStatusTest, DeleteTaskTest
//    SKIPPED (1): StatsTest
//    Топ-3 долгих: UpdateStatusTest, DeleteTaskTest, CreateTaskTest
//    Среднее время прошедших: 382.5 ms
}

