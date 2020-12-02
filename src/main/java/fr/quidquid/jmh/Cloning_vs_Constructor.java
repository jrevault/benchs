package fr.quidquid.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Cloning_vs_Constructor {

  @State( Scope.Thread )
  public static class MyState {
    public static GridCloneable gridCloneable = new GridCloneable();
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput )
  @OutputTimeUnit( TimeUnit.MICROSECONDS )
  public void test_constructor( Blackhole blackhole ) {
    Grid grid = new Grid();
    blackhole.consume(  grid );
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput )
  @OutputTimeUnit( TimeUnit.MICROSECONDS )
  public void test_clone( MyState state, Blackhole blackhole ) throws CloneNotSupportedException {
    GridCloneable grid = state.gridCloneable.clone();
    blackhole.consume(  grid );
  }

  static class GridCloneable implements Cloneable {
    int pos = 0;

    @Override
    protected GridCloneable clone() throws CloneNotSupportedException {
      return ( GridCloneable ) super.clone( );
    }
  }

  static class Grid {
    int pos = 0;

  }

}
