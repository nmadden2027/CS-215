package piece;

import main.GamePanel;

public class Pawn extends Piece {
    
    public Pawn(int color, int col, int row) {
        super(color, col, row);
        
        if(color == GamePanel.White) {
            image = getImage("/piece/w-pawn");
        }
        else {
            image = getImage("/piece/b-pawn");
        }
    }
    
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        
        if(!isWithinBoard(targetCol, targetRow) || isSameSquare(targetCol, targetRow)) {
            return false;
        }

        int moveDirection = (color == GamePanel.White) ? -1 : 1;
        

        int startRow = (color == GamePanel.White) ? 6 : 1;
        
        if(targetCol == preCol && targetRow == preRow + moveDirection) {
            if(getHittingP(targetCol, targetRow) == null) {
                return true;
            }
        }

        if(targetCol == preCol && targetRow == preRow + (moveDirection * 2) && preRow == startRow) {
            if(getHittingP(targetCol, targetRow) == null && 
               getHittingP(targetCol, preRow + moveDirection) == null) {
                return true;
            }
        }

        if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveDirection) {
            hittingP = getHittingP(targetCol, targetRow);
            if(hittingP != null && hittingP.color != this.color) {
                return true;
            }
        }
        
        return false;
    }
}