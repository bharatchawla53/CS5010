import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Book class.
 */
public class BookTest {
  private Book book;
  private Person author;

  @Before
  public void setUp() {
    author = new Person("John", "Doe", 1945);
    book = new Book("Java 8", author, 51);
  }

  @Test
  public void testTitle() {
    assertEquals("Java 8", book.getTitle());
  }

  @Test
  public void testPrice() {
    assertEquals(51, book.getPrice(), 2);
  }

  @Test
  public void testAuthor() {
    assertEquals("John", author.getFirstName());
    assertEquals("Doe", author.getLastName());
    assertEquals(1945, author.getYearOfBirth());
  }
}