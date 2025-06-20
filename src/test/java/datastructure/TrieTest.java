package datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    private Trie trie;

    @BeforeEach
    void setUp() {
        trie = new Trie();
    }

    @Test
    void testInsertAndSuggest() {
        trie.insert("apple");
        trie.insert("apply");
        trie.insert("application");
        trie.insert("banana");

        List<String> suggestions = trie.suggest("app");
        assertEquals(3, suggestions.size());
        assertTrue(suggestions.containsAll(List.of("apple", "apply", "application")));

        suggestions = trie.suggest("apple");
        assertEquals(1, suggestions.size());
        assertTrue(suggestions.contains("apple"));

        suggestions = trie.suggest("b");
        assertEquals(1, suggestions.size());
        assertTrue(suggestions.contains("banana"));

        suggestions = trie.suggest("z");
        assertTrue(suggestions.isEmpty());
    }

    @Test
    void testDelete() {
        trie.insert("team");
        trie.insert("tear");

        List<String> suggestions = trie.suggest("tea");
        assertEquals(2, suggestions.size());

        trie.delete("team");
        suggestions = trie.suggest("tea");
        assertEquals(1, suggestions.size());
        assertEquals("tear", suggestions.get(0));

        // Deleting a word that is a prefix of another should only unset the endOfWord flag
        trie.insert("app");
        trie.insert("apple");
        trie.delete("app");

        List<String> appSuggestions = trie.suggest("app");
        assertEquals(1, appSuggestions.size());
        assertEquals("apple", appSuggestions.get(0)); // "app" is not a word anymore, but "apple" is
    }

    @Test
    void testDeleteNonExistentWord() {
        trie.insert("hello");
        trie.delete("hell"); // Not a word in the trie
        trie.delete("world"); // Not in trie at all

        List<String> suggestions = trie.suggest("hel");
        assertEquals(1, suggestions.size());
        assertEquals("hello", suggestions.get(0));
    }
}