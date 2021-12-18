import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.Scanner;

public class A4_2019MT60628 {

    private ArrayList<Vertex> vertexArray;
    private ArrayList<Edge> edgeArray;


    public A4_2019MT60628() {
        ArrayList<Vertex> vertexArray = new ArrayList<Vertex>();
        ArrayList<Edge> edgeArray = new ArrayList<Edge>();
        this.vertexArray = vertexArray;
        this.edgeArray = edgeArray;
    }

    public ArrayList<Vertex> getVertexArray() {
        return vertexArray;
    }


    public static class Vertex {
        private String id;
        private LinkedList<Vertex> adj;
        private int occur;
        private int compNum;
        private String color;

        public Vertex(String id) {
            this.id = id;
            this.adj = new LinkedList<Vertex>();
            this.occur = 0;
        }
        // number of distinct vertices our vertex is adjacent to
        public int getDegree() {
            return adj.size();
        }

        public String getId() {
            return id;
        }
    }

    public static class Edge {
        private Vertex v1;
        private Vertex v2;
        private int weight;

        public Edge(String s1,String s2,int weight) {
            Vertex v1 = new Vertex(s1);
            Vertex v2 = new Vertex(s2);
            this.weight = weight;
            this.v1 = v1;this.v2 = v2;
        }

        public Vertex getV1() {
            return v1;
        }

        public Vertex getV2() {
            return v2;
        }
    }

    // Insert an edge e in the edge array
    public void insertE(Edge e) {
        edgeArray.add(e);
        Vertex v_1 = new Vertex("");
        Vertex v_2 = new Vertex("");
        for (Vertex w : vertexArray) {
            if (w.getId().equals(e.getV1().getId())) {
                v_1 = w;
            }
        }
        for (Vertex w : vertexArray) {
            if (w.getId().equals(e.getV2().getId())) {
                v_2 = w;
            }
        }
        v_1.adj.add(v_2);
        v_2.adj.add(v_1);
        v_1.occur = v_1.occur + e.weight;
        v_2.occur = v_2.occur + e.weight;
    }

    public void insertV(String s) {
        Vertex v = new Vertex(s);
        for (Vertex w : vertexArray) {
            if (w.getId().equals(v.getId())) return;
        }
        vertexArray.add(v);
    }

    public void setGraph(String filename1,String filename2) {
        File vertices = new File(filename1);
        File edges = new File(filename2);

        try {
            Scanner inputStreamv = new Scanner(vertices);
            Scanner inputStreame = new Scanner(edges);

            inputStreamv.useDelimiter("\n");
            if (!inputStreamv.hasNext()) return;
            inputStreamv.next();
            while (inputStreamv.hasNext()) {
                String data = inputStreamv.next();
                // values separated by commas not in quotes
                String[] values = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                values[1] = values[1].replaceAll("^\"|\"$","");
                insertV(values[1]);
            }
            inputStreamv.close();

            inputStreame.useDelimiter("\n");
            if (!inputStreame.hasNext()) return;
            inputStreame.next();
            while (inputStreame.hasNext()) {
                String data = inputStreame.next();
                String[] values = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < 3 ; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$","");
                }
                Edge e = new Edge(values[0], values[1], Integer.parseInt(values[2]));
                insertE(e);
            }
            inputStreame.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public float average() {
        float sum = 0;
        float n = vertexArray.size();
        for (Vertex v :vertexArray) {
            sum = sum + v.getDegree();
        }
        float avg = sum/n;
        return avg;
    }

    public boolean lexicographicIsLessThan(Vertex v1, Vertex v2) {
        if (v1.getId().compareTo(v2.getId()) < 0) {
            return true;
        }
        return false;
    }

    public boolean arrayListLessThan(ArrayList<Vertex> L1,ArrayList<Vertex> L2) {

        if (L1.size() < L2.size()) return true;
        if (L1.size() == L2.size()) {
            int i = 0;
            // L1 and L2 are already sorted in lexicographic descending order of charName
            while (L1.get(i).getId().compareTo(L2.get(i).getId()) == 0) {
                i++;
            }
            if (lexicographicIsLessThan(L1.get(i),L2.get(i))) return true;
        }
        return false;
    }

    public boolean IsLessThan(Vertex v1,Vertex v2) {
        if (v1.occur < v2.occur) return true;
        if (v1.occur == v2.occur) {
            if (v1.getId().compareTo(v2.getId()) < 0) {
                return true;
            }
        }
        return false;
    }

    public Vertex[] merge(Vertex[] arr1,Vertex[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;

        int i = 0;int j = 0;int k =0;
        Vertex[] merged = new Vertex[n1+n2];
        while (i < n1 & j < n2) {
            if (IsLessThan(arr1[i],arr2[j])) {
                merged[k] = arr1[i];
                i++;k++;
            }
            else {
                merged[k] = arr2[j];
                j++;k++;
            }
        }
        if (i == n1) {
            for (int l = j;l < n2;l++) {
                merged[k] = arr2[l];
                k++;
            }
        }
        if (j == n2) {
            for (int l = i;l < n1;l++) {
                merged[k] = arr1[l];
                k++;
            }
        }
        return merged;
    }

    public Vertex[] mergeSort(Vertex[] arr) {

        int n = arr.length;
        if (n == 1 | n == 0) {
            return arr;
        }
        int k = n/2;
        Vertex[] temp1 = new Vertex[k];
        Vertex[] temp2 = new Vertex[n-k];
        for (int i = 0;i < k;i++) { temp1[i] = arr[i]; }
        for (int j = k;j < n;j++) { temp2[j-k] = arr[j]; }

        return merge(mergeSort(temp1),mergeSort(temp2));
    }

    public Vertex[] mergeV(Vertex[] arr1,Vertex[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;

        int i = 0;int j = 0;int k =0;
        Vertex[] merged = new Vertex[n1+n2];
        while (i < n1 & j < n2) {
            if (!lexicographicIsLessThan(arr1[i],arr2[j])) {
                merged[k] = arr1[i];
                i++;k++;
            }
            else {
                merged[k] = arr2[j];
                j++;k++;
            }
        }
        if (i == n1) {
            for (int l = j;l < n2;l++) {
                merged[k] = arr2[l];
                k++;
            }
        }
        if (j == n2) {
            for (int l = i;l < n1;l++) {
                merged[k] = arr1[l];
                k++;
            }
        }
        return merged;
    }

    public Vertex[] mergeSortV(Vertex[] arr) {

        int n = arr.length;
        if (n == 1 | n == 0) {
            return arr;
        }
        int k = n/2;
        Vertex[] temp1 = new Vertex[k];
        Vertex[] temp2 = new Vertex[n-k];
        for (int i = 0;i < k;i++) { temp1[i] = arr[i]; }
        for (int j = k;j < n;j++) { temp2[j-k] = arr[j]; }

        return mergeV(mergeSortV(temp1),mergeSortV(temp2));
    }

    public void rank() {
        int n = vertexArray.size();
        Vertex[] vertices = new Vertex[n];
        int i = 0;
        for (Vertex v : vertexArray) {
            vertices[i] = v;i++;
        }
        vertices = mergeSort(vertices);
        for (int k = n-1; k >= 0; k-- ) {
            if (k == 0) System.out.print((vertices[k].getId()));
            else System.out.print((vertices[k].getId()) + ",");
        }
    }

    public ArrayList<Vertex>[] mergeVA(ArrayList<Vertex>[] arr1,ArrayList<Vertex>[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;

        int i = 0;int j = 0;int k =0;
        ArrayList<Vertex>[] merged = new ArrayList[n1+n2];
        while (i < n1 & j < n2) {
            if (!arrayListLessThan(arr1[i],arr2[j])) {
                merged[k] = arr1[i];
                i++;k++;
            }
            else {
                merged[k] = arr2[j];
                j++;k++;
            }
        }
        if (i == n1) {
            for (int l = j;l < n2;l++) {
                merged[k] = arr2[l];
                k++;
            }
        }
        if (j == n2) {
            for (int l = i;l < n1;l++) {
                merged[k] = arr1[l];
                k++;
            }
        }
        return merged;
    }

    public ArrayList<Vertex>[] mergeSortVA(ArrayList<Vertex>[] arr) {
        int n = arr.length;
        if (n == 1 | n == 0) {
            return arr;
        }
        int k = n/2;
        ArrayList<Vertex>[] temp1 = new ArrayList[k];
        ArrayList<Vertex>[] temp2 = new ArrayList[n-k];
        for (int i = 0;i < k;i++) { temp1[i] = arr[i]; }
        for (int j = k;j < n;j++) { temp2[j-k] = arr[j]; }

        return mergeVA(mergeSortVA(temp1), mergeSortVA(temp2));
    }

    public int DFS() {
        for (Vertex v : vertexArray) {
            v.color = "white";
            v.compNum = -1;
        }
        int numComps = 0;
        for (Vertex v : vertexArray) {
            if (v.color.equals("white")) {
                v.compNum = numComps;
                numComps++;
                DFS_visit(v);
            }
        }
        return numComps;
    }

    public void DFS_visit(Vertex v) {
        v.color = "grey";
        int compNum = v.compNum;
        for (Vertex w : v.adj) {
            if (w.color.equals("white")) {
                w.compNum = compNum;
                DFS_visit(w);
            }
        }
        v.color = "black";
    }

    public void independent_storylines_dfs() {
        int numComp = DFS();
        int k;
        ArrayList<Vertex>[] components = new ArrayList[numComp];
        for (int i = 0; i< numComp ; i++) {
            components[i] = new ArrayList<>();
        }
        for (Vertex v : vertexArray) {
            k = v.compNum;
            components[k].add(v);
        }

        // sort each component in components array
        for (int i = 0;i < numComp ; i++) {
            int j = 0;
            Vertex[] component = new Vertex[components[i].size()];
            for (Vertex v : components[i]) {
                component[j] = v;j++;
            }
            component = mergeSortV(component);      // vertices are sorted in descending order
            ArrayList<Vertex> auxCompList = new ArrayList<>();
            for (Vertex v : component) {
                auxCompList.add(v);
            }
            components[i] = auxCompList;
        }
        // each component is sorted in lexicographically descending order
        components = mergeSortVA(components);
        // components is sorted in descending order
        boolean isFirst = true;
        for (ArrayList<Vertex> comp : components) {
            for (Vertex v : comp) {
                if (isFirst) {
                    System.out.print(v.getId());
                    isFirst = false;
                }
                else System.out.print("," + v.getId());
            }
            isFirst = true;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        A4_2019MT60628 G = new A4_2019MT60628();
        G.setGraph(args[0],args[1]);
        if (args[2].equals("average")) {
            float avg = G.average();
            avg = Math.round(avg * 100) / 100f;
            System.out.printf("%.2f",avg);
            System.out.println();
        }
        if (args[2].equals("rank")) {
            G.rank();
            System.out.println();
        }
        if (args[2].equals("independent_storylines_dfs")) {
            G.independent_storylines_dfs();
        }
    }
}

