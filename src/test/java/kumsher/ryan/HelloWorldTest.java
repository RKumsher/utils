package kumsher.ryan;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class HelloWorldTest {

  private final HelloWorld helloWorld = new HelloWorld();

  @Test
  public void execute() {
    assertThat(helloWorld.execute(), is("Hello World!!!"));
  }
}
