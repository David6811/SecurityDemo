package org.helloai.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class DemoController {

  @GetMapping(value = "/test")
  @ResponseBody
  public String test() throws Exception {
    return "Testing Successful.";
  }

  @GetMapping(value = "/excludedAuthPages")
  @ResponseBody
  public String excludedAuthPages() throws Exception {
    return "Testing excludedAuthPages.";
  }


}
