package org.aplicacao.testbench;


import org.aplicacao.dominio.Producer;
import org.aplicacao.services.ProducerService;

import java.util.List;

public class ConnectionFactoryTest3 {
    public static void main(String[] args) {
        Producer producer1 = Producer.builder().name("Toei Animation").build();
        Producer producer2 = Producer.builder().name("Studio Ghibli").build();
        Producer producer3 = Producer.builder().name("Aniplex").build();

        ProducerService.saveTransaction(List.of(producer1,producer2,producer3));

    }




}
