@file:JvmName("Main")

package scratchpad

import jetbrains.exodus.bindings.IntegerBinding
import jetbrains.exodus.env.Environments
import jetbrains.exodus.env.StoreConfig
import kotlin.random.Random

fun main() {


//    val env = Environments.newInstance("/home/alex/tempXodus2")
//
//    env.clear()
//
//    env.close()




//    val start2 = System.currentTimeMillis()
//    for (x in 0..10000) {
//        val t1 = env.beginTransaction()
//
//        for (y in 0..6) {
//            val c1 = env.openStore("cnt$y", StoreConfig.WITHOUT_DUPLICATES, t1)
//
//            c1.put(t1, intToEntry(Random.nextInt()), booleanToEntry(Random.nextBoolean()))
//        }
//
//        t1.commit()
//    }
//    val end2 = System.currentTimeMillis()
//
//
//    val start1 = System.currentTimeMillis()
//    for (x in 0..10000) {
//        val t1 = env.beginTransaction()
//
//        val c1 = env.openStore("cnt", StoreConfig.WITHOUT_DUPLICATES, t1)
//
//        for (y in 0..6) {
//            c1.put(t1, stringToEntry("${Random.nextInt()}-${Random.nextInt()}-${Random.nextInt()}-${Random.nextInt()}-${Random.nextInt()}-${Random.nextInt()}"), booleanToEntry(Random.nextBoolean()))
//        }
//
//        t1.commit()
//    }
//    val end1 = System.currentTimeMillis()



    val env2 = Environments.newInstance("/home/alex/tempXodus2")
    var t = env2.beginTransaction()


    val store = env2.openStore("test", StoreConfig.WITHOUT_DUPLICATES, t)

    println(store.count(t))

    val start3 = System.currentTimeMillis()
    for (x in 0..1000000) {
        store.put(t, IntegerBinding.intToEntry(Random.nextInt()), IntegerBinding.intToEntry(Random.nextInt()))
    }
    t.commit()

    val writeEnd = System.currentTimeMillis()
    t = env2.beginTransaction()

    var c = 0

    for (x in 0..1000000) {
        val result = store.get(t, IntegerBinding.intToEntry(Random.nextInt()))
        if (result == null) {
            c++
        }
    }
    t.abort()
    val end3 = System.currentTimeMillis()




    env2.close()



//    env.close()

//    println("${end1 - start1}")

//    println("${end2 - start2}")
    println("write - ${end3 - writeEnd}")
    println("read - ${(end3 - start3) - (end3 - writeEnd)}")
    println("total - ${end3 - start3}")

}
