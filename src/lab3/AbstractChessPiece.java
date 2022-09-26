package lab3;

/**
 * Abstract base class for implementations of {@link ChessPiece}. This class contains all the method
 * definitions that are common to the concrete implementations of the {@link ChessPiece} interface.
 * A new implementation of the interface has the option of extending this class, or directly
 * implementing all the methods.
 */
public abstract class AbstractChessPiece implements ChessPiece {
  private final int row;
  private final int col;
  private final Color color;

  /**
   * Constructs a AbstractChessPiece object using row, col, and color provided.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the col to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   * @throws IllegalArgumentException for any values less than zero.
   */
  protected AbstractChessPiece(int row, int col, Color color) throws IllegalArgumentException {
    if ((row < 0) || (col < 0)) {
      throw new IllegalArgumentException("Illegal position");
    }
    this.row = row;
    this.col = col;
    this.color = color;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getColumn() {
    return col;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public boolean canKill(ChessPiece piece) {
    return (this.getColor() != piece.getColor()) && canMove(
            piece.getRow(),
            piece.getColumn());
  }

  /**
   * Checks if the provided row and col are withing the range of the chess piece.
   *
   * @param row the row to which this chess piece can be moved.
   * @param col the col to which this chess piece can be moved.
   * @return true if it is in range to this position, false otherwise.
   */
  protected static boolean isInRangeToMove(int row, int col) {
    return (row >= 0) && (col >= 0) && (row < 8) && (col < 8);
  }

}
