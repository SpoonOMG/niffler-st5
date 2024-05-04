package guru.qa.niffler.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
//В ENUM можно хранить значения в полях, и чтоб это работало - нужно объявить конструктор
@RequiredArgsConstructor
@Getter
public enum DataBase {
    AUTH("jdbc:postgresql://localhost:5432/niffler-auth"),
    CURRENCY("jdbc:postgresql://localhost:5432/niffler-currency"),
    SPEND("jdbc:postgresql://localhost:5432/niffler-spend"),
    USERDATA("jdbc:postgresql://localhost:5432/niffler-userdata");

    private final String jdbcUrl;
    /*
В дальнейшем класс, который будет делать соединение (ленивое соединение), должен иметь возможность:
- Создать соединение и вернуть его
- При повторном соединении - не создает, а возвращается тот, что был ранее
- Создается новое соединение для другой БД
     */

}
