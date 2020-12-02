package fr.quidquid.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.SECONDS)
public class Loops {

  @State( Scope.Thread)
  public static class MyState {
    int count = 100_000;
    public List<String> list = new ArrayList<>( count );

    @Setup(Level.Trial)
    public void doSetup() {
      for(int i = 0; i < count; i++ ){
        list.add( String.valueOf( i ) );
      }
    }

    @TearDown(Level.Trial)
    public void doTearDown() {
      list.clear();
    }
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_for( MyState state, Blackhole blackhole ) {
    for(int i=0, n=state.list.size(); i < n; i++){
      Object element = state.list.get(i);
      blackhole.consume( element );
    }
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_for_each(MyState state, Blackhole blackhole) {
    for(String element : state.list ){
      blackhole.consume( element );
    }
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_for_optimized(MyState state, Blackhole blackhole) {
    for ( int i = 0, listSize = state.list.size( ) ; i < listSize ; i++ ) {
      blackhole.consume( state.list.get( i ));
    }
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_while(MyState state, Blackhole blackhole) {
    List<String> list = state.list;
    int i = 0;
    while ( i < state.list.size() ) {
      blackhole.consume( state.list.get( i ));
      i++;
    }
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_stream(MyState state, Blackhole blackhole) {
    List<String> list = state.list;
    list
        .stream()
        .sorted()
        .forEach(blackhole::consume);
  }

  @Benchmark
  @BenchmarkMode( Mode.Throughput)
  @OutputTimeUnit( TimeUnit.MICROSECONDS)
  public void test_loop_stream_parrallel(MyState state, Blackhole blackhole) {
    List<String> list = state.list;
    list
        .parallelStream()
        .sorted()
        .forEach(blackhole::consume);
  }

}
