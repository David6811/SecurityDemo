package org.helloai.gateway.controller;

import org.helloai.gateway.mq.QueueProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class DemoController {
  @Autowired
  QueueProduceService queueProduceService;

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

  @GetMapping(value = "/admin")
  @ResponseBody
  public String admin() throws Exception {
    return "Testing admin role.";
  }

  @GetMapping(value = "/testActiveMQ")
  @ResponseBody
  public void testActiveMQ() throws Exception {
    queueProduceService.produceMsg("test activeMQ");
  }


}
