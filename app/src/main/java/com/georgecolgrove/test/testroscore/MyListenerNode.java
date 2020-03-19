package com.georgecolgrove.test.testroscore;

import android.util.Log;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;
import std_msgs.String;
import java.lang.StringBuffer;

public class MyListenerNode implements NodeMain {
    View view;

    public MyListenerNode(View rootView) {
        this.view = rootView;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("test_app/talker");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Subscriber<String> subscriber =
                connectedNode.newSubscriber("chatter", std_msgs.String._TYPE);
        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
//        connectedNode.executeCancellableLoop(new CancellableLoop() {
//            private int sequenceNumber;
//
//            @Override
//            protected void setup() {
//
//            }
//
//            @Override
//            protected void loop() throws InterruptedException {
//
//            }
//        });
        subscriber.addMessageListener(new MessageListener<String>() {
            @Override
            public void onNewMessage(String string) {
                // Need to use StringBuffer as java.lang.String conflicts with std_msgs.String
                StringBuffer buf = new StringBuffer();
                buf.append(string.getData());
                Log.i("RosApp", buf.toString());
                Snackbar.make(view, string.getData(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }
}
