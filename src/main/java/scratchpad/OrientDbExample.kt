package scratchpad

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import com.orientechnologies.orient.core.metadata.schema.OType
import com.orientechnologies.orient.core.record.impl.ODocument
import com.orientechnologies.orient.core.sql.OCommandSQL

fun orientDB() {
    val db = ODatabaseDocumentTx("memory:test")
    db.create<ODatabaseDocumentTx>()

    val testClass = db.metadata.schema.createClass("Test")
    testClass.createProperty("set", OType.EMBEDDEDSET)

    val doc = ODocument("Test")
    doc.field("test", "Test")
    doc.field("set", mutableSetOf("test1","test2","test3"))

    db.save<ODocument>(doc)

    assert(db.browseClass("Test").toList().size == 1)

    val doc2 = db.browseClass("Test").toList().first()
    val result = doc2.field<Set<String>>("set")
    assert(result.size == 3)
    println(result)

    val i = db.command(OCommandSQL("update Test add set = \"test4\"")).execute<Int>()
    assert(i == 1)
    val doc3 = db.browseClass("Test").toList().first()
    val result3 = doc3.field<Set<String>>("set")
    (result3.size == 4)
    println(result3)

    db.close()
}
