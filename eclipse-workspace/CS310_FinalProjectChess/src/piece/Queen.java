package piece;

import main.GamePanel;

public class Queen extends Piece {
    
    public Queen(int color, int col, int row) {
        super(color, col, row);
        
        if(color == GamePanel.White) {
            image = getImage("/piece/w-queen");
        }
        else {
            image = getImage("/piece/b-queen");
        }
    }
    
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        
        if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {

            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
                if(isValidSquare(targetCol, targetRow) && !pieceOnDiagonal(targetCol, targetRow)) {
                    return true;
                }
            }

            if(targetCol == preCol || targetRow == preRow) {
                if(isValidSquare(targetCol, targetRow) && !pieceOnStraight(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
