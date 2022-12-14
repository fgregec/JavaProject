/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.RepositoryMovie;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Komp
 */
public class SqlMovieRepository implements RepositoryMovie {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String PERSON_ID = "PersonID";
    private static final String DURATION = "Duration";
    private static final String GENRE = "Genre";
    private static final String PICTURE_PATH = "PicturePath";

    private static final String ID_PERSON = "IDPerson";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String DELETE_MOVIES = "{ CALL deleteMovies }";

    private static final String CREATE_PERSON = "{ CALL createPerson (?,?,?) }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PERSON_BY_NAME = "{ CALL selectPersonByName (?,?) }";
    private static final String SELECT_PERSONS = "{ CALL selectPersons }";

    private static final String CREATE_MOVIE_ACTOR = "{ CALL createMovieActor (?,?,?) }";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors (?) }";

    private static final String SELECT_NOT_FAVORITE_MOVIES = "{ CALL selectNotFavouriteMovies }";
    private static final String ADD_FAVORITE_MOVIE = "{ CALL addFavouriteMovie (?) }";
    private static final String SELECT_FAVORITE_MOVIES = "{ CALL selectFavouriteMovies }";
    private static final String REMOVE_FAVORITE_MOVIE = "{ CALL removeFavoriteMovie (?) }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        int director = 0;
        DataSource dataSource = DataSourceSingleton.getInstance();
        director = this.createPerson(movie.getDirector());

        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMAT));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalTitle());
            stmt.setInt(5, director);
            stmt.setString(6, movie.getDuration());
            stmt.setString(7, String.valueOf(movie.getGenre()));
            stmt.setString(8, movie.getPicturePath());
            stmt.registerOutParameter(9, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(9);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                int createPerson = 0;
                createPerson = this.createPerson(movie.getDirector());

                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMAT));
                stmt.setString(3, movie.getDescription());
                stmt.setString(4, movie.getOriginalTitle());
                stmt.setInt(5, createPerson);
                stmt.setString(6, movie.getDuration());
                stmt.setString(7, movie.getGenre());
                stmt.setString(8, movie.getPicturePath());
                stmt.registerOutParameter(9, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

            stmt.setInt(1, id);
            stmt.setString(2, movie.getTitle());
            stmt.setString(3, movie.getPublishedDate().format(Movie.DATE_FORMAT));
            stmt.setString(4, movie.getDescription());
            stmt.setString(5, movie.getOriginalTitle());
            stmt.setInt(6, movie.getDirector().getId());
            stmt.setString(7, String.valueOf(movie.getDuration()));
            stmt.setString(8, String.valueOf(movie.getGenre()));
            stmt.setString(9, movie.getPicturePath());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMAT),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            new Person(rs.getInt(PERSON_ID),
                                    rs.getString(FIRST_NAME),
                                    rs.getString(LAST_NAME)),
                            selectMovieActors(rs.getInt(ID_MOVIE)),
                            rs.getString(DURATION),
                            rs.getString(GENRE),
                            rs.getString(PICTURE_PATH)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMAT),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        new Person(rs.getInt(PERSON_ID),
                                rs.getString(FIRST_NAME),
                                rs.getString(LAST_NAME)),
                        null,
                        rs.getString(DURATION),
                        rs.getString(GENRE),
                        rs.getString(PICTURE_PATH));
                movie.setActors(selectMovieActors(movie.getId()));

                movies.add(movie);
            }
        }
        return movies;
    }

    @Override
    public int createPerson(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PERSON)) {

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void updatePerson(int id, Person data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {

            stmt.setString(1, data.getFirstName());
            stmt.setString(2, data.getLastName());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Person> selectPerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Person(
                            rs.getInt(id)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Person> selectPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSONS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                persons.add(new Person(
                        rs.getInt(ID_PERSON),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
            }
        }
        return persons;
    }

    @Override
    public int createMovieActor(Person person, Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_ACTOR)) {

            stmt.setInt(1, movie.getId());
            stmt.setInt(2, person.getId());
            stmt.registerOutParameter(3, Types.INTEGER);

            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public List<Person> selectMovieActors(int id) throws Exception {
        List<Person> persons = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTORS)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    persons.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)));
                }
            }

            return persons;
        }
    }

    @Override
    public void deleteMovies() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIES)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Movie> selectNotFavoriteMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_NOT_FAVORITE_MOVIES);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                if (rs == null) {
                    continue;
                }
                Movie movie = new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMAT),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        new Person(rs.getInt(PERSON_ID),
                                rs.getString(FIRST_NAME),
                                rs.getString(LAST_NAME)),
                        null,
                        rs.getString(DURATION),
                        rs.getString(GENRE),
                        rs.getString(PICTURE_PATH));
                movie.setActors(selectMovieActors(movie.getId()));

                movies.add(movie);
            }
        }
        return movies;
    }

    @Override
    public void addFavoriteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ADD_FAVORITE_MOVIE)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void removeFavoriteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(REMOVE_FAVORITE_MOVIE)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        }

    }

    @Override
    public List<Movie> selectFavoriteMovies() throws Exception {
    List<Movie> favoriteMovies = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_FAVORITE_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                favoriteMovies.add(new Movie(rs.getInt(ID_MOVIE), rs.getString(TITLE)));
                
                
            }
        }
        return favoriteMovies;  

}

    @Override
    public Optional<Person> selectPersonByName(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON_BY_NAME)) {

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Person(rs.getInt(1),rs.getString(2), rs.getString(3)));
                }
            }
        }
        return Optional.empty();    }

}