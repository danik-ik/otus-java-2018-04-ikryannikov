package ru.otus.danik_ik.homework01;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringListViews {

  public static String getJoinedView(Collection<String> source) {
    return source
        .parallelStream()
        .collect(Collectors.joining(", "));
  }

  public static String getNormalizedView(Collection<String> source) {
    return getNormalizedStream(source)
        .collect(Collectors.joining(", "));
  }

  public static  Stream<String> getNormalizedStream(Collection<String> source) {
    return source
        .parallelStream()
        .map(String::toLowerCase)
        .distinct()
        .sorted();
  }
}
