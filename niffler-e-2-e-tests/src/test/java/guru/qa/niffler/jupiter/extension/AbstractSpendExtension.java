package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.SpendDB;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback {

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

    protected abstract SpendJson createSpend (ExtensionContext extensionContext, SpendDB spend);
    protected abstract void removeSpend (SpendJson spend);

}