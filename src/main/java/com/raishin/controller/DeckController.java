package com.raishin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.raishin.entity.DeckEntity;
import com.raishin.form.DeckForm;
import com.raishin.repository.DeckRepository;

@Controller
public class DeckController {

  // @Autowired
  // DeckMapper mapper;

  @Autowired
  DeckRepository deckRepository;

  Random random = new Random();

  @RequestMapping(value = "/deck/list")
  public String index(@ModelAttribute("deckForm") DeckForm form, BindingResult result,
      Model model) {
    List<String> decknameList = new ArrayList<>();
    List<String> backColorList = new ArrayList<>();
    List<Integer> duelNumberList = new ArrayList<>();
    List<DeckEntity> entityList = deckRepository.findAll();
    entityList.stream().forEach(x -> decknameList.add(x.getDeckname()));
    entityList.stream().forEach(x -> duelNumberList.add(x.getWin() + x.getLose() + x.getDraw()));
    entityList.stream().forEach(x -> backColorList.add("rgb(" + random.nextInt(256) + ", "
        + random.nextInt(256) + ", " + random.nextInt(256) + ")"));

    model.addAttribute("backColorList", backColorList);
    model.addAttribute("decknameList", decknameList);
    model.addAttribute("duelNumberList", duelNumberList);
    model.addAttribute("TopFiveNameList",
        decknameList.stream().limit(5).collect(Collectors.toList()));
    model.addAttribute("TopFiveNumberList",
        duelNumberList.stream().limit(5).collect(Collectors.toList()));
    form.setDeckList(entityList);
    return "starter";
  }
}
