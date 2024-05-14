package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface SpendDB {
     String username();
     CurrencyValues currency();
     String spendDate();
     double amount();
     String description();
     CategoryEntity category();
}
