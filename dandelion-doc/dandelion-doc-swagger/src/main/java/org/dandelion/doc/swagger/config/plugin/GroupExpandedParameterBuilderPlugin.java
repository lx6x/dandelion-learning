package org.dandelion.doc.swagger.config.plugin;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

import java.lang.annotation.Annotation;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/25 13:35
 */
public class GroupExpandedParameterBuilderPlugin implements ExpandedParameterBuilderPlugin {
    @Override
    public void apply(ParameterExpansionContext context) {
        // 附加额外配置
        GroupDocumentationType documentationType = (GroupDocumentationType) context.getDocumentationType();
        ParameterExpansionContextAnnotation peca = new ParameterExpansionContextAnnotation(context);
        ParameterBuilderAllowableValues pbav = new ParameterBuilderAllowableValues(context.getParameterBuilder());

        GroupModelPropertyBuilderPlugin.apply(peca, ParameterExpansionContextAnnotation::getAnnotation,
                pbav, ParameterBuilderAllowableValues::hidden, ParameterBuilderAllowableValues::required,
                ParameterBuilderAllowableValues::allowableValues, ParameterBuilderAllowableValues::pattern, documentationType.groups);
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType instanceof GroupDocumentationType;
    }

    public static class ParameterExpansionContextAnnotation {
        public ParameterExpansionContext context;

        public ParameterExpansionContextAnnotation(ParameterExpansionContext context) {
            this.context = context;
        }

        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return context.findAnnotation(annotationType).orNull();
        }
    }

    public static class ParameterBuilderAllowableValues {
        public ParameterBuilder parameterBuilder;

        public ParameterBuilderAllowableValues(ParameterBuilder parameterBuilder) {
            this.parameterBuilder = parameterBuilder;
        }

        public void hidden(boolean hidden) {
            parameterBuilder.hidden(hidden);
        }

        public void required(boolean required) {
            parameterBuilder.required(required);
        }

        public void allowableValues(AllowableValues allowableValues) {
            parameterBuilder.allowableValues(allowableValues);
        }

        public void pattern(String pattern) {
            parameterBuilder.pattern(pattern);
        }
    }
}
