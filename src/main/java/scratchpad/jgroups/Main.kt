package scratchpad.jgroups

import org.jgroups.*
import org.jgroups.blocks.RpcDispatcher
import org.jgroups.protocols.*
import org.jgroups.protocols.pbcast.GMS
import org.jgroups.protocols.pbcast.NAKACK2
import org.jgroups.protocols.pbcast.STABLE
import java.net.InetAddress

fun createWithReceiver(receiver: Receiver): JChannel {

    val protocolStack = listOf(
            UDP(),
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
    val ch = JChannel(protocolStack)

    ch.receiver = receiver

    ch.connect("ChatCluster")
    return ch
}

fun main(args: Array<String>) {
    val channel1 = createWithReceiver(object: ReceiverAdapter() {
        override fun viewAccepted(new_view: View) {
            System.out.println("1 - view: " + new_view);
        }

        override fun receive(msg: Message) {
            System.out.println("1 - << " + msg.getObject() + " [" + msg.getSrc() + "]");
        }
    })

    val channel2 = createWithReceiver(object: ReceiverAdapter() {
        override fun viewAccepted(new_view: View) {
            System.out.println("2 - view: " + new_view);
        }

        override fun receive(msg: Message) {
            System.out.println("2 - << " + msg.getObject() + " [" + msg.getSrc() + "]");
        }
    })

    println("about to send message")
    channel2.send(Message(null, "Hello from 2"))
    println("message sent")
}
