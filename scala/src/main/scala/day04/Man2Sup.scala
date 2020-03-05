package day04

object Man2Sup {
  implicit def man2SuperMan(man:Man):superman={
    new superman(man.name)
  }
}
