package at.fhooe.ai.rushhour;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.lang.Math;

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

    //private HashMap<NodewH, Integer> open;
    //private PriorityQueue<NodewH> open;
    private ArrayList<nNode> open;
    private HashSet<nNode> closed;

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {

        open = new ArrayList();

        closed = new HashSet<nNode>();

        Node inode = puzzle.getInitNode();
        open.add(new nNode(inode, heuristic.getValue(inode.getState())));

        while (!open.isEmpty()) {

            Collections.sort(open);
            nNode current = open.remove(0);

            if (closed.contains(current))
                continue;

            closed.add(current);

            if (current.getState().isGoal()) {

                path = new State[current.getDepth() + 1];

                // reconstruct path
                Node node = current;
                while (node != null) {
                    path[node.getDepth()] = node.getState();
                    node = node.getParent();
                }
                break;
            }

            for (Node no : current.expand()) {
                int score = no.getDepth() + heuristic.getValue(no.getState());
                nNode n = new nNode(no, score);

                if (closed.contains(n))
                    continue;

                int i = open.indexOf(n);
                if (i != -1 && open.get(i).compareTo(n) > 1) {
                    open.remove(i);
                    open.add(n);
                }
                else
                    open.add(n);
            }
        }
    }

    // new Node class
    private class nNode extends Node implements Comparable<nNode> {

        private int d;

        public nNode(Node n, int d) {
            super(n.getState(), n.getDepth(), n.getParent());
            this.d = d;
        }

        @Override
        public int hashCode() {
            return this.getState().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;

            return this.hashCode() == ((nNode)o).hashCode();
        }

        @Override
        public int compareTo(nNode n) {
            return Integer.compare(this.d, n.d);
        }

    }

}
