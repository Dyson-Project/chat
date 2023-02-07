package org.dyson.chat.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource

@Configuration
open class DatabaseConfig {
//    @Bean
//    open fun dataSource(dataSourceProperties: DataSourceProperties): DriverManagerDataSource {
//        return dataSourceProperties.initializeDataSourceBuilder().type(DriverManagerDataSource::class.java).build();
//    }
}
