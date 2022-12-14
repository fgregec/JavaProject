/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.RepositoryAccount;
import hr.algebra.model.Account;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Komp
 */
public class SqlAccountRepository implements RepositoryAccount {

    private static final String ID_ACCOUNT = "IDAccount";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String IS_ADMIN = "IsAdmin";
    
    
    private static final String CREATE_ACCOUNT = "{ CALL createAccount(?,?,?) }";
    private static final String SELECT_ACCOUNT = "{ CALL selectAccount(?) }";
    
    @Override
    public int createAccount(Account account) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACCOUNT))
        {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.registerOutParameter(3, Types.INTEGER);
            
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public Optional<Account> selectAccount(String username) throws Exception {
       DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACCOUNT)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Account(
                            rs.getInt(ID_ACCOUNT),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            rs.getBoolean(IS_ADMIN)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean doesAccountExist(String username) throws Exception {
         DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACCOUNT)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
