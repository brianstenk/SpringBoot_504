package com.example.springboot_503;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    CloudinaryConfig cloudc;
    @RequestMapping("/")
    public String listActors(Model model){
        model.addAttribute("actors", actorRepository.findAll());
        return "list";
    }

    @RequestMapping(value = "/actor/list", method = RequestMethod.GET)
    public String listMovieActors(Model model){
        Iterable<Actor> itr = actorRepository.findAll();
        List<Actor> results = new ArrayList<>();
        Actor actor = new Actor();
        actor.setHeadshot("Some headshot");
        actor.setId(1);
        actor.setName("Some name");
        actor.setRealname("Some real name");
        results.add(actor);
        model.addAttribute("actors",results);
        return "list";
    }

    @GetMapping("/add")
    public String newActor(Model model){
        model.addAttribute("actor", new Actor());
        return "form";
    }
    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor,
                               @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/add";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepository.save(actor);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }

}
