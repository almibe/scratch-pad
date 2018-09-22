package scratchpad.jgroups

import org.jgroups.JChannel
import org.jgroups.Message
import org.jgroups.ReceiverAdapter
import org.jgroups.View
import org.jgroups.protocols.*
import org.jgroups.protocols.pbcast.GMS
import org.jgroups.protocols.pbcast.NAKACK2
import org.jgroups.protocols.pbcast.STABLE
import java.net.InetAddress

fun main(args: Array<String>) {
    val protocolStack = listOf(
        UDP().setValue("bind_addr", InetAddress.getByName("127.0.0.1")),
        PING(),
        MERGE3(),
        FD_SOCK(),
        FD_ALL(),
        VERIFY_SUSPECT(),
        BARRIER(),
        NAKACK2(),
        UNICAST3(),
        STABLE(),
        GMS(),
        UFC(),
        MFC(),
        FRAG2())
    val ch = JChannel(protocolStack).name(args[0])

    ch.receiver = object: ReceiverAdapter() {
        override fun viewAccepted(new_view: View) {
            System.out.println("view: " + new_view);
        }

        override fun receive(msg: Message) {
            System.out.println("<< " + msg.getObject() + " [" + msg.getSrc() + "]");
        }
    }

    ch.connect("ChatCluster")
}
