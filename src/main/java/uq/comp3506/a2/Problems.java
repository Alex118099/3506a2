// @edu:student-assignment

package uq.comp3506.a2;

// You may wish to import more/other structures too
import uq.comp3506.a2.structures.Edge;
import uq.comp3506.a2.structures.Vertex;
import uq.comp3506.a2.structures.Entry;
import uq.comp3506.a2.structures.TopologyType;
import uq.comp3506.a2.structures.Tunnel;
import uq.comp3506.a2.structures.UnorderedMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This is part of COMP3506 Assignment 2. Students must implement their own solutions.

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 * No bounds are provided. You should maximize efficiency where possible.
 * Below we use `S` and `U` to represent the generic data types that a Vertex
 * and an Edge can have, respectively, to avoid confusion between V and E in
 * typical graph nomenclature. That is, Vertex objects store data of type `S`
 * and Edge objects store data of type `U`.
 */
public class Problems {

    /**
     * Return a double representing the minimum radius of illumination required
     * to light the entire tunnel. Your answer will be accepted if
     * |your_ans - true_ans| is less than or equal to 0.000001
     * @param tunnelLength The length of the tunnel in question
     * @param lightIntervals The list of light intervals in [0, tunnelLength];
     * that is, all light interval values are >= 0 and <= tunnelLength
     * @return The minimum radius value required to illuminate the tunnel
     * or -1 if no light fittings are provided
     * Note: We promise that the input List will be an ArrayList.
     */
    public static double tunnelLighting(int tunnelLength, List<Integer> lightIntervals) {
        if (lightIntervals == null || lightIntervals.isEmpty()) {
            return -1;
        }

        Collections.sort(lightIntervals);
        

        double maxRadius = 0.0;

        double startDistance = lightIntervals.get(0) - 0;
        maxRadius = Math.max(maxRadius, startDistance);

        for (int i = 0; i < lightIntervals.size() - 1; i++) {
            double gap = lightIntervals.get(i + 1) - lightIntervals.get(i);
            double requiredRadius = gap / 2.0;
            maxRadius = Math.max(maxRadius, requiredRadius);
        }
        

        double endDistance = tunnelLength - lightIntervals.get(lightIntervals.size() - 1);
        maxRadius = Math.max(maxRadius, endDistance);
        
        return maxRadius;
    }

    /**
     * Compute the TopologyType of the graph as represented by the given edgeList.
     * @param edgeList The list of edges making up the graph G; each is of type
     *              Edge, which stores two vertices and a value. Vertex identifiers
     *              are NOT GUARANTEED to be contiguous or in a given range.
     * @return The corresponding TopologyType.
     * Note: We promise not to provide any self loops, double edges, or isolated
     * vertices.
     */
    public static <S, U> TopologyType topologyDetection(List<Edge<S, U>> edgeList) {
        if (edgeList == null || edgeList.isEmpty()) {
            return TopologyType.UNKNOWN;
        }

        UnorderedMap<Vertex<S>, List<Vertex<S>>> adjacencyList = new UnorderedMap<>();
        UnorderedMap<Vertex<S>, Boolean> allVertices = new UnorderedMap<>();

        for (Edge<S, U> edge : edgeList) {
            Vertex<S> v1 = edge.getVertex1();
            Vertex<S> v2 = edge.getVertex2();
            
            allVertices.put(v1, true);
            allVertices.put(v2, true);
            
            List<Vertex<S>> v1Neighbors = adjacencyList.get(v1);
            if (v1Neighbors == null) {
                v1Neighbors = new ArrayList<>();
                adjacencyList.put(v1, v1Neighbors);
            }
            v1Neighbors.add(v2);
            
            List<Vertex<S>> v2Neighbors = adjacencyList.get(v2);
            if (v2Neighbors == null) {
                v2Neighbors = new ArrayList<>();
                adjacencyList.put(v2, v2Neighbors);
            }
            v2Neighbors.add(v1);
        }

        if (allVertices.isEmpty()) {
            return TopologyType.UNKNOWN;
        }

        UnorderedMap<Vertex<S>, Boolean> visited = new UnorderedMap<>();
        List<Boolean> componentHasCycle = new ArrayList<>();

        List<Vertex<S>> allVerticesList = getAllKeys(allVertices);
        for (Vertex<S> vertex : allVerticesList) {
            if (visited.get(vertex) == null) {
                UnorderedMap<Vertex<S>, Boolean> componentVertices = new UnorderedMap<>();
                boolean hasCycle = dfsDetectCycle(vertex, null, visited, componentVertices, adjacencyList);
                componentHasCycle.add(hasCycle);
            }
        }

        int numComponents = componentHasCycle.size();
        
        if (numComponents == 1) {
            return componentHasCycle.get(0) ? TopologyType.CONNECTED_GRAPH : TopologyType.CONNECTED_TREE;
        }

        boolean hasTree = false;
        boolean hasGraph = false;
        
        for (Boolean hasCycle : componentHasCycle) {
            if (hasCycle) {
                hasGraph = true;
            } else {
                hasTree = true;
            }
        }

        if (hasTree && hasGraph) {
            return TopologyType.HYBRID;
        } else if (hasTree) {
            return TopologyType.FOREST;
        } else {
            return TopologyType.DISCONNECTED_GRAPH;
        }
    }

    private static <S> boolean dfsDetectCycle(Vertex<S> vertex, Vertex<S> parent, 
                                              UnorderedMap<Vertex<S>, Boolean> visited, 
                                              UnorderedMap<Vertex<S>, Boolean> componentVertices,
                                              UnorderedMap<Vertex<S>, List<Vertex<S>>> adjacencyList) {
        visited.put(vertex, true);
        componentVertices.put(vertex, true);
        
        List<Vertex<S>> neighbors = adjacencyList.get(vertex);
        if (neighbors == null) {
            return false;
        }

        boolean hasCycle = false;
        for (Vertex<S> neighbor : neighbors) {
            if (visited.get(neighbor) == null) {
                if (dfsDetectCycle(neighbor, vertex, visited, componentVertices, adjacencyList)) {
                    hasCycle = true;
                }
            } else if (!neighbor.equals(parent)) {
                hasCycle = true;
            }
        }
        
        return hasCycle;
    }
    
    /**
     * Helper method to get all keys from an UnorderedMap (simulating Set iteration)
     */
    private static <K> List<K> getAllKeys(UnorderedMap<K, Boolean> map) {
        return map.keys();
    }
    
    /**
     * Helper method to get all keys from an UnorderedMap with any value type
     */
    private static <K, V> List<K> getAllKeysGeneric(UnorderedMap<K, V> map) {
        return map.keys();
    }
 
    /**
     * Compute the list of reachable destinations and their minimum costs.
     * @param edgeList The list of edges making up the graph G; each is of type
     *              Edge, which stores two vertices and a value. Vertex identifiers
     *              are NOT GUARANTEED to be contiguous or in a given range.
     * @param origin The origin vertex object.
     * @param threshold The total time the driver can drive before a break is required.
     * @return an ArrayList of Entry types, where the first element is the identifier
     *         of a reachable station (within the time threshold), and the second
     *         element is the minimum cost of reaching that given station. The
     *         order of the list is not important.
     * Note: We promise that S will be of Integer type.
     * Note: You should return the origin in your result with a cost of zero.
     */
    public static <S, U> List<Entry<Integer, Integer>> routeManagement(List<Edge<S, U>> edgeList,
                                                          Vertex<S> origin, int threshold) {
        ArrayList<Entry<Integer, Integer>> answers = new ArrayList<>();
        
        if (edgeList == null || edgeList.isEmpty()) {
            answers.add(new Entry<>(origin.getId(), 0));
            return answers;
        }
        
        UnorderedMap<Vertex<S>, List<Entry<Vertex<S>, Integer>>> adjacencyList = new UnorderedMap<>();
        UnorderedMap<Vertex<S>, Boolean> allVertices = new UnorderedMap<>();
        
        for (Edge<S, U> edge : edgeList) {
            Vertex<S> v1 = edge.getVertex1();
            Vertex<S> v2 = edge.getVertex2();
            Integer weight = (Integer) edge.getData();
            
            allVertices.put(v1, true);
            allVertices.put(v2, true);
            
            List<Entry<Vertex<S>, Integer>> v1Neighbors = adjacencyList.get(v1);
            if (v1Neighbors == null) {
                v1Neighbors = new ArrayList<>();
                adjacencyList.put(v1, v1Neighbors);
            }
            v1Neighbors.add(new Entry<>(v2, weight));
            
            List<Entry<Vertex<S>, Integer>> v2Neighbors = adjacencyList.get(v2);
            if (v2Neighbors == null) {
                v2Neighbors = new ArrayList<>();
                adjacencyList.put(v2, v2Neighbors);
            }
            v2Neighbors.add(new Entry<>(v1, weight));
        }
        
        allVertices.put(origin, true);
        
        UnorderedMap<Vertex<S>, Integer> distances = new UnorderedMap<>();
        
        List<Vertex<S>> allVerticesList = getAllKeys(allVertices);
        for (Vertex<S> vertex : allVerticesList) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(origin, 0);
        
        uq.comp3506.a2.structures.Heap<Integer, Vertex<S>> pq = 
            new uq.comp3506.a2.structures.Heap<>();
        pq.insert(0, origin);
        
        UnorderedMap<Vertex<S>, Boolean> visited = new UnorderedMap<>();
        
        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<S>> current = pq.removeMin();
            int currentDist = current.getKey();
            Vertex<S> currentVertex = current.getValue();
            
            if (visited.get(currentVertex) != null) {
                continue;
            }
            visited.put(currentVertex, true);
            
            if (currentDist > threshold) {
                continue;
            }
            
            List<Entry<Vertex<S>, Integer>> neighbors = adjacencyList.get(currentVertex);
            if (neighbors == null) {
                continue;
            }
            
            for (Entry<Vertex<S>, Integer> neighbor : neighbors) {
                Vertex<S> nextVertex = neighbor.getKey();
                int edgeWeight = neighbor.getValue();
                int newDist = currentDist + edgeWeight;
                
                if (newDist < distances.get(nextVertex)) {
                    distances.put(nextVertex, newDist);
                    if (newDist <= threshold) {
                        pq.insert(newDist, nextVertex);
                    }
                }
            }
        }
        
        List<Vertex<S>> allDistanceKeys = getAllKeysGeneric(distances);
        for (Vertex<S> vertex : allDistanceKeys) {
            Integer dist = distances.get(vertex);
            if (dist != null && dist <= threshold && dist != Integer.MAX_VALUE) {
                answers.add(new Entry<>(vertex.getId(), dist));
            }
        }
        
        return answers;
    }

    /**
     * Compute the tunnel that if flooded will cause the maximal flooding of 
     * the network
     * @param tunnels A list of the tunnels to consider; see Tunnel.java
     * @return The identifier of the Tunnel that would cause maximal flooding.
     * Note that for Tunnel A to drain into some other tunnel B, the distance
     * from A to B must be strictly less than the radius of A plus an epsilon
     * allowance of 0.000001. 
     * Note also that all identifiers in tunnels are GUARANTEED to be in the
     * range [0, n-1] for n unique tunnels.
     */
    public static int totallyFlooded(List<Tunnel> tunnels) {
        return -1;
    }

    /**
     * Compute the number of sites that cannot be infiltrated from the given starting sites.
     * @param sites The list of unique site identifiers. A site identifier is GUARANTEED to be
     *              non-negative, starting from 0 and counting upwards to n-1.
     * @param rules The infiltration rule. The right-hand side of a rule is represented by a list
     *             of lists of site identifiers (as is done in the assignment specification). The
     *             left-hand side of a rule is given by the rule's index in the parameter `rules`
     *             (i.e. the rule whose left-hand side is 4 will be at index 4 in the parameter
     *              `rules` and can be accessed with `rules.get(4)`).
     * @param startingSites The list of site identifiers to begin your infiltration from.
     * @return The number of sites which cannot be infiltrated.
     */
    public static int susDomination(List<Integer> sites, List<List<List<Integer>>> rules,
                                     List<Integer> startingSites) {
        return -1;
    }
}
