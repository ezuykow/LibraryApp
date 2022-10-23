package ru.ezuykow.app.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ezuykow.app.model.Book;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void add(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book(title, author, publishingYear) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getPublishingYear());
    }

    public List<Book> getBooksByPersonId(int person_id) {
        return jdbcTemplate.query(
                "SELECT book.title, book.author, book.publishingYear " +
                        "FROM person JOIN book ON person.person_id = ? AND book.person_id=?",
                new Object[]{person_id, person_id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
