package scratchpad.db

import jetbrains.exodus.entitystore.PersistentEntityStores



fun main(args: Array<String>) {
    val xodusTest = XodusTest()
    xodusTest.run()
}

class XodusTest() {

    fun run() {
        val entityStore = PersistentEntityStores.newInstance("/home/alex/xodus")
        entityStore.executeInTransaction { txn ->
            val message = txn.newEntity("Message2")
            message.setProperty("hello", "World!")
            txn.getAll("Message").forEach {
                println(it.getProperty("hello"))
            }
        }
        entityStore.close()
    }
}
