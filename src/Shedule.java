import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Shedule {
    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    record WorkSheet(String name, List<DayOfWeek> workDays) { }

    void main() {
        var denSheet = new WorkSheet("Денис", List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY));
        var benSheet = new WorkSheet("Бен", List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY));
        var lisSheet = new WorkSheet("Лиза", List.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
        var sheets = List.of(denSheet, benSheet, lisSheet);
        // TODO: вывести на экран дни, в которые работало более 1 человека

        sheets.stream()
                .flatMap(sheet -> sheet.workDays().stream())      //общий котел с днями недели
                .collect(Collectors.groupingBy(day ->day, Collectors.counting()))// группируем по дням в мапу
                .entrySet().stream()                                        //делмем мапу где день значение, это один объект
                .filter(entry -> entry.getValue()>1)  // фильтруем по значению больше 1
                // тоесть дни которых больше всего, тоесть дни в которых больше 1 сотрудника
                .map(entry -> entry.getKey())         //достаем дни из ентри
                .sorted()
                .forEach(day->System.out.println(day));          // проходимся по мапе и выводим только названия дней

    }


//    WEDNESDAY
//    THURSDAY


}
