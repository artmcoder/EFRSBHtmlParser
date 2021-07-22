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



    public static void main(String[] args) {
        List<ArbitrManager> arbitrManagers = new ArrayList<>();
        ArbitrManagerParserThread arbitrManagerParserThread1 =
                new ArbitrManagerParserThread(0, 10);
        ArbitrManagerParserThread arbitrManagerParserThread2 =
                new ArbitrManagerParserThread(10
                        , 20);
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
    }
}