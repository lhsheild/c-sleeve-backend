package com.sheildog.csleevebackend.optional;

import lombok.var;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void testOptional(){
//        Optional<String> empty = Optional.empty();
        Optional<String> t1 = Optional.ofNullable("888");
//        empty.get();
//        t1.ifPresent(System.out::print);
//        var t = t1.orElse("666");
//        var t = t1.orElseGet(() -> "666");
//        System.out.print(t);
        var a = t1.filter(t-> t.equals("321")).orElseGet(()->"123");

        System.out.print(a);
    }
}
