package com.hendisantika.springbootthymeleafpagination.controller;

import com.hendisantika.springbootthymeleafpagination.entity.Tutorial;
import com.hendisantika.springbootthymeleafpagination.repository.TutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-thymeleaf-pagination
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/2/23
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/tutorials")
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialRepository tutorialRepository;

    @GetMapping
    public String getAllTutorials(Model model, @RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "3") int size) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            Pageable paging = PageRequest.of(page - 1, size);

            Page<Tutorial> pageTuts;
            if (keyword == null) {
                pageTuts = tutorialRepository.findAll(paging);
            } else {
                pageTuts = tutorialRepository.findByTitleContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            tutorials = pageTuts.getContent();

            model.addAttribute("tutorials", tutorials);
            model.addAttribute("currentPage", pageTuts.getNumber() + 1);
            model.addAttribute("totalItems", pageTuts.getTotalElements());
            model.addAttribute("totalPages", pageTuts.getTotalPages());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "tutorials";
    }

    @GetMapping("/new")
    public String addTutorial(Model model) {
        Tutorial tutorial = new Tutorial();
        tutorial.setPublished(true);

        model.addAttribute("tutorial", tutorial);
        model.addAttribute("pageTitle", "Create new Tutorial");

        return "tutorial-form";
    }

    @PostMapping
    public String saveTutorial(Tutorial tutorial, RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.save(tutorial);

            redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }
}
