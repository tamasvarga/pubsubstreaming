/*
 Copyright Google Inc. 2018
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package demo

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.DStream


object HashTagsStreaming {
  case class Popularity(tag: String, amount: Int)

  // [START extract]
  private[demo] def extractTrendingTags(input: RDD[String]): RDD[Popularity] =
    input.flatMap(_.split("\\s+")) // Split on any white character
      .map(r => Popularity(r, 10))
      .sortBy(r => (-r.amount, r.tag), ascending = true)
  // [END extract]

  def processTrendingHashTags(input: DStream[String],
                              windowLength: Int,
                              slidingInterval: Int,
                              n: Int,
                              handler: Array[Popularity] => Unit): Unit = {
    val sortedHashtags: DStream[Popularity] = input
      .window(Seconds(windowLength), Seconds(slidingInterval)) //create a window
      .transform(extractTrendingTags(_)) //apply transformation

    sortedHashtags.foreachRDD(rdd => {
      handler(rdd.take(n)) //take top N hashtags and save to external source
    })
  }

}
