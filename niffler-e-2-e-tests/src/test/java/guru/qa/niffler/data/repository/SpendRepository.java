package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;

//здесь будут все методы, которые касаются сервиса спенд и работают со спендами
public interface SpendRepository {
    //тут будет создание и удаление категорий

     CategoryEntity createCategory(CategoryEntity category);
     CategoryEntity editCategory(CategoryEntity category);
     void removeCategory(CategoryEntity category);

     SpendEntity createSpend (SpendEntity category) ;
     SpendEntity editSpend (SpendEntity category) ;
     void removeSpend (SpendEntity category) ;

}
