/*
 * Copyright 2017 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Tools.DataStoreConnector;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.pubsub.PubsubUtils;
import org.apache.spark.streaming.pubsub.SparkGCPCredentials;
import org.apache.spark.streaming.pubsub.SparkPubsubMessage;

import java.nio.charset.StandardCharsets;

public class EventStreaming {

    public static void main(String[] args) throws InterruptedException {
        JavaStreamingContext jsc = new JavaStreamingContext(
                new SparkConf().setAppName("Cloud PubSub Spark Streaming Word Count"),
                Seconds.apply(30) // Batch duration
        );

        JavaReceiverInputDStream<SparkPubsubMessage> pubSubStream = PubsubUtils.createStream(
                jsc,
                "nkm-rtd", // GCP project ID
                "tweets-subscription2", // Cloud PubSub subscription
                new SparkGCPCredentials.Builder().build(),
                StorageLevel.MEMORY_AND_DISK_SER());

//        JavaDStream<Event> map = pubSubStream.map(msg -> new Event(new String(msg.getData(), StandardCharsets.UTF_8)));
//        map.mapPartitions(RuleExecutor::Evulate).map(Event::GetActions).print();
        pubSubStream.map(msg -> new String(msg.getData(), StandardCharsets.UTF_8)).foreachRDD(rdd->{
            rdd.foreach(msg->{
               System.out.println("Reached RDD");
            });
        });

        try {
            jsc.start();
            // Let the job run for the given duration and then terminate it.
            jsc.awaitTermination();
        } finally {
            jsc.stop(true, true);
        }
    }
}
