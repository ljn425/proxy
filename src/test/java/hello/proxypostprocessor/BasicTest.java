package hello.proxypostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@SpringBootTest
public class BasicTest {
    @Test
    void basicConfig() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        A a = applicationContext.getBean("a", A.class);
        a.hello();

        //B는 빈으로 등록되지 않는다.
        Assertions.assertThatThrownBy(() -> applicationContext.getBean("b", B.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);

    }
    @Configuration
    static class BasicConfig {
//        @Bean(name = "beanA")
        @Bean
        public A a() {
            return new A();
        }
    }

    static class A {
        public void hello() {
            log.info("hello A");
        }
    }

    static class B {
        public void hello() {
            log.info("hello B");
        }
    }
}
