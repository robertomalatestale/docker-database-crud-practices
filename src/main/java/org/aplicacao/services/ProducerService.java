package org.aplicacao.services;

import org.aplicacao.dominio.Producer;
import org.aplicacao.repository.ProducerRepository;

import java.util.List;
import java.util.Objects;

public class ProducerService {

    public static void save(Producer producer){
        Objects.requireNonNull(producer, "Producer must not be null");
        ProducerRepository.save(producer);
    }


    public static void delete(Integer id){
        requireValidId(id);
        ProducerRepository.delete(id);
    }

    public static void update(Producer producer){
        Objects.requireNonNull(producer, "Producer must not be null");
        requireValidId(producer.getId());
        ProducerRepository.update(producer);
    }

    public static void updatePreparedStatement(Producer producer){
        Objects.requireNonNull(producer, "Producer must not be null");
        requireValidId(producer.getId());
        ProducerRepository.updatePreparedStatement(producer);
    }

    public static List<Producer> findAll(){
        return ProducerRepository.findAll();
    }

    public static List<Producer> findByName(String name){
        return ProducerRepository.findByName(name);
    }

    public static List<Producer> findByNamePreparedStatement(String name){
        return ProducerRepository.findByNamePreparedStatement(name);
    }
    public static List<Producer> findByNameCallableStatement(String name){
        return ProducerRepository.findByNameCallableStatement(name);
    }

    public static void showProducerMetaData(){
        ProducerRepository.showProducerMetaData();
    }

    public static void showDriverMetaData(){
        ProducerRepository.showDriverMetaData();
    }

    public static void showTypeScrollWorking(){
        ProducerRepository.showTypeScrollWorking();
    }

    public static List<Producer> findByNameAndUpdateToUppercase(String name){
        return ProducerRepository.findByNameAndUpdateToUppercase(name);
    }

    public static List<Producer> findByNameAndInsertWhenNotFound(String name){
        return ProducerRepository.findByNameAndInsertWhenNotFound(name);}

    public static void findByNameAndDelete(String name){
        ProducerRepository.findByNameAndDelete(name);}

    private static void requireValidId(Integer id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("Invalid value for id");
        }
    }


}
