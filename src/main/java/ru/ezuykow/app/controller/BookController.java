package ru.ezuykow.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ezuykow.app.dao.BookDAO;
import ru.ezuykow.app.model.Book;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/new")
    public String addPage(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping()
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.add(book);
        return "redirect:/books";
    }
}
