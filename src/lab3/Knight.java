package lab3;

/**
 * It represents Knight operations.
 */
public class Knight extends AbstractChessPiece {

  /**
   * Constructs a Knight object using row, col, and color provided.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the col to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   */
  public Knight(int row, int col, Color color) {
    super(row, col, color);
  }

  @Override
  public boolean canMove(int row, int col) {
    return isInRangeToMove(row, col)
            && (Math.abs(this.getRow() - row) * Math.abs(this.getColumn() - col) == 2);
  }
}
