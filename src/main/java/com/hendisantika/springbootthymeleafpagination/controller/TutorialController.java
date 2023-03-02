package com.hendisantika.springbootthymeleafpagination.controller;

import com.hendisantika.springbootthymeleafpagination.repository.TutorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

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
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialRepository tutorialRepository;
}
