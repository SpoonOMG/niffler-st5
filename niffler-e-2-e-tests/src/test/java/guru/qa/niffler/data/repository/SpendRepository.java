package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;

//здесь будут все методы, которые касаются сервиса спенд и работают со спендами
public interface SpendRepository {
    //тут будет создание и удаление категорий

     CategoryEntity createCategory(CategoryEntity category);
     void removeCategory(CategoryEntity category);

}
