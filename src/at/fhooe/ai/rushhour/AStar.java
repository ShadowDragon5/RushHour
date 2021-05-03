package at.fhooe.ai.rushhour;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * This is the template for a class that performs A* search on a given rush hour
 * puzzle with a given heuristic. The main search computation is carried out by
 * the constructor for this class, which must be filled in. The solution (a path
 * from the initial state to a goal state) is returned as an array of
 * <tt>State</tt>s called <tt>path</tt> (where the first element
 * <tt>path[0]</tt> is the initial state). If no solution is found, the
 * <tt>path</tt> field should be set to <tt>null</tt>. You may also wish to
 * return other information by adding additional fields to the class.
 */
public class AStar {

    /** The solution path is stored here */
    public State[] path;

    private PriorityQueue< SimpleEntry<Node, Integer> > open;
    private HashMap<Node, Integer> closed;

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {

        ArrayList<State> new_path = new ArrayList<State>();

        open = new PriorityQueue(1, new Comparator<SimpleEntry>() {
            public int compare(SimpleEntry e1, SimpleEntry e2) {
                return Integer.compare((int)e1.getValue(), (int)e2.getValue());
            }
        });

        closed = new HashMap<Node, Integer>();

        Node inode = puzzle.getInitNode();
        open.add(new SimpleEntry<Node, Integer>(inode,
                    inode.getDepth() + heuristic.getValue(inode.getState())));

        int a = 0;
        while (!open.isEmpty()) {

            SimpleEntry<Node, Integer> current = open.poll();

            if (closed.containsKey(current))
                continue;

            closed.put(current.getKey(), current.getValue());

            if (current.getKey().getState().isGoal()) {

                // reconstruct path
                Node node = current.getKey();
                while (!node.getState().equals(inode.getState())) {
                    new_path.add(node.getState());
                    node = node.getParent();
                }
                path = new State[new_path.size()];
                path = new_path.toArray(path);

                break;
            }

            for (Node n : current.getKey().expand()) {
                if (closed.containsKey(n))
                    continue;

                int score = n.getDepth() + heuristic.getValue(n.getState());
                open.add(new SimpleEntry<Node, Integer>(n, score));
            }
        }
    }

}
