package ru.otus.danik_ik.homework01;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static ru.otus.danik_ik.homework01.StringListViews.*;

/**
 * Группировка слов по длине в отдельные коллекции
 */
public class Main
{
  final static String TEXT =
      "Больше всего на свете я ненавижу обман и люблю честность и потому сразу честно признаюсь, что я вас (совсем немножко!) обманул: на самом деле это не НИКАКАЯ ГЛАВА, а НИКАКАЯ НЕ ГЛАВА – это просто-напросто… Думаете, так я вам и сказал? Нет, подождите. Вот дочитаете до конца, тогда узнаете! А не дочитаете – ну что ж, дело ваше. Только тогда – почти наверняка! – не сумеете правильно прочитать и всю книжку. Да, да!\n" +
      "Дело в том, что хотя перед вами – сказка, но сказка эта очень, очень не простая.\n" +
      "Начнем с начала, как советует Червонный Король (вам предстоит с ним скоро встретиться). И даже немножко раньше: с названия.\n" +
      "«Приключения Алисы в Стране Чудес»…\n" +
      "Будь моя воля, я бы ни за что не назвал так эту книжку. Такое название, по-моему, только сбивает с толку. В самом деле – разве по названию догадаешься, что речь пойдет о маленькой (хотя и очень умной!) девочке? Что приключения будут совсем не такие, как обычно: не будет ни шпионов, ни индейцев, ни пиратов, ни сражений, ни землетрясений, ни кораблекрушений, ни даже охоты на крупную дичь.\n" +
      "Да и «Страна Чудес» – тоже не совсем те слова, какие хотелось бы написать в заглавии этой сказки!\n" +
      "Нет, будь моя воля, я назвал бы книжку, например, так: «Аленка в Вообразилии». Или «Аля в Удивляндии». Или «Алька в Чепухании». Ну уж, на худой конец: «Алиска в Расчудесии». Но стоило мне заикнуться об этом своем желании, как все начинали на меня страшно кричать, чтобы я не смел. И я не посмел!\n" +
      "Все горе в том, что книжка эта была написана в Англии сто лет тому назад и за это время успела так прославиться, что и у нас все – хотя бы понаслышке – знают про Алису и привыкли к скучноватому названию «Приключения Алисы в Стране Чудес». Это называется литературной традицией, и тут, как говорится, ничего не попишешь. Хотя название «Алиска в Расчудесии» гораздо больше похоже на настоящее, английское название этой сказки; но если бы я ее так назвал, люди подумали бы, что это совершенно другая книжка, а не та, знаменитая…\n" +
      "А знаменита «Алиса» действительно сверх всякой меры. В особенности в тех странах, где говорят по-английски. Там ее знает каждый и любят все. И самое интересное, что, хотя эта сказка для детей, пожалуй, больше детей любят ее взрослые, а больше всех – самые взрослые из взрослых – ученые!\n" +
      "Да, сразу видно, что это очень и очень непростая сказка!\n";

  private Map<Integer, Collection<String>> wordsByLengths = new TreeMap<>();

  public static void main( String[] args ) {
    new Main().execute();
  }

  private void execute() {
    collectSample();
    printCollected();
  }

  private void collectSample() {
    collectByLengths(TEXT);
  }

  private void printCollected() {

    for (Map.Entry<Integer, Collection<String>> mapEntry: wordsByLengths.entrySet()) {
      System.out.println("===================================");
      System.out.println("Length: " + mapEntry.getKey());
      System.out.println(getNormalizedView(mapEntry.getValue()));
    }
  }

  public void collectByLengths(String source) {
    getSampleWords(source)
        .forEach(this::registerWord);
  };

  public Collection<String> getWordsByLength(Integer length) {
    Collection<String> result = wordsByLengths.get(length);
    return result == null ? Collections.emptyList() : result;
  }

  private void registerWord(String word) {
    Integer length = word.length();
    Collection<String> collection = wordsByLengths.get(length);
    if (collection == null) {
      collection = new LinkedList<>();
      wordsByLengths.put(length, collection);
    }
    collection.add(word);
  }

  private static Stream<String> getSampleWords(String source) {
    return getWords(source);
  }

  private static Stream<String> getWords(String contents) {
    return Pattern.compile("[\\P{L}]+").splitAsStream(contents);
  }
}
