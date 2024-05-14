package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.CategoryDB;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JdbcExtensionCategory extends AbstractCategoryExtension {

    private final SpendRepository spendRepository = new SpendRepositoryJdbc();
    @Override
    protected CategoryJson createCategory(ExtensionContext extensionContext, CategoryDB category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategory(category.category());
        categoryEntity.setUsername(category.username());
        return CategoryJson.fromEntity(categoryEntity);
    }

    @Override
    protected void removeCategory(CategoryJson category) {
        CategoryJson categoryJson = new CategoryJson(category.id(), category.category(), category.username());
        spendRepository.removeCategory(CategoryEntity.fromJson(categoryJson));
    }
}
