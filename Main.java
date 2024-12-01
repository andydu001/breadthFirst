/*
This program gets the basic information about different GPUs and GPU devices.

 */

package org.example;

import com.google.common.graph.*;
import org.uma.jmetal.lab.visualization.html.Html;
import org.uma.jmetal.lab.visualization.html.HtmlComponent;
import com.nvidia.spark.rapids.tool.A10GGpu$;
import com.nvidia.spark.rapids.tool.GpuDevice$;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {


        ValueGraphBuilder builder = ValueGraphBuilder.undirected();

        MutableValueGraph mutableValueGraph = builder.allowsSelfLoops(true).expectedNodeCount(6).build();

        mutableValueGraph.putEdgeValue("Boston", "California", "City");
        mutableValueGraph.putEdgeValue("Houston", "Dallas", "City");
        mutableValueGraph.putEdgeValue("Boston", "New York", "City");
        mutableValueGraph.putEdgeValue("Houston", "New York", "City");
        mutableValueGraph.putEdgeValue("Boston", "Houston", "City");
        mutableValueGraph.putEdgeValue("New York", "Houston", "City");
        mutableValueGraph.putEdgeValue("New York", "Boston", "City");
        mutableValueGraph.putEdgeValue("Dallas", "Houston", "City");
        mutableValueGraph.putEdgeValue("Dallas", "New York", "City");
        mutableValueGraph.putEdgeValue("Dallas", "Boston", "City");
        mutableValueGraph.putEdgeValue("Santa Fe", "California", "City");
        mutableValueGraph.putEdgeValue("Santa Fe", "New York", "City");
        mutableValueGraph.putEdgeValue("Santa Fe", "Dallas", "City");
        mutableValueGraph.putEdgeValue("Santa Fe", "Miami", "City");
        mutableValueGraph.putEdgeValue("Miami", "Dallas", "City");
        mutableValueGraph.putEdgeValue("Miami", "Denver", "City");
        mutableValueGraph.putEdgeValue("Miami", "San Francisco", "City");
        mutableValueGraph.putEdgeValue("Miami", "Los Angeles", "City");
        mutableValueGraph.putEdgeValue("Miami", "San Diego", "City");
        mutableValueGraph.putEdgeValue("Miami", "Chicago", "City");
        mutableValueGraph.putEdgeValue("Miami", "Minneapolis", "City");
        mutableValueGraph.putEdgeValue("Miami", "Detroit", "City");
        mutableValueGraph.putEdgeValue("Miami", "San Jose", "City");
        mutableValueGraph.putEdgeValue("Denver", "Chicago", "City");
        mutableValueGraph.putEdgeValue("Denver", "Minneapolis", "City");


        mutableValueGraph.edgeValue("Miami", "Dallas");
        mutableValueGraph.edgeValue("Miami", "Detroit");
        mutableValueGraph.edgeValue("Miami", "San Francisco");
        mutableValueGraph.edgeValue("Miami", "Los Angeles");
        mutableValueGraph.edgeValue("Miami", "San Diego");
        mutableValueGraph.edgeValue("Miami", "Chicago");
        mutableValueGraph.edgeValue("Miami", "Minneapolis");

        if(mutableValueGraph.addNode("Philadelphia")){

            System.out.println("Node already exists");
        }
        
        if(mutableValueGraph.hasEdgeConnecting("Boston", "Houston")){


            System.out.println("There is direct flight from Houston to Boston");
        }
        else{
            System.out.println("No direct flight from Houston to Boston");
        }




        MutableGraph<Object> m =  Graphs.copyOf(mutableValueGraph.asGraph());

        System.out.println(m.edges());

        Graphs.transpose(mutableValueGraph.asGraph());


        Traverser travers = Traverser.forGraph(mutableValueGraph);

        /*new SuccessorsFunction<Object>() {
            @Override
            public Iterable<?> successors(Object node) {
                return Collections.singleton(new Scanner(System.in));
            }
        });*/



        travers.breadthFirst("Miami").forEach(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        });

        travers.depthFirstPreOrder("Boston").forEach(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("_______________________________________________________");
                System.out.println(o);
            }
        });




        A10GGpu$ a10gpu = A10GGpu$.MODULE$;


        GpuDevice$ gpuDevice = GpuDevice$.MODULE$;

        Html html = new Html();


        html.addComponent(new HtmlComponent() {
            @Override
            public String getHtml() {


                return   "<h1>Graph by Andy Duverneau!</h1>"


                        + "<p>This is an example of a simple HTML component.</p>"
                        + "<table>"
                        + "<tr>"
                        + "<th>Column 1</th>"
                        + "<th>Column 2</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Row 1, Column 1</td>"
                        + "<td>Row 1, Column 2</td>"
                        + "</tr>"
                        + "<tr>"
                        +mutableValueGraph.successors("Miami")+ "</tr>"
                        + "<tr>"
                        + a10gpu.productPrefix()+ "</tr>"
                        +a10gpu.getInitialPartitionNum()
                        + "<tr>"
                        +mutableValueGraph.predecessors("Miami")
                        + "<td>Row 2, Column 1</td>"
                        +mutableValueGraph.inDegree("Boston")
                        + "<td>, Column 2</td>"
                        + "</tr>"
                        +gpuDevice.deviceMap().seq()
                        + "</table>"+mutableValueGraph.nodes()+"</>"
                        + "</table>"+ mutableValueGraph.hasEdgeConnecting("Boston", "Houston");
            }

            @Override
            public String getCSS() {
                return null;
            }
        });
        html.show();

    }
}