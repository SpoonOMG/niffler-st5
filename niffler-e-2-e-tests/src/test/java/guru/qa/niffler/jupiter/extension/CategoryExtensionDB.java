package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.CategoryDB;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtensionDB implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtensionDB.class);

    private final SpendRepository spendRepository = new SpendRepositoryJdbc();
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                CategoryDB.class
        ).ifPresent(
                cat -> {

                        CategoryEntity category = new CategoryEntity();
                        category.setCategory(cat.category()); //забираем из аннотации
                        category.setUsername(cat.username()); //забираем из аннотации

                        category = spendRepository.createCategory(category);
                        extensionContext.getStore(NAMESPACE).put(
                                extensionContext.getUniqueId(), CategoryJson.fromEntity(category)
                        );

                }
        );
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
       CategoryJson categoryJson = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        spendRepository.removeCategory(CategoryEntity.fromJson(categoryJson));
    }
}
