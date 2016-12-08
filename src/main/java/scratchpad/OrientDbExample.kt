package scratchpad

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import com.orientechnologies.orient.core.record.impl.ODocument

fun orientDB() {
    val db = ODatabaseDocumentTx("memory:test")
    db.create<ODatabaseDocumentTx>()

    val doc = ODocument("Test")
    doc.field("test", "Test")
    doc.field("set", mutableSetOf("test1","test2","test3"))

    db.save<ODocument>(doc)

    assert(db.browseClass("Test").toList().size == 1)

    val doc2 = db.browseClass("Test").toList().first()

    val result = doc2.field<Set<String>>("set")

    println(result)

    db.close()
}
