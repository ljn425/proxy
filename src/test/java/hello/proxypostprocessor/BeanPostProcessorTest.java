package hello.proxypostprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BeanPostProcessorTest {

    @Test
    @DisplayName("포스트프로세서")
    public void postProcessor() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        Assertions.assertThatThrownBy(() -> applicationContext.getBean(A.class))
                .isInstanceOf(org.springframework.beans.factory.NoSuchBeanDefinitionException.class);;
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBPostProcessor helloPostProcessor() {
            return new AToBPostProcessor();
        }

        @Bean(name ="beanC")
        public C c() {
            return new C();
        }


    }

    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    static class C {
        public void helloC() {
            log.info("hello C");
        }
    }

    static class AToBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }

}
