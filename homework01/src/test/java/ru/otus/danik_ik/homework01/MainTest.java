package ru.otus.danik_ik.homework01;

import java.lang.String;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static ru.otus.danik_ik.homework01.StringListViews.*;

/**
 * Simple parametrized test.
 */
@RunWith(Parameterized.class)
public class MainTest
{
  /**
   * Data for test.
   */
  private static final String EMPTY_INPUT = "";
  private static final String INPUT_1 =
      "А а Б б б Фф жж жЖ ЦЦЦ";

  @Parameterized.Parameters(name = "{index}: («{1}»)")
  public static Iterable<Object[]> dataForTest() {
    return Arrays.asList(new Object[][]{
        {EMPTY_INPUT, 0, 0, "", 0, ""},
        {EMPTY_INPUT, 1, 0, "", 0, ""},
        {EMPTY_INPUT, 100, 0, "", 0, ""},
        {INPUT_1, 1, 5, "А, а, Б, б, б", 2, "а, б"},
        {INPUT_1, 2, 3, "Фф, жж, жЖ", 2, "жж, фф"},
        {INPUT_1, 3, 1, "ЦЦЦ", 1, "ццц"},
    });
  }

  /**
   * Data for current instance (one row from result of dataForTest() )
   */
  private final Main testObj;
  private final int index;
  private final int fullCount;
  private final String fullListView;
  private final int normalizedCount;
  private final String normalizedListView;


  public MainTest(String source, int index, int fullCount, String fullListView,
                  int normalizedCount, String normalizedListView) {
    testObj = new Main();
    testObj.collectByLengths(source);

    this.index = index;
    this.fullCount = fullCount;
    this.fullListView = fullListView;
    this.normalizedCount = normalizedCount;
    this.normalizedListView = normalizedListView;
  }

  @Test
  public void testFullCount() throws Exception {
    assertEquals(fullCount, testObj.getWordsByLength(index).size());
  }

  @Test
  public void testFullListView() throws Exception {
    assertEquals(fullListView, getJoinedView(testObj.getWordsByLength(index)));
  }

  @Test
  public void testNormalizedCount() throws Exception {
    assertEquals(normalizedCount, getNormalizedStream(testObj.getWordsByLength(index)).count());
  }

  @Test
  public void testNormalizedListView() throws Exception {
    assertEquals(normalizedListView, getNormalizedView(testObj.getWordsByLength(index)));
  }

}
