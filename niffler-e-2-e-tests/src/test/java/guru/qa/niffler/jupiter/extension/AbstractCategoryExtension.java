package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.CategoryDB;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public abstract class AbstractCategoryExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtensionDB.class);

    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                CategoryDB.class
        ).ifPresent(
                category -> {
                    extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(),createCategory(extensionContext,category));

                });
    }

    public void afterEach(ExtensionContext context) throws Exception {
// Достать из контекста
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
// Это абстрактный метод
        removeCategory(category);
    }
    
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
                .getParameter()
                .getType()
                .isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId());
    }

    protected abstract CategoryJson createCategory (ExtensionContext extensionContext, CategoryDB category);
    protected abstract void removeCategory (CategoryJson category);

}