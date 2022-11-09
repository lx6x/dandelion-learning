package org.dandelion.doc.swagger.config.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 13:53
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    private List<String> enumStringList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enumStringList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return enumStringList.contains(value);
    }
}
