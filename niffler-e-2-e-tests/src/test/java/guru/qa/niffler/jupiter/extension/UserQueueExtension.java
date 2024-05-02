package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.model.UserJson.simpleUser;

public class UserQueueExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(User.Selector.WITH_FRIENDS, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("spoonomg", "12345"), simpleUser("spoon", "12345")))
        );
        USERS.put(User.Selector.INVITATION_RECEIVED, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("spoonlol", "12345"), simpleUser("spoonwtb", "12345")))
        );
        USERS.put(User.Selector.INVITATION_SENT, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("spoonsht", "12345"), simpleUser("spoonwtf", "12345")))
        );

    }

    @Override
    public void beforeEach(ExtensionContext context) {
        // Получение тестового метода
        Method testMethod = context.getRequiredTestMethod();
        // Получение @BeforeEach-методов
        List<Method> beforeEachMethods = Arrays.stream(
                context.getRequiredTestClass().getDeclaredMethods()
        ).filter(i -> i.isAnnotationPresent(BeforeEach.class)).toList();

        // Общий список методов, которые необходимо обработать
        List<Method> methods = new ArrayList<>();
        methods.add(testMethod);
        methods.addAll(beforeEachMethods);
        // Общий список параметров, которые мы хотим обработать
        List<Parameter> parameters = methods.stream()
                .flatMap(m -> Arrays.stream(m.getParameters()))
                .filter(p -> p.isAnnotationPresent(User.class))
                .toList();
        // Объект, где хранятся тип пользователя и сам пользователь. Далее будет сохранен в store
        Map<User.Selector, UserJson> users = new HashMap<>();
        // Обрабатываем каждый из полученных параметров
        for (Parameter parameter : parameters) {
            User.Selector selector = parameter.getAnnotation(User.class).selector();
            // Данный тип пользователя обрабатывался ранее
            if (users.containsKey(selector)) {
                continue;
            }
            UserJson userForTest = null;

            // Получение очереди с необходимым типом пользователей
            Queue<UserJson> queue = USERS.get(selector);

            // "Умное ожидание" пользователя
            while (userForTest == null) {
                userForTest = queue.poll();
            }
            // Добавляем полученного из очереди пользователя в наш объект
            users.put(selector, userForTest);
        }
        // Сохраняем данные о пользователях в store
        context.getStore(NAMESPACE).put(context.getUniqueId(), users);
    }


    @Override
    public void afterEach(ExtensionContext extensionContext) {
        // Получаем мапу из хранилища
        Map<User.Selector, UserJson> users = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        for (Map.Entry<User.Selector, UserJson> user : users.entrySet()) {
            // Возвращаем обратно пользователя в соотвествующую очередь
            USERS.get(user.getKey()).add(user.getValue());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        User.Selector selector = parameterContext.getParameter().getAnnotation(User.class).selector();
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(selector);
    }
}
