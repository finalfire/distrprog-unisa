import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Pipe {
    public static void main(String[] args) throws Exception {
        // props is a map specifying different Stream execution
        // configuration values
        Properties props = new Properties();
        // StreamsConfig.APPLICATION_ID_CONFIG is the unique name of the Streams app
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        // StreamsConfig.BOOTSTRAP_SERVERS_CONFIG is the Kafka cluster
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // default serialization and deserialization libraries for the event
        // in the form of key-value pairs
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // A Stream application has a computational logic:
        // the computational logic consists in the steps the Stream application takes
        // to process the data; internally, it is represented as a directed graph, in which
        // nodes are the processes to be done, and arcs represents the order between the
        // processes. We can instantiate it as a StreamsBuilder
        final StreamsBuilder builder = new StreamsBuilder();

        // the source of the Stream is the topic from which we get the data
        // a KStream continously generates records from its source topic, and
        // are organized as a (k: String, v: String) pair
        KStream<String, String> source = builder.stream("pipe-in");
        KStream<String, String> lowerCase = source
                .flatMapValues(value -> Arrays.asList(value.toUpperCase()));
        // .to just write the content of the stream to a topic
        // note we can also use the Builder pattern here and write evrything as a chain
        // method => builder.stream(...).to(...)
        lowerCase.to("pipe-out");

        // If we are interested in the topology (e.g., the nodes and the arcs
        // mentioned above), we create a Topology and describe() it
        final Topology topology = builder.build();
        System.out.println(topology.describe());

        // Finally, we need to create the Streams itself, to do so we need the
        // topology of our computational logic, and the props
        final KafkaStreams stream = new KafkaStreams(topology, props);
        // by calling its start function ew can trigger the execution of this client
        // note: the execution won't stop until close() is called, so we have to manage this
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                stream.close();
                latch.countDown();
            }
        });

        try {
            stream.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
