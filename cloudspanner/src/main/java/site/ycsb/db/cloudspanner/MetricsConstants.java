/**
 * Copyright (c) 2017 YCSB contributors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License. See accompanying
 * LICENSE file.
 */
package site.ycsb.db.cloudspanner;

import io.opencensus.stats.Aggregation;
import io.opencensus.stats.Aggregation.Distribution;
import io.opencensus.stats.BucketBoundaries;
import io.opencensus.stats.Measure.MeasureLong;
import io.opencensus.stats.View;
import io.opencensus.stats.View.Name;
import io.opencensus.tags.TagKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * OpenCensus metric constants for Cloud Spanner.
 */
public final class MetricsConstants {

  private MetricsConstants() {
  }

  private static final String MILLISECOND = "ms";

  private static final List<Double> RPC_MILLIS_BUCKET_BOUNDARIES =
      Collections.unmodifiableList(
          Arrays.asList(
              0.0, 0.01, 0.05, 0.1, 0.3, 0.6, 0.8, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 8.0, 10.0, 13.0,
              16.0, 20.0, 25.0, 30.0, 40.0, 50.0, 65.0, 80.0, 100.0, 130.0, 160.0, 200.0, 250.0,
              300.0, 400.0, 500.0, 650.0, 800.0, 1000.0, 2000.0, 5000.0, 10000.0, 20000.0, 50000.0,
              100000.0));

  private static final Aggregation AGGREGATION_WITH_MILLIS_HISTOGRAM =
      Distribution.create(BucketBoundaries.create(RPC_MILLIS_BUCKET_BOUNDARIES));

  static final TagKey GRPC_CLIENT_METHOD_KEY = TagKey.create("grpc_client_method");

  static final MeasureLong SPANNER_GFE_LATENCY =
      MeasureLong.create(
          "cloud.google.com/java/spanner/gfe_latency",
          "Latency between Google's network receives an RPC and reads back the first byte of the response",
          MILLISECOND);

  static final View SPANNER_GFE_LATENCY_VIEW  = View.create(Name.create("cloud.google.com/java/spanner/gfe_latency"),
      "Latency between Google's network receives an RPC and reads back the first byte of the response",
      SPANNER_GFE_LATENCY,
      AGGREGATION_WITH_MILLIS_HISTOGRAM,
      Collections.singletonList(GRPC_CLIENT_METHOD_KEY));
}
