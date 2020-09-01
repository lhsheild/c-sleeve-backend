package com.sheildog.csleevebackend.sample.hero;

import org.springframework.stereotype.Component;

/** @author a7818 */
@Component
public class Irelia implements ISkill {

  @Override
  public void q() {
    System.out.println("Irelia q");
  }

  @Override
  public void w() {
    System.out.println("Irelia w");
  }

  @Override
  public void e() {
    System.out.println("Irelia e");
  }

  @Override
  public void r() {
    System.out.println("Irelia r");
  }
}
