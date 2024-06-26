package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.DataBase;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.jdbc.DataSourceProvider;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SpendRepositoryJdbc implements SpendRepository{
    //создаем подключение к spendDataSource, описанный в DataSourceProvider (чтобы при каждом запросе не создавался новый объект)
    private static final DataSource spendDataSource = DataSourceProvider.dataSource(DataBase.SPEND);

    //connection создается внутри метода, чтобы гарантированно обособить переменную внутри метода для
    // потокобезопасности объекта connection


    // метод создает категорию  в базе данных через sql-запрос insert into... на вход метод принимает название категории
    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        try(Connection conn = spendDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO \"category\" (category, username) VALUES (?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS//возвращает id после выполнения инсерта
            )) {
            //наполняем "вопросики" параметрами
ps.setString(1,category.getCategory()); //1 - первый параметр (первый вопросик)
ps.setString(2,category.getUsername());//2 - второй параметр (второй вопросик)
ps.executeUpdate();
            // возвращаем результат запроса
            UUID generatedId = null;
            try (ResultSet resultSet = ps.getGeneratedKeys()){
                //"коробочка" с результатами запроса
                //нужно обратиться "курсором" к определенной строке в resultSet, это делается через метод next()
                // next() делает два действия: 1 - проверяет, есть ли хоть одна строка 2 если есть - next сдвинет курсор к ближайшей строке с данными
              //  if (resultSet.next()) - если нужна одна строка для дальнейшей работы
             // while (resultSet.next()) - для работы с несколькими строками
                if (resultSet.next()){
        generatedId = UUID.fromString(resultSet.getString("id"));
                }else{
                    throw new IllegalStateException("Can't access to id");
                }
            }
            category.setId(generatedId);
            return category;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCategory(CategoryEntity category) {
        try(Connection conn = spendDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM \"category\" WHERE id = ?"
            )){
            //наполняем "вопросики" параметрами
            ps.setObject(1,category.getId()); //1 - первый параметр (первый вопросик)
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
