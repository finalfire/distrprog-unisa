import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class WordCount {
    public static void main(String[] args) throws Exception {
        // props is a map specifying different Stream execution
        // configuration values
        Properties props = new Properties();
        // StreamsConfig.APPLICATION_ID_CONFIG is the unique name of the Streams app
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
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
        KStream<String, String> source = builder.stream("streams-plaintext-input");
        // here the processing is different
        // (1) flatMapValues splits the string (treated as lower case) by whitespace
        // (2) to do the counting aggregation, we want to operate on the value of the string (the word)
        //     and not on the key, thus we use groupBy: it generates a new grouped stream,
        //     which we can then aggregate
        // (3) the count operator has a Materialized parameter that specifies that the running
        //     count should be stored in a "state store" named counts-store. This store is an
        //     instance of KTable, it can be queried in real time, and also streams can be attached to it.
        // (4) the count store can be also write back into a topic, in this case the streams-wordcount-output one
        //     note that the value type of each event is a Long, thus the defaul serialization classes cannot
        //     be used, and we need to describe with Produced how the data is serialized
        //     also, the topic should be configures with "log compaction enabled" to be read from
        source.flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
                .groupBy((_key, value) -> value)
                .count(Materialized.as("counts-store"))
                .toStream()
                .to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));

        // If we are interested in the topology (e.g., the nodes and the arcs
        // mentioned above), we create a Topology and describe() it
        final Topology topology = builder.build();
        System.out.println(topology.describe());

        // Finally, we need to create the Streams itself, to do so we need the
        // topology of our computational logic, and the props
        final KafkaStreams streams = new KafkaStreams(topology, props);
        // by calling its start function ew can trigger the execution of this client
        // note: the execution won't stop until close() is called, so we have to manage this
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
