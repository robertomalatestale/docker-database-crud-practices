package org.aplicacao.services;

import org.aplicacao.dominio.Producer;
import org.aplicacao.repository.ProducerRepositoryRowSet;

import java.util.List;

public class ProducerServiceRowSet {

    public static List<Producer> findByNameJdbcRowSet(String name){
        return ProducerRepositoryRowSet.findByNameJdbcRowSet(name);
    }

    public static void updateJdbcRowSet(Producer producer){
        ProducerRepositoryRowSet.updateJdbcRowSet(producer);
    }

    public static void updateCachedRowSet(Producer producer){
        ProducerRepositoryRowSet.updateCachedRowSet(producer);
    }
}
