fun main() {
    /*
      Proceso que printea la estructura de una carpeta
      La línea comentada, sola con el print nos saca las carpetas y archivos del ls que hemos pedido
     */
    val ls = ProcessBuilder("ls", "-ls", ".").start()
//    val lsOut = ls.inputStream.bufferedReader().lineSequence().joinToString("\n")
    val lsOut = ls.inputStream.bufferedReader()
        .lineSequence()
        .filter { it.contains(".kts") || it.contains(".bat") }
        .joinToString("\n")
    println(lsOut)

    ls.waitFor()
    val exitValue = ls.exitValue()
    println("Valor de salida del proceso ls: $exitValue")

    /*
      Proceso de ejecución de un comando cat
      Tomamos la primera línea
     */
    val catFile = lsOut.lines().first().split(" ").last()
    println(catFile)

    /*
      Proceso de ejecución de un comando cat y filtrado con grep
      Este comando permite crear un archivo .txt, por ejemplo, o abrir y mostrar por
      terminal el contenido de un archivo
      Hacemos que el proceso espere con "waitFor()"
     */
    val cat = ProcessBuilder("cat", catFile).start()
    cat.waitFor()

    //Dentro del archivo buscamos algo determinado, por ejemplo "kotlinOptions.jvmTarget"
    //Para ello usamos el comando grep
    //Básicamente lo que hacemos es esto: cat build.gradle.kts | grep "kotlinOptions.jvmTarget"
    val grep = ProcessBuilder("grep", "kotlinOptions.jvmTarget").start()
    //Leemos el cat para verlo por nuestro terminal
    val catOut = cat.inputStream.bufferedReader().readText()
    //Pasamos la salida del cat al grep para que nos filtre lo que queremos ver
    grep.outputStream.bufferedWriter().use { it.write(catOut) }
    //Ahora leemos el resultado de grep
    val grepOut = grep.inputStream.bufferedReader().readText()
    println("El resultado es: $grepOut")

    /*
      Proceso que hace ping a una página web, por ejemplo
      Primero creamos el comando ping y después creamos la variable de salida para ver el resutlado
     */
    val ping = ProcessBuilder("ping", "-c", "1", "www.google.com").start()
//    val pingOut = ping.inputStream.bufferedReader().lineSequence().joinToString("\n")
//    println(pingOut)
    //Si queremos filtrar para saber, por ejemplo, el tiempo medio de respuesta, el pingOut sería así
    val pingOut2 = ping.inputStream.bufferedReader().lineSequence().last().split("/")
    println("Tiempo medio de respuesta: ${pingOut2[4]}")
}