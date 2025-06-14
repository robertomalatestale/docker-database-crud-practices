package org.aplicacao.repository;

import org.aplicacao.dominio.Producer;
import org.aplicacao.jdbcconnection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProducerRepository {
    public static void save(Producer producer){
        String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
        try(Connection connection = ConnectionFactory.getConnection();
            Statement statement = connection.createStatement()){
            int rowsAffected = statement.executeUpdate(sql);
            System.out.println(rowsAffected);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
