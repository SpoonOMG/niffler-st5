package guru.qa.niffler.data.jdbc;

import guru.qa.niffler.data.DataBase;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Поставщик DataSource'ов
    /*
    должны быть синглтоном - поэтому создаем через enum - наиболее простой способ создать синглтоном
     */
public enum DataSourceProvider {
    INSTANCE;
    //нужно создать точку хранения инстансов подключений. На случай, если нам понадобится два раза dataSource для auth.
    // для этого используем map

   private final Map<DataBase, DataSource> store = new ConcurrentHashMap<>(); //если разные потоки будут обращаться -
    // concurrent позволит избежать возможных сайд-эффектов,связанных с многопоточностью

    //создадим метод, который будет принимать на вход БД, который будет искать в мапе  store БД.
    // Если в мапе есть эта бд - она будет ее возвращать, если нет - будет класть бд в мапу
    private DataSource computeDataSource(DataBase db){
       return store.computeIfAbsent(db,key ->{ //если по ключу в мапе store уже есть DataSource, то метод его же и вернет. Если нет - выполнит лямбду
           //с сеттингом нового dataSource с подключениями (сэтим урл, юзера и пасс) и вернет его.
            PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
            pgDataSource.setUrl(db.getJdbcUrl());
            pgDataSource.setUser("postgres");
            pgDataSource.setPassword("secret");
            return pgDataSource;
       });
    }

    public static DataSource dataSource(DataBase db){
        return DataSourceProvider.INSTANCE.computeDataSource(db);
    }

    //в итоге мы можем из любой точки вызывать метод dataSource, подавая на вход БД, и получить либо новое подключение либо вернуть ранее созданное
}

