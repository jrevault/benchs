package fr.quidquid.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit( TimeUnit.SECONDS)
public class Casts {

  @Benchmark
  @BenchmarkMode( Mode.Throughput )
  @OutputTimeUnit( TimeUnit.MICROSECONDS )
  public void test_cast( Blackhole blackhole ) {
    Object number = "546";
    long num = Long.parseLong( ( String ) number );
    blackhole.consume(  num );
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput )
  @OutputTimeUnit( TimeUnit.MICROSECONDS )
  public void test_concat( Cloning_vs_Constructor.MyState state, Blackhole blackhole ) {
    Object number = "546";
    long num = Long.parseLong( "" + number );
    blackhole.consume(  num );
  }

}
