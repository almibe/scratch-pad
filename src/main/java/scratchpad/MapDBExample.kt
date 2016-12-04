package scratchpad

import org.mapdb.DBMaker
import org.mapdb.Serializer

fun inMemoryTest() {
    val db = DBMaker.memoryDB().make()
    val map = db.hashMap("s", Serializer.STRING, Serializer.STRING).createOrOpen()
    map.put("something", "here")
    db.close()
}
