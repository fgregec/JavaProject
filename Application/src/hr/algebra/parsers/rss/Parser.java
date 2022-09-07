/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.enums.TagType;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Komp
 */
public class Parser {

    private static final String URL = "https://web.archive.org/web/20210228003317/https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    private static final String SEPARATOR = ",";

    private static Person getPerson(String data) {
        String[] personName = data.trim().split(" ", 2);
        switch (personName.length) {
            case 1:
                return new Person(personName[0], "");
            case 2:
                return new Person(personName[0], personName[1]);
        }
        return null;
    }

    private static void getPicture(Movie movie, String url) throws IOException {
         try {
            String ext = url.substring(url.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(url, localPicturePath);

            movie.setPicturePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Parser() {
    }

    public static List<Movie> parse() throws IOException, XMLStreamException, ParseException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(URL);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);

                        if (tagType.isPresent() && tagType.get() == TagType.ITEM) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent()) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        LocalDateTime publisheDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publisheDate);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {                                        
                                        movie.setDescription(data.substring(data.lastIndexOf("\">") +2 , data.lastIndexOf("<br />")).trim());
                                    }
                                    break;
                                case ORG_TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setOriginalTitle(data);
                                    }
                                    break;
                                case DIRECTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDirector(getPerson(data));
                                    }
                                    break;
                                case ACTORS:
                                    if (movie != null && !data.isEmpty()) {
                                        List<Person> actors = new ArrayList<>();
                                        String[] peopleInfo = data.split(SEPARATOR);
                                        for (String personInfo : peopleInfo) {
                                            actors.add(getPerson(personInfo));
                                        }
                                        movie.setActors(actors);
                                    }
                                    break;
                                case DURATION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDuration(data);
                                    }
                                    break;
                                case PICTURE_PATH:
                                    if (movie != null && !data.isEmpty()) {
                                        getPicture(movie, data);
                                    }
                                    break;
                                case GENRE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setGenre(data);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
         
        return movies;
    }
}
