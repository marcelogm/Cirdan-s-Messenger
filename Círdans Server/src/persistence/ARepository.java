/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Marcelo Gomes Martins
 */
public abstract class ARepository {
    protected PreparedStatement statement;
    
    protected void setTimestampOnCreate(PreparedStatement statement) throws SQLException{
        statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
    }
    
    protected void setTimestampOnUpdate(PreparedStatement statement) throws SQLException{
        statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
    }
}
