package scratchpad.db

import jetbrains.exodus.entitystore.PersistentEntityStores
import java.nio.file.Files


fun main(args: Array<String>) {
    val xodusTest = XodusTest()
    xodusTest.run()
}

class XodusTest() {

    fun run() {
        //val entityStore = PersistentEntityStores.newInstance("/home/alex/xodus")
        val entityStore = PersistentEntityStores
                .newInstance(Files.createTempDirectory("tmp").toFile())


        val result = entityStore.computeInTransaction { txn ->
//            val message = txn.newEntity("Message")
//            message.setProperty("hello", "World!")
//            txn.getAll("Message").forEach {
//                println(it.getProperty("hello"))
//            }
            val linkedExample = txn.newEntity("Example")

            val linkedExample2 = txn.newEntity("Example")
            val linkedExample3 = txn.newEntity("Example")


            val example = txn.newEntity("Example")
            example.setProperty("Int", 5)
            example.setProperty("Long", 5L)

            example.addLink("go", linkedExample2)
            example.addLink("go", linkedExample3)
            example.addLink("go", linkedExample)

            example.setLink("go", linkedExample)

            println("* ${example.getLink("go")}")
            println("** ${example.getLinks("go").size()}")

//            val result = txn.findLinks("Example", linkedExample, "go")
//
//            println(result.first!!.propertyNames)
//
//            println(result.first!!.propertyNames)
//            val resultI = txn.find("Example", "Int", 5)
//            val resultL = txn.find("Example", "Long", 5L)
//
//            println(resultI.first)
//            println(resultL.first)
//
//            result.first!!

        }



        //println("!!!" + result.propertyNames)


        entityStore.close()
    }
}
