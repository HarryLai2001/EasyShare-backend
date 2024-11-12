package com.easyshare.config;

import com.easyshare.converter.DateToLocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongodbConfig {
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                                       MappingMongoConverter mappingMongoConverter) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        return new MongoTemplate(mongoDatabaseFactory, mappingMongoConverter);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory mongoDatabaseFactory,
                                                       MongoMappingContext mongoMappingContext,
                                                       MongoCustomConversions mongoCustomConversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); //去掉默认添加的_class字段
        mappingConverter.setCustomConversions(mongoCustomConversions);
        return mappingConverter;
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List list = new ArrayList();
        // mongodb本身事件类型为Date，需添加转为LocalDateTime的转换器
        list.add(new DateToLocalDateTimeConverter());
        return new MongoCustomConversions(list);
    }
}
