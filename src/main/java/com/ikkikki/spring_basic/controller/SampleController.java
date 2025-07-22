package com.ikkikki.spring_basic.controller;

import com.ikkikki.spring_basic.domain.dto.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.IntStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

@Controller
@RequestMapping("sample")
@Slf4j
public class SampleController {
  @GetMapping("ex1")
  public void ex1(){
    log.info("ex1.....................");
  }

  @GetMapping({"ex2", "exLink"})
  public void ex2(Model model){
    List<SampleDTO> dtos = LongStream.rangeClosed(1,20)
            .mapToObj(i -> SampleDTO.builder()
            .sno((long)i)
            .first("First"+i).last("Last"+i)
            .regTime(LocalDateTime.now())
            .build()).toList();

    model.addAttribute("list", dtos);
  }

  @GetMapping("ex3inline")
  public String ex3(RedirectAttributes rttr){
    List<SampleDTO> dtos = LongStream.rangeClosed(1,20)
            .mapToObj(i -> SampleDTO.builder()
                    .sno(i)
                    .first("First"+i).last("Last"+i)
                    .regTime(LocalDateTime.now())
                    .build()).toList();
    rttr.addFlashAttribute("list", dtos);
    rttr.addFlashAttribute("result", "success");
    rttr.addFlashAttribute("number", 10);
    rttr.addAttribute("date", LocalDateTime.now());
    return "redirect:ex3";
  }

  @GetMapping("ex3")
  public void ex3(){}

  @GetMapping("exLayout1")
  public void exLayout1(Model model){}
}
