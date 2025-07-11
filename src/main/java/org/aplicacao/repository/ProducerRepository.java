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

    public static void saveTransaction(List<Producer> producers){
        String sql = "UPDATE `anime_store`.`producer` SET `name` = '%s' WHERE (`id` = '%d');";
        try(Connection connection = ConnectionFactory.getConnection()){
            connection.setAutoCommit(false);
            preparedStatementsaveTransaction(connection, producers);
            connection.commit();
            connection.setAutoCommit(true);
        }catch(SQLException e){
            log.error("Error while trying to save producers '{}'", producers, e);
        }
    }

    public static void preparedStatementsaveTransaction(Connection connection, List<Producer> producers) throws SQLException {
        String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES ( ? );";
        boolean shouldRollback = false;
        for(Producer p: producers){
            try( PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                log.info("Saving producer '{}'",p.getName());
                preparedStatement.setString(1,p.getName());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                shouldRollback = true;
            }
        }
        if(shouldRollback) {
            log.warn("Transaction is being rollback");
            connection.rollback();}

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

    public static void updatePreparedStatement(Producer producer){
        String sql = "UPDATE `anime_store`.`producer` SET `name` = '%s' WHERE (`id` = '%d');";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = preparedStatementUpdate(connection, producer)){
            int rowsAffected = preparedStatement.executeUpdate();
            log.info("Updated producer '{}' - '{}' - rows affected '{}'", producer.getName(),producer.getId(), rowsAffected);
        }catch(SQLException e){
            log.error("Error while trying to update producer '{}'",producer.getId(), e);
        }
    }

    public static PreparedStatement preparedStatementUpdate(Connection connection, Producer producer) throws SQLException {
        String sql = "UPDATE `anime_store`.`producer` SET `name` = ? WHERE (`id` = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,producer.getName());
        preparedStatement.setInt(2,producer.getId());
        return preparedStatement;
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

    public static List<Producer> findByNamePreparedStatement(String name){
        log.info("Finding producers...");
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = preparedStatementFindByName(connection, name);
            ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()){
                Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                producers.add(producer);
            }
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
        return producers;
    }

    public static PreparedStatement preparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = "SELECT * FROM producer WHERE name LIKE ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,String.format("%%%s%%",name));
        return preparedStatement;
    }

    public static List<Producer> findByNameCallableStatement(String name){
        log.info("Finding producers...");
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            CallableStatement callableStatement = callableStatementFindByName(connection, name);
            ResultSet rs = callableStatement.executeQuery()) {
            while(rs.next()){
                Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                producers.add(producer);
            }
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
        return producers;
    }

    public static CallableStatement callableStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = "CALL `anime_store`.`sp_get_producer_by_name`(?);";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1,String.format("%%%s%%",name));
        return callableStatement;
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

    public static void showTypeScrollWorking(){
        log.info("Finding producers...");
        String sql = "SELECT * FROM producer;";
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql)){
            log.info("Last row? '{}'",rs.last());
            log.info("Row number '{}'",rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

            log.info("First row? '{}'",rs.first());
            log.info("Row number '{}'",rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

            log.info("Row absolute? '{}'",rs.absolute(2));
            log.info("Row number '{}'",rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

            log.info("Row relative? '{}'",rs.relative(-1));
            log.info("Row number '{}'",rs.getRow());
            log.info(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());

            log.info("Is last? '{}'",rs.isLast());
            log.info("Row number '{}'",rs.getRow());

            log.info("Is first? '{}'",rs.isFirst());
            log.info("Row number '{}'",rs.getRow());

        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
    }

    public static List<Producer> findByNameAndUpdateToUppercase(String name){
        log.info("Finding producers...");
        String sql = "SELECT * FROM producer WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql)){
            while(rs.next()){
                rs.updateString("name", rs.getString("name").toUpperCase());
                rs.updateRow();
                Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                producers.add(producer);
            }
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
        return producers;
    }

    public static List<Producer> findByNameAndInsertWhenNotFound(String name){
        log.info("Finding producers...");
        String sql = "SELECT * FROM producer WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql)){
            if(rs.next()) return producers;
            insertNewProducer(name, rs);
            producers.add(getProducer(rs));
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
        return producers;
    }

    public static void findByNameAndDelete(String name){
        log.info("Finding producers...");
        String sql = "SELECT * FROM producer WHERE name LIKE '%%%s%%';"
                .formatted(name);
        List<Producer> producers = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(sql)){
            while(rs.next()){
                log.info("Deleting '{}'", rs.getString("name"));
                rs.deleteRow();
            }
        }catch(SQLException e){
            log.error("Error while trying to find all producers", e);
        }
    }

    private static void insertNewProducer(String name, ResultSet rs) throws SQLException {
        rs.moveToInsertRow();
        rs.updateString("name", name);
        rs.insertRow();
    }

    private static Producer getProducer(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        rs.next();
        Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
        return producer;
    }
}
