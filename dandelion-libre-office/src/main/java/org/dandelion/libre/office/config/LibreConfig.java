package org.dandelion.libre.office.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author lx6x
 * @date 2023/12/14
 */
@Configuration
public class LibreConfig {

   /* @Bean
    public DocumentConverter documentConverter(OfficeManager officeManager, DocumentFormatRegistry documentFormatRegistry) {
        return LocalConverter.builder()
                .filterChain(new LibreRefreshFilter())
                .officeManager(officeManager)
                .formatRegistry(documentFormatRegistry)
                .build();
    }*/

}
