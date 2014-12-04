import sbtassembly.Plugin.{AssemblyKeys, PathList, MergeStrategy}
import AssemblyKeys._


// put this at the top of the file

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (default) => {
	case PathList("META-INF", "cxf", "bus-extensions.txt") => MergeStrategy.concat
	case PathList("META-INF", "spring.tooling") => MergeStrategy.concat
	case PathList("META-INF", "tools-plugin.xml") => MergeStrategy.concat
	case PathList("META-INF", "tools.service.validator.xml") => MergeStrategy.concat
	case PathList("META-INF", "wsdl.plugin.xml") => MergeStrategy.concat
	case PathList("META-INF", "javamail.charset.map") => MergeStrategy.concat
	case PathList("META-INF", "mailcap") => MergeStrategy.concat
	case PathList("schemas", "xml.xsd") => MergeStrategy.rename
	case PathList("schemas", "xmldsig-core-schema.xsd") => MergeStrategy.rename
	case x => default(x)
}}