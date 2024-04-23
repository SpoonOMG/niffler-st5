package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.GenerateCategoryApi;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.Spend;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.UUID;

public class GenerateCategoryExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(GenerateCategoryExtension.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        GenerateCategoryApi generateCategoryApi = retrofit.create(GenerateCategoryApi.class);

        AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                GenerateCategory.class).ifPresent(generateCategory -> {
            CategoryJson categoryJson = new CategoryJson(
                    UUID.randomUUID(),
                    generateCategory.category(),
                    generateCategory.username()
            );
            try{
                CategoryJson result = generateCategoryApi.createCategory(categoryJson).execute().body();
                context.getStore(NAMESPACE).put("category", result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get("category");
    }
}
