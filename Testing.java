import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Testing {

    public static void main(String[] args) throws IOException {
        File vertices = new File("nodes.csv");
        File edges = new File("edges.csv");
        A4_2019MT60628 G = new A4_2019MT60628();

        Scanner inputStreamv = new Scanner(vertices);
        Scanner inputStreame = new Scanner(edges);

        inputStreamv.useDelimiter("\n");
        inputStreamv.next();
        while (inputStreamv.hasNext()) {
            String data = inputStreamv.next();
            // values separated by commas not in quotes
            String[] values = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            values[1] = values[1].replaceAll("^\"|\"$", "");
            G.insertV(values[1].trim());
        }
        inputStreame.useDelimiter("\n");
        inputStreame.next();

        while (inputStreame.hasNext()) {
            String data = inputStreame.next();
            String[] values = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int i = 0; i < 2; i++) {
                values[i] = values[i].replaceAll("^\"|\"$", "");
            }
            A4_2019MT60628.Edge e = new A4_2019MT60628.Edge(values[0].trim(), values[1].trim(), Integer.parseInt(values[2].trim()));
            G.insertE(e);
        }

        G.DFS_visit(G.getVertexArray().get(0));


    }



}

