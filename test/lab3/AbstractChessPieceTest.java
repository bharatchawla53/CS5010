package lab3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link ChessPiece}s.
 */
public abstract class AbstractChessPieceTest {
  /*
    Leave this section alone: It contains abstract methods to
    create ChessPiece, and concrete implementations of this testing class
    will supply particular implementations of ChessPiece to be used within
    these tests.
   */

  /**
   * Constructs an instance of the class under test representing AbstractChessPiece object using
   * row, col, and color.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the color to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   * @return an instance of the class under test
   */
  protected abstract ChessPiece acp(int row, int col, Color color);

  /**
   * This class tests Bishop chess piece.
   */
  public static final class BishopTest extends AbstractChessPieceTest {

    @Override
    protected ChessPiece acp(int row, int col, Color color) {
      return new Bishop(row, col, color);
    }

    @Test
    public void testEachChessPieceMoves() {
      for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
          initializeResults();
          ChessPiece piece = acp(row, col, Color.BLACK);
          setupResults(row, col, piece);
          verifyMoveResults(piece);
        }
      }
    }

    @Test(timeout = 500)
    public void testEachChessPieceKills() {
      for (Color c : Color.values()) {
        for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
            initializeResults();
            ChessPiece piece = acp(row, col, c);
            setupResults(row, col, piece);
            verifyKillResults(piece);
          }
        }
      }
    }

    private void setupResults(int row, int col, ChessPiece piece) {
      //check if canMove works
      for (int i = 0; i < 8; i++) {
        fillChessResultArray(row, col, i);
      }
    }
  }

  /**
   * This class tests Queen chess piece.
   */
  public static final class QueenTest extends AbstractChessPieceTest {
    @Override
    protected ChessPiece acp(int row, int col, Color color) {
      return new Queen(row, col, color);
    }

    @Test
    public void testEachChessPieceMoves() {
      for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
          initializeResults();
          ChessPiece piece = acp(row, col, Color.BLACK);
          setupResults(row, col, piece);
          verifyMoveResults(piece);
        }
      }
    }

    @Test(timeout = 500)
    public void testEachChessPieceKills() {
      for (Color c : Color.values()) {
        for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
            initializeResults();
            ChessPiece piece = acp(row, col, c);
            setupResults(row, col, piece);
            verifyKillResults(piece);
          }
        }
      }
    }

    private void setupResults(int row, int col, ChessPiece piece) {
      //check if canMove works
      for (int i = 0; i < 8; i++) {
        results[i][col] = true;
        results[row][i] = true;
        fillChessResultArray(row, col, i);
      }
    }
  }

  /**
   * This class tests Rook chess piece.
   */
  public static final class RookTest extends AbstractChessPieceTest {
    @Override
    protected ChessPiece acp(int row, int col, Color color) {
      return new Rook(row, col, color);
    }

    @Test
    public void testEachChessPieceMoves() {
      for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
          initializeResults();
          ChessPiece piece = acp(row, col, Color.BLACK);
          setupResults(row, col, piece);
          verifyMoveResults(piece);
        }
      }
    }

    @Test(timeout = 500)
    public void testEachChessPieceKills() {
      for (Color c : Color.values()) {
        for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
            initializeResults();
            ChessPiece piece = acp(row, col, c);
            setupResults(row, col, piece);
            verifyKillResults(piece);
          }
        }
      }
    }

    private void setupResults(int row, int col, ChessPiece piece) {
      //check if canMove works
      for (int i = 0; i < 8; i++) {
        results[i][col] = true;
        results[row][i] = true;
      }
    }
  }

  /**
   * This class tests Knight chess piece.
   */
  public static final class KnightTest extends AbstractChessPieceTest {
    @Override
    protected ChessPiece acp(int row, int col, Color color) {
      return new Knight(row, col, color);
    }

    @Test
    public void testEachChessPieceMoves() {
      for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
          initializeResults();
          ChessPiece piece = acp(row, col, Color.BLACK);
          setupResults(row, col, piece);
          verifyMoveResults(piece);
        }
      }
    }

    @Test(timeout = 500)
    public void testEachChessPieceKills() {
      for (Color c : Color.values()) {
        for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
            initializeResults();
            ChessPiece piece = acp(row, col, c);
            setupResults(row, col, piece);
            verifyKillResults(piece);
          }
        }
      }
    }

    private void setupResults(int row, int col, ChessPiece piece) {
      if (row + 1 < 8 && col + 2 < 8) {
        results[row + 1][col + 2] = true;
      }
      if (row + 1 < 8 && col - 2 > -1) {
        results[row + 1][col - 2] = true;
      }
      if (row - 1 > -1 && col - 2 > -1) {
        results[row - 1][col - 2] = true;
      }
      if (row - 1 > -1 && col + 2 < 8) {
        results[row - 1][col + 2] = true;
      }
      if (row + 2 < 8 && col + 1 < 8) {
        results[row + 2][col + 1] = true;
      }
      if (row + 2 < 8 && col - 1 > -1) {
        results[row + 2][col - 1] = true;
      }
      if (row - 2 > -1 && col - 1 > -1) {
        results[row - 2][col - 1] = true;
      }
      if (row - 2 > -1 && col + 1 < 8) {
        results[row - 2][col + 1] = true;
      }
    }
  }

  protected boolean[][] results;

  @Before
  public void setup() {
    results = new boolean[8][8];
  }

  @Test(timeout = 500)
  public void testGetters() {
    ChessPiece piece;
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        for (Color c : Color.values()) {
          piece = acp(row, col, c);
          assertEquals("Row number does not match what was initialized", row,
                  piece.getRow());
          assertEquals("Column number does not match what was initialized",
                  col, piece.getColumn());
          assertEquals("solution.Color does not match what was initialized",
                  c, piece.getColor());

        }
      }
    }
  }

  @Test(timeout = 500)
  public void testInvalidConstructions() {
    ChessPiece piece;
    for (Color c : Color.values()) {
      for (int i = 0; i < 8; i++) {
        try {
          piece = acp(i, -1, c);
          fail("Did not throw an exception when" + piece.getClass().getName()
                  + "is created with invalid " + "row");
        } catch (IllegalArgumentException e) {
          //passes
        }

        try {
          piece = acp(-1, i, c);
          fail("Did not throw an exception when" + piece.getClass().getName()
                  + "is created with invalid " + "column");
        } catch (IllegalArgumentException e) {
          //passes
        }
      }
    }
  }

  protected void initializeResults() {
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        results[row][col] = false;
      }
    }
  }

  protected void verifyMoveResults(ChessPiece piece) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if ((i == piece.getRow()) && (j == piece.getColumn())) {
          continue;
        }
        assertEquals("Piece at :" + piece.getRow() + "," + piece.getColumn()
                        + ", Unexpected canMove result "
                        + "for "
                        + "i=" + i + " j="
                        + j + "",
                results[i][j], piece.canMove(i, j));

      }
    }
  }

  protected void verifyKillResults(ChessPiece piece) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {

        if ((i == piece.getRow()) && (j == piece.getColumn())) {
          continue;
        }
        ChessPiece another = acp(i, j,
                Color.values()[(piece.getColor().ordinal() + 1)
                        % Color.values().length]);

        assertEquals("Unexpected canKill result for "
                        + "i=" + i + " j="
                        + j + "",
                results[i][j], piece.canKill(another));
      }
    }
  }

  protected void fillChessResultArray(int row, int col, int i) {
    if ((row + i) < 8) {
      if ((col + i) < 8) {
        results[row + i][col + i] = true;
      }
      if (col >= i) {
        results[row + i][col - i] = true;
      }
    }

    if (row >= i) {
      if ((col + i) < 8) {
        results[row - i][col + i] = true;
      }
      if (col >= i) {
        results[row - i][col - i] = true;
      }
    }
  }

}
