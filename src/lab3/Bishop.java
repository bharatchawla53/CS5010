package lab3;

/**
 * It represents Bishop operations.
 */
public class Bishop extends AbstractChessPiece {

  /**
   * Constructs a Bishop object using row, col, and color provided.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the col to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   */
  public Bishop(int row, int col, Color color) {
    super(row, col, color);
  }

  @Override
  public boolean canMove(int row, int col) {
    return isInRangeToMove(row, col)
            && (Math.abs(this.getRow() - row) == Math.abs(this.getColumn() - col));
  }

}
