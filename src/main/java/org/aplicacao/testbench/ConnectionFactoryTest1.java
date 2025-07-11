package org.aplicacao.testbench;

import lombok.extern.log4j.Log4j2;
import org.aplicacao.dominio.Producer;
import org.aplicacao.jdbcconnection.ConnectionFactory;
import org.aplicacao.repository.ProducerRepository;
import org.aplicacao.services.ProducerService;

import java.util.List;

@Log4j2
public class ConnectionFactoryTest1 {
    public static void main(String[] args) {
        Producer producer = Producer.builder().name("HNK").build();
        Producer producerToUpdate = Producer.builder().id(1).name("MADHOUSE").build();

        //ProducerService.save(producer);
        //ProducerService.delete(1);
        //ProducerService.update(producerToUpdate);
        //log.info("All producers: '{}'",ProducerService.findAll());
        //log.info("Producers found: '{}'",ProducerService.findByName("Studio"));
        //ProducerService.showProducerMetaData();
        //ProducerService.showDriverMetaData();
        //ProducerService.showTypeScrollWorking();
        //List<Producer> producers = ProducerService.findByNameAndUpdateToUppercase("Deen");
        //List<Producer> producers = ProducerService.findByNameAndInsertWhenNotFound("Bones");
        //log.info(producers);
        List<Producer> producers = ProducerService.findByNamePreparedStatement("H");
        log.info(producers);


    }
}
