package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.jupiter.annotation.SpendDB;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtensionDB.class);

    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                SpendDB.class
        ).ifPresent(
                spend -> {
                    extensionContext.getStore(NAMESPACE).put(extensionContext.getUniqueId(),createSpend(extensionContext,spend));

                });
    }

    public void afterEach(ExtensionContext context) throws Exception {
// Достать из контекста
        SpendJson spend = context.getStore(NAMESPACE).get(context.getUniqueId(),SpendJson.class);
// Это абстрактный метод
        removeSpend(spend);
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

    protected abstract SpendJson createSpend (ExtensionContext extensionContext, SpendDB spend);
    protected abstract void removeSpend (SpendJson spend);

}