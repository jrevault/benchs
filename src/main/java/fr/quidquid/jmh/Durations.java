package fr.quidquid.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.SECONDS)
public class Durations {

  @Benchmark
  public void test_duration_system(Blackhole bh) {
    long start = System.currentTimeMillis();
    bh.consume(start);
    long duration =  System.currentTimeMillis() - start;
    bh.consume(duration);
  }

  @Benchmark
  public void test_duration_instant(Blackhole bh) {
    Instant start = Instant.now();
    bh.consume(start);
    Duration duration = Duration.between(start, Instant.now());
    bh.consume(duration);
  }

}
