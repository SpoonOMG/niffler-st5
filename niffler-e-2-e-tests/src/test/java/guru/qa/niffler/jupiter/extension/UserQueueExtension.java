package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.model.UserJson.*;

// Любой тест проходит через него
public class UserQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static final Queue<UserJson> WITH_FRIENDS_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> INVITATIONS_SENT_QUEUE = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> INVITATION_RECEIVED_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        WITH_FRIENDS_QUEUE.add(simpleUser("spoonomg", "12345"));
        WITH_FRIENDS_QUEUE.add(simpleUser("spoon", "12345"));

        INVITATIONS_SENT_QUEUE.add(simpleUser("spoonwtf", "12345"));
        INVITATIONS_SENT_QUEUE.add(simpleUser("spoonsht", "12345"));

        INVITATION_RECEIVED_QUEUE.add(simpleUser("spoonlol", "12345"));
        INVITATION_RECEIVED_QUEUE.add(simpleUser("spoonwtb", "12345"));
    }


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Parameter[] testParameters = context.getRequiredTestMethod().getParameters();
        for (Parameter parameter : testParameters) {
            User desiredUser = parameter.getAnnotation(User.class);
            if (desiredUser != null) {
                User.Selector selector = desiredUser.selector();
                UserJson userForTest = null;
                while (userForTest == null) {
                    switch (selector) {
                        case WITH_FRIENDS -> userForTest = WITH_FRIENDS_QUEUE.poll();
                        case INVITATION_SENT -> userForTest = INVITATIONS_SENT_QUEUE.poll();
                        case INVITATION_RECEIVED -> userForTest = INVITATION_RECEIVED_QUEUE.poll();
                    }
                }
                context.getStore(NAMESPACE).put(context.getUniqueId(), Map.of(selector, userForTest));
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {


        Map<User.Selector, UserJson> users = (Map<User.Selector, UserJson>) context.getStore(NAMESPACE).get(context.getUniqueId());
        User.Selector selector = users.keySet().iterator().next();
        switch (selector) {
            case WITH_FRIENDS -> WITH_FRIENDS_QUEUE.add(users.get(selector));
            case INVITATION_SENT -> INVITATIONS_SENT_QUEUE.add(users.get(selector));
            case INVITATION_RECEIVED -> INVITATION_RECEIVED_QUEUE.add(users.get(selector));
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
