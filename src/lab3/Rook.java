package lab3;

/**
 * It represents Rook operations.
 */
public class Rook extends AbstractChessPiece {

  /**
   * Constructs a Rook object using row, col, and color provided.
   *
   * @param row   the row to which this chess piece can be moved.
   * @param col   the col to which this chess piece can be moved.
   * @param color the color of the chess piece checking for.
   */
  public Rook(int row, int col, Color color) {
    super(row, col, color);
  }

  @Override
  public boolean canMove(int row, int col) {
    return isInRangeToMove(row, col)
            && ((this.getRow() == row) || (this.getColumn() == col));
  }

}
