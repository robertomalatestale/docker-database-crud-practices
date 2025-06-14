package org.aplicacao.testbench;

import org.aplicacao.dominio.Producer;
import org.aplicacao.jdbcconnection.ConnectionFactory;
import org.aplicacao.repository.ProducerRepository;

public class ConnectionFactoryTest1 {
    public static void main(String[] args) {
        Producer producer = Producer.ProducerBuilder.aProducer().name("NHK").build();
        ProducerRepository.save(producer);
    }
}
