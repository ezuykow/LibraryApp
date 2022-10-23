package ru.ezuykow.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ezuykow.app.dao.PersonDAO;
import ru.ezuykow.app.model.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String indexPage(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{person_id}")
    public String personPage(@PathVariable("person_id") int person_id, Model model) {
        model.addAttribute("person", personDAO.getPerson(person_id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String addPerson(@Valid @ModelAttribute("person") Person person,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.add(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String editPage(@PathVariable("person_id") int person_id,
                           Model model) {
        model.addAttribute("person", personDAO.getPerson(person_id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String editPerson(@PathVariable("person_id") int person_id,
                             @Valid @ModelAttribute("person") Person person,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.edit(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String deletePerson(@PathVariable("person_id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}
