package top.pdev.you.infrastructure.config.bean;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * 日期格式化配置
 * Created in 2022/8/20 22:41
 *
 * @author Peter1303
 */
@Configuration
@JsonComponent
public class DateFormatConfig {
    @Value("${spring.jackson.datetime-format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimePattern;

    @Value("${spring.jackson.date-format:yyyy-MM-dd}")
    private String datePattern;

    /**
     * date 类型全局时间格式化
     *
     * @return {@link Jackson2ObjectMapperBuilderCustomizer}
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
        return builder -> {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat(dateTimePattern);
            df.setTimeZone(tz);
            builder.failOnEmptyBeans(false)
                    .failOnUnknownProperties(false)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .dateFormat(df);
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimePattern)));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(datePattern)));
            builder.serializerByType(DateTime.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern(dateTimePattern)));
            builder.deserializerByType(DateTime.class,
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateTimePattern)));
        };
    }
}

