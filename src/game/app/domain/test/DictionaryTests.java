package game.app.domain.test;

import game.app.domain.Dictionary;
import game.app.domain.exceptions.UnableAddWordToDictionaryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DictionaryTests {
    @Test
    void checkDictionaryCreate() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        Assertions.assertTrue(true);
    }

    @Test
    void containsWordInDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        Assertions.assertTrue(dict.contains("cat"));
    }

    @Test
    void containsWordInDifferentCasesDictionaryCreate() {
        Set<String> words = new HashSet<>(Arrays.asList("CaT", "DOG", "sheep"));
        var dict = new Dictionary("Test", words);
        Assertions.assertTrue(dict.contains("cAt"));
        Assertions.assertTrue(dict.contains("dog"));
        Assertions.assertTrue(dict.contains("SHEEP"));
    }

    @Test
    void addExistingWordInDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("CaT", "DOG", "sheep"));
        var dict = new Dictionary("Test", words);
        try {
            dict.addNewWord("cat");
            Assertions.fail();
        } catch (UnableAddWordToDictionaryException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void addNewWordInDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        dict.addNewWord("duck");
        Assertions.assertTrue(dict.contains("duck"));
    }

    @Test
    void removeWordFromDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        dict.removeWord("dog");
        Assertions.assertFalse(dict.contains("dog"));
    }

    @Test
    void removeNotExistingWordFromDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        dict.removeWord("duck");
        Assertions.assertFalse(dict.contains("duck"));
    }

    @Test
    void addWordAfterRemoveWordFromDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        dict.removeWord("dog");
        try {
            dict.addNewWord("dog");
            Assertions.fail();
        } catch (UnableAddWordToDictionaryException e) {
            Assertions.assertTrue(true);
        }
    }


    @Test
    void checkGetRandomWordByLength() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep", "luck", "tan"));
        var dict = new Dictionary("Test", words);
        HashMap<String, Integer> results = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            String randomWord = dict.getRandomWordByLength(3);
            results.put(randomWord, results.containsKey(randomWord)? results.get(randomWord) + 1 : 1);
        }

        Assertions.assertTrue(results.get("cat") > 0);
        Assertions.assertTrue(results.get("dog") > 0);
        Assertions.assertTrue(results.get("tan") > 0);
        Assertions.assertFalse(results.containsKey("luck"));
        Assertions.assertFalse(results.containsKey("luck"));
    }

    @Test
    void checkGetAllWordsFromDictionary() {
        Set<String> words = new HashSet<>(Arrays.asList("cat", "dog", "sheep"));
        var dict = new Dictionary("Test", words);
        Assertions.assertEquals(words.size(), dict.getAllWords().size());
    }
}
