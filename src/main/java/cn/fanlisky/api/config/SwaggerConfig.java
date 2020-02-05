package cn.fanlisky.api.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * swagger配置类
 *
 * @author Gary
 * @since 2019-03-05 19:06
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private String host;

    @Value(value = "${swagger.master.address:127.0.0.1}")
    private String webHost;

    @Value(value = "${swagger.local.address:127.0.0.1}")
    private String localHost;

    @PostConstruct
    private void setHost() {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("application.yml"));
        Properties properties = yamlPropertiesFactoryBean.getObject();
        String active = Objects.requireNonNull(properties).getProperty("spring.profiles.active");
        if ("dev".equals(active)) {
            host = localHost;
        } else {
            host = webHost;
        }
    }

    @Bean
    public Docket createRestApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter parameter = parameterBuilder.parameterType("header")
                .name("token")
                .description("请输入您的token")
                .modelRef(new ModelRef("string"))
                .required(false)
                .build();
        ArrayList<Parameter> parameters = Lists.newArrayList(parameter);
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.fanlisky.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("QR-API测试")
                .description("QR-API测试，https://api.fanlisky.cn")
                .termsOfServiceUrl("https://api.fanlisky.cn")
                .version("1.0").contact(new Contact("Gary", "www.niurenqu.com", "cnahzjl@gmail.com"))
                .build();
    }

}
