package com.soez.mtc.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // url : sangta.ch


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mtc Spring Boot REST API")
                .version("1.0.0")
                .description("Swagger ㅁㅌㅊ?")
                .build();
    }

    public Docket getDocket(String groupName, Predicate<String> predicate) {
//		List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
//		responseMessages.add(new ResponseMessageBuilder().code(200).message("OK !!!").build());
//		responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생 !!!").responseModel(new ModelRef("Error")).build());
//		responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다 !!!").build());
        return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.soez.mtc")).paths(predicate)
                .apis(RequestHandlerSelectors.any()).build();
//				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,responseMessages);
    }

    @Bean
    public Docket userApi() {
        return getDocket("회원", Predicates.or(PathSelectors.regex("/user.*")));
    }

    @Bean
    public Docket articleApi() {
        return getDocket("게시물", Predicates.or(PathSelectors.regex("/article.*")));
    }

    @Bean
    public Docket commentApi() {
        return getDocket("댓글", Predicates.or(PathSelectors.regex("/comment.*")));
    }
    @Bean
    public Docket replyApi() {
        return getDocket("답글", Predicates.or(PathSelectors.regex("/reply.*")));
    }

    @Bean
    public Docket reportApi(){
        return getDocket("신고", Predicates.or(PathSelectors.regex("/report.*")));
    }

    @Bean
    public Docket relationApi(){
        return getDocket("팔로우, 차단", Predicates.or(PathSelectors.regex("/relation.*")));
    } @Bean
    public Docket alarmApi(){
        return getDocket("알람", Predicates.or(PathSelectors.regex("/alarm.*")));
    }

    @Bean
    public Docket settingApi(){
        return getDocket("세팅", Predicates.or(PathSelectors.regex("/setting.*")));
    }
}
