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
 
package spark.pubsub.example.wordcount;

import com.google.cloud.hadoop.util.EntriesCredentialConfiguration;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Milliseconds;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.pubsub.PubsubUtils;
import org.apache.spark.streaming.pubsub.SparkGCPCredentials;
import org.apache.spark.streaming.pubsub.SparkPubsubMessage;

import scala.Tuple2;

public class CloudPubSubStreamingWordCount {

  public static void main(String[] args) throws InterruptedException {
    JavaStreamingContext jsc = new JavaStreamingContext(
        new SparkConf().setAppName("Cloud PubSub Spark Streaming Word Count"),
        Milliseconds.apply(500) // Batch duration
    );

      JavaReceiverInputDStream<SparkPubsubMessage> pubSubStream = PubsubUtils.createStream(
        jsc,
        "nkm-rtd", // GCP project ID
        "tweets-subscription", // Cloud PubSub subscription
        new SparkGCPCredentials.Builder().build(),
        StorageLevel.MEMORY_AND_DISK_SER());

      pubSubStream.map(msg ->  new String(msg.getData(), StandardCharsets.UTF_8)).print();

    try {
      jsc.start();
      // Let the job run for the given duration and then terminate it.
      jsc.awaitTermination();
    } finally {
      jsc.stop(true, true);
    }
  }
}
