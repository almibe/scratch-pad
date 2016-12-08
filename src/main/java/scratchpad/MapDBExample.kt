package scratchpad

import org.mapdb.DBMaker
import org.mapdb.Serializer
import java.io.Serializable
import java.util.concurrent.ConcurrentMap

data class Person(val name: String, val age: Int) : Serializable
data class Car(val company: String, val age: Int) : Serializable

fun inMemoryTest() {
    val db = DBMaker.memoryDB().make()
    val map: ConcurrentMap<Int, Any> = db.hashMap("person", Serializer.INTEGER, Serializer.JAVA).createOrOpen()
    map.put(1, Person("Test", 3))
    map.put(2, Person("Test", 3))
    map.put(3, Car("Ford", 90))
    println(map[1] == Person("Test", 3))
    println(map[3] == Car("Ford", 90))
    println(map[3] == Person("Test", 3))
    db.close()
}
