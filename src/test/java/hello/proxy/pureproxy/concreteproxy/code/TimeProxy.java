package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic{
    private final ConcreteLogic realLogic;

    public TimeProxy(ConcreteLogic realLogic) {
        this.realLogic = realLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long start = System.currentTimeMillis();

        String result = realLogic.operation();

        long end = System.currentTimeMillis();
        log.info("TimeDecorator 실행시간: {}", end - start);
        return result;
    }
}
