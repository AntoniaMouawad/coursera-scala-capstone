package observatory


import org.scalacheck.Properties
import org.scalatest.{BeforeAndAfterAll, FunSuite}
import org.scalatest.prop.Checkers

trait VisualizationTest
  extends FunSuite
  with Checkers
  with BeforeAndAfterAll {

  test("antipodes simple test") {
    val a = Location(5, 10)
    val b = Location(-5, 10)

    assert(Visualization.areAntipodes(a, b))
    assert(!Visualization.areAntipodes(a, a))
  }

  test("distance simple test") {
    val a = Location(5, 10)
    val b = Location(-5, 10)

    assert(Visualization.computeDist(a, a) == 0)
    assert(Visualization.computeDist(a, b) > 0)
  }

  test("temperatures interpolation test") {
    val temps = List[(Location, Temperature)]((Location(5, 5), 3),(Location(-5, 5), 5))
    var location = Location(5, 5)

    var computed = Visualization.predictTemperature(temps, location)
    var expected = 3
    assert(computed == expected)

    location = Location(0, 3)
    computed = Visualization.predictTemperature(temps, location)
    expected = 4
    assert(computed == expected)

    location = Location(4, 4)
    computed = Visualization.predictTemperature(temps, location)
    assert(computed < 4)
  }

  test("temperatures interpolation test2") {
    val temps = List[(Location, Temperature)]((Location(90, -180), 10),(Location(80, -100), 20),
      (Location(0, 0), 20),(Location(-30, 50), 20))

    var location = Location(88, -176)
    var computed = Visualization.predictTemperature(temps, location)
    assert(computed < 15)
  }

  test("check properties") {
    check(new QuickCheckVisualization)
  }

  test("color interp one val") {
    val cols = List[(Temperature, Color)]((1, Color(2, 2, 2)))
    val value = 2

    var computed = Visualization.interpolateColor(cols, value)
    val expected = Color(2, 2, 2)
    assert(computed == expected)
  }

  test("color interp two vals") {
    val cols = List[(Temperature, Color)]((1, Color(2, 2, 2)), (-1, Color(4, 4, 4)))

    var value = 1
    var computed = Visualization.interpolateColor(cols, value)
    var expected = Color(2, 2, 2)
    assert(computed == expected)

    value = 0
    computed = Visualization.interpolateColor(cols, value)
    expected = Color(3, 3, 3)
    assert(computed == expected)
  }

}
