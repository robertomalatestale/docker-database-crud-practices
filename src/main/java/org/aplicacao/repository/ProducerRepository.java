package org.aplicacao.repository;

import lombok.extern.log4j.Log4j2;
import org.aplicacao.dominio.Producer;
import org.aplicacao.jdbcconnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {

    public static void save(Producer producer){
        String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement()){
            int rowsAffected = statement.executeUpdate(sql);
            log.info("Inserted producer '{}' in database, rows affected '{}'",producer.getName(), rowsAffected);
        }catch(SQLException e){
            log.error("Error while trying to insert producer '{}'",producer.getName(), e);
        }
    }

    public static void delete(int id){
        String sql = "DELETE FROM `anime_store`.`producer` WHERE (`id` = '%d');".formatted(id);
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement()){
            int rowsAffected = statement.executeUpdate(sql);
            log.info("Deleted producer in database whose id was '{}', rows affected '{}'", id, rowsAffected);
        }catch(SQLException e){
            log.error("Error while trying to delete producer '{}'",id, e);
        }
    }

    public static void update(Producer producer){
        String sql = "UPDATE `anime_store`.`producer` SET `name` = '%s' WHERE (`id` = '%d');"
                .formatted(producer.getName(),producer.getId());
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement()){
            int rowsAffected = statement.executeUpdate(sql);
            log.info("Updated producer '{}' - '{}' - rows affected '{}'", producer.getName(),producer.getId(), rowsAffected);
        }catch(SQLException e){
            log.error("Error while trying to update producer '{}'",producer.getId(), e);
        }
    }

    public static List<Producer> findAll(){
        log.info("Finding all producers");
        return findByName("");
    }


    public static List<Producer> findByName(String name){
        log.info("Finding producers...");
        String sql = "SELECT * FROM producer WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            while(rs.next()){
                Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                producers.add(producer);
            }
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
        return producers;
    }
}
