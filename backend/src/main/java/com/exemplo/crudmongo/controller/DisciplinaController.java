package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    
    @GetMapping
    public List<Disciplina> getAll() {
        return service.findAll();
    }

@PostMapping
public Disciplina create(@RequestBody Disciplina disciplina) {
    return service.save(disciplina);

}
}