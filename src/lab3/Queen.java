package lab3;

/**
 * It represents Queen operations.
 */
public class Queen extends AbstractChessPiece {

  /**
   * Constructs a Queen object using row, col, and color provided.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the col to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   */
  public Queen(int row, int col, Color color) {
    super(row, col, color);
  }

  @Override
  public boolean canMove(int row, int col) {
    return isInRangeToMove(row, col) && ((this.getRow() == row) || (this.getColumn() == col)
            || (Math.abs(this.getRow() - row) == Math.abs(this.getColumn() - col)));
  }

}
