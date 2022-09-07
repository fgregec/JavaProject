/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.SqlAccountRepository;
import hr.algebra.dal.sql.SqlMovieRepository;



/**
 *
 * @author dnlbe
 */
public class RepositoryFactory {

    private RepositoryFactory() {
    }
    
    public static RepositoryAccount getAccountRepository() throws Exception {
        return new SqlAccountRepository();
    }
    
    public static RepositoryMovie getMovieRepository() throws Exception{
        return new SqlMovieRepository();
    }
}
