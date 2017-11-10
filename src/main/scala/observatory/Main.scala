package observatory

import org.apache.log4j.{Level, Logger}

object Main extends App {

  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

  val temps = Extraction.locateTemperatures(1975, "/stations.csv", "/1975.csv")
  val tempsAvg = Extraction.locationYearlyAverageRecords(temps)
  val currTemp = Visualization.predictTemperature(tempsAvg, Location(0, 1.5))

  val tempToCol = List[(Temperature, Color)]((60, Color(255, 255, 255)), (32, Color(255, 0, 0)),
    (12, Color(255, 255, 0)), (0, Color(0, 255, 255)), (-15, Color(0, 0, 255)), (-27, Color(255, 0, 255)),
    (-50, Color(33, 0, 107)), (-60, Color(0, 0, 0)))

  val col = Visualization.interpolateColor(tempToCol, 1)

  println(col)

}
