package code {
	package lib {

		import org.xml.sax.InputSource
		import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
		import scala.xml._

		class NaturalCrawl(urlFile: String) {
			if (urlFile == null) throw new Exception("URL is null")

			val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
			val parser = parserFactory.newSAXParser()
			val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
			var xml = NodeSeq.Empty

			def parseUrl(url: String) = {
				try {
					val source = new org.xml.sax.InputSource(url)
					xml = adapter.loadXML(source, parser)
					mainTask(url);
				}
				catch {
					case e => println("url down => " + url)
				}

			}

			def parseFile() = {
				val lines = scala.io.Source.fromFile(urlFile).mkString
				val decoup = lines.split("\\n")
				decoup.map(n => parseUrl(n))
			}

			def mainTask(url: String) = {
				var tmp = ""
				try {
					val town =  (xml \\ "span").filter(n => (n \ "@class").
					text.equals("adr"))(0).text.split(",")(0)
					tmp = town
					val isResult = List("london","ldn","manche")

					println(url + ";" + town +";" + isResult.filter(p => town.toLowerCase.contains(p))(0));
				}
				catch {
					case e => println(url + ";" + tmp)
				}
			}

		}
	}
}