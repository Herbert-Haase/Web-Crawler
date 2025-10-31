case class Site(name: String, url: String) {
  def getName: String = name
}

case class Spider(links: Int , current:Site) {
  def isSet: Boolean = links != 0
}

val site1 = Site("google","google.com")
val site2 = Site("youtube","youtube.com")

val spider1 = Spider(2,site1)
val spider2 = Spider(0,site2)

spider1.isSetc
spider2.isSet

site1.getName
site2.getName