package at.fhooe.ai.rushhour;
/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {

    private Puzzle puzzle;

    /**
     * This is the required constructor, which must be of the given form.
     */
    public AdvancedHeuristic(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {
        if (state.isGoal())
            return 0;

        int count = 1;
        int cx = state.getVariablePosition(0);
        int cy = puzzle.getFixedPosition(0);

        int grid[][] = state.getGrid();
        int gn = puzzle.getGridSize();

        for (int i = cx; i < gn; i++) {
            for (int j = 0; j < gn; j++) {
                if (grid[i][j] != -1)
                    count++;
            }
        }

        return count;
    }

}
