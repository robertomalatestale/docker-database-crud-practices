package org.aplicacao.testbench;

import lombok.extern.log4j.Log4j2;
import org.aplicacao.dominio.Producer;
import org.aplicacao.repository.ProducerRepositoryRowSet;
import org.aplicacao.services.ProducerService;
import org.aplicacao.services.ProducerServiceRowSet;

import java.util.List;

@Log4j2
public class ConnectionFactoryTest02 {
    public static void main(String[] args) {
        Producer producerToUpdate = Producer.builder().id(1).name("madhouse").build();
        ProducerServiceRowSet.updateJdbcRowSet(producerToUpdate);
//        log.info("----");
//        List<Producer> producers = ProducerRepositoryRowSet.findByNameJdbcRowSet("");
//        log.info(producers);
    }
}
