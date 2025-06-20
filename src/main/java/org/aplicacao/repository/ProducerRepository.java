package org.aplicacao.repository;

import lombok.extern.log4j.Log4j2;
import org.aplicacao.dominio.Producer;
import org.aplicacao.jdbcconnection.ConnectionFactory;

import java.sql.*;
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

    public static void showProducerMetaData(){
        log.info("Showing producer Metadata");
        String sql = "SELECT * FROM anime_store.producer;";
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            log.info("Columns count: '{}'",columnCount);
            for (int i = 1; i <= columnCount; i++) {
                log.info("Table name: '{}'", rsMetaData.getTableName(i));
                log.info("Column name: '{}'", rsMetaData.getColumnName(i));
                log.info("Column size: '{}'", rsMetaData.getColumnDisplaySize(i));
                log.info("Column type: '{}'", rsMetaData.getColumnTypeName(i));

            }
        }catch(SQLException e){
            log.error("Error while trying to show producer Metadata", e);
        }
    }

    public static void showDriverMetaData(){
        log.info("Showing driver Metadata");
        String sql = "SELECT * FROM anime_store.producer;";
        try(Connection connection = ConnectionFactory.getConnection()){
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            if(databaseMetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)){
                log.info("Supports TYPE_FORWARD_ONLY");
            } if(databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE)){
                log.info("Supports CONCUR_UPDATABLE");
            }
            if(databaseMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)){
                log.info("Supports SCROLL_INSENSITIVE");
            } if(databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                log.info("Supports CONCUR_UPDATABLE");
            }
            if(databaseMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)){
                log.info("Supports SCROLL_SENSITIVE");
            } if(databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE)){
                log.info("Supports CONCUR_UPDATABLE");
            }
        }catch(SQLException e){
            log.error("Error while trying to show producer Metadata", e);
        }
    }


}
