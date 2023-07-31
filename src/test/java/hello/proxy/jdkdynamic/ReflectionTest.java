package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ReflectionTest {

    @Test
    public void reflection0() {
        Hello target = new Hello();
        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result1: {}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result1: {}", result2);
        //공통 로직2 종료
    }

    @Test
    public void reflection1() throws Exception {
        //클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //클래스의 callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        //methodCallA와 일치하는 target 인스턴스의 메서드 호출
        Object result1 = methodCallA.invoke(target);
        log.info("result1: {}", result1);

        Object result2 = classHello.getMethod("callB").invoke(target);
        log.info("result2: {}", result2);
    }
    @Test
    public void reflectionMethod() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Class<?> declaringClass = classHello.getDeclaringClass(); // 클래스가 선언된 클래스
        String name = classHello.getName(); // 클래스명
        log.info("name: {}", name);
        log.info("declaringClass: {}", declaringClass);

        String packageName = declaringClass.getName(); // 클래스 패키지명
        String simpleName = declaringClass.getSimpleName(); // 클래스명
        log.info("packageName: {}", packageName);
        log.info("simpleName: {}", simpleName);

        Method[] methods = classHello.getMethods(); // 클래스의 메서드 정보(상속받은 메서드 포함)
        //Arrays.stream(methods).forEach(method -> log.info("method: {}", method));

        Method[] declaredMethods = classHello.getDeclaredMethods(); // 클래스의 메서드 정보(상속받은 메서드 제외)
        Arrays.stream(declaredMethods).forEach(method -> log.info("declaredMethod: {}", method));

        Method methodCallA = classHello.getMethod("callA"); // 클래스의 특정 메서드 정보
        log.info("methodCallA: {}", methodCallA);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }

    @Test
    public void reflection2() throws Exception {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result: {}", result);
    }


}
