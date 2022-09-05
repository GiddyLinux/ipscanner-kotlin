
import java.io.File
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType


fun main(args: Array<String>) {
    var outfile = "output"
    val parser = ArgParser("example")
    val outputFile by parser.option(ArgType.String, shortName = "o", description = "Output file")
    parser.parse(args)

    if (outputFile?.isEmpty() == null) {
        println("Using default output directory (output) \n Change this by using the -o flag \n\n")
    } else {
        outfile = outputFile as String
    }

    while(true){
        scan(outfile)
    }


}
fun generate(): String {
    var ip = ""
    for (i in 1..4) {
        val octet = (0..255).random()
        ip = "$ip$octet."
    }
    ip = ip.dropLast(1)
    return ip
}
fun scan(outfile: String) {
    val ip = generate()
    val port = 80
    try {

        val sockaddr: SocketAddress = InetSocketAddress(ip, port)
        val sock = Socket()
        val timeoutMs = 500
        sock.connect(sockaddr, timeoutMs)

        val host = InetAddress.getByName(ip).hostName
        println("$ip (${host}) is up")
        File(outfile).appendText("$ip (${host}) has port $port open \n")

    } catch (e: IOException) {

        println("$ip is down")

    }
}
