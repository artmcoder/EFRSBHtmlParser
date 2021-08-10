package ru.yakunin.efrsbhtmlparser.parser;

import org.springframework.stereotype.Component;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.repository.ArbitrManagerRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArbitrManagerParser {
    private final ArbitrManagerRepository arbitrManagerRepository;

    public ArbitrManagerParser(ArbitrManagerRepository arbitrManagerRepository) {
        this.arbitrManagerRepository = arbitrManagerRepository;
    }



    public void parserArbitrManagers(int startInFirstThread, int finishInFirstThread,
                                     int startInSecondThread, int finishInSecondThread) {
        List<ArbitrManager> arbitrManagers = new ArrayList<>();
        ArbitrManagerParserThread arbitrManagerParserThread1 =
                new ArbitrManagerParserThread(startInFirstThread, finishInFirstThread);
        ArbitrManagerParserThread arbitrManagerParserThread2 =
                new ArbitrManagerParserThread(startInSecondThread
                        , finishInSecondThread);
        arbitrManagerParserThread1.start();
        arbitrManagerParserThread2.start();
        try {
            arbitrManagerParserThread1.join();
            arbitrManagerParserThread2.join();
            arbitrManagers.addAll(arbitrManagerParserThread1.getArbitrManagers());
            arbitrManagers.addAll(arbitrManagerParserThread2.getArbitrManagers());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arbitrManagers.forEach(System.out::println);
//        arbitrManagerRepository.saveAll(arbitrManagers);
    }
}