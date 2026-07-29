// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.petclinic.web;

import com.javastarterkit.petclinic.model.Owner;
import com.javastarterkit.petclinic.service.ClinicService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final ClinicService clinicService;

    public OwnerController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    public String listOwners(Model model) {
        List<Owner> owners = clinicService.findAllOwners();
        model.addAttribute("owners", owners);
        return "owners/list";
    }

    @GetMapping("/{id}")
    public String showOwner(@PathVariable Long id, Model model) {
        Owner owner = clinicService.findOwnerById(id).orElseThrow();
        model.addAttribute("owner", owner);
        return "owners/details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/form";
    }

    @PostMapping
    public String saveOwner(@ModelAttribute Owner owner) {
        clinicService.saveOwner(owner);
        return "redirect:/owners";
    }
}
